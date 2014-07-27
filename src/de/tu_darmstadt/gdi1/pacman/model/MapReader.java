package de.tu_darmstadt.gdi1.pacman.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.Sys;
import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.pacman.exceptions.*;

/**
 * 
 * 
 *
 */
public class MapReader {

	public int height;
	public int width;
	public final int MAPDENSITY = 35;

	public int height_on_display;
	public int width_on_display;

	private File mapFile;// path of txt file
	private String[][] mapElementStringArray;
	private MapElement[][] mapElementArray;
	
	private List<PlayerSpawnPoint> playerSpawnPoints;
	private List<GhostSpawnPoint> ghostSpawnPoints;
	int aPlayerSpawnPointCol, aPlayerSpawnPointRow;

	public MapReader(File mapFile) {

		this.mapFile = mapFile;
		playerSpawnPoints=new LinkedList<>();
		ghostSpawnPoints=new LinkedList<>();
		initHW();
		initMapData();
		intElementCoordinates();
		isAllAreaAchievable();

	}

	private void initHW() {

		height = 0;
		width = 0;

		try {
			BufferedReader br = new BufferedReader(new FileReader(mapFile));
			String line = br.readLine();
			// get length of map
			width = line.length();
			// get height of map
			while (line != null) {
				height += 1;
				line = br.readLine();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		height_on_display = (height - 1) * 35;
		width_on_display = (width - 1) * 35;
		mapElementStringArray = new String[height][width];
		mapElementArray = new MapElement[height][width];

	}

	/**
	 * read map from txt data and save all characters into 2D sting array
	 * 
	 * 
	 */
	private void initMapData() {

		try {

			BufferedReader br = new BufferedReader(new FileReader(mapFile));

			for (int row = 0; row < height; row++) {
				// read a line
				String line = br.readLine();
				// if length of this line is different from width, then throw
				// exception
				if (line.length() != width)
					throw new InvalidLevelFormatException();
				// split all single characters of this string into array
				char[] elements = line.toCharArray();
				// save these single characters into mapData array

				// System.out.println(mapData[row].length);
				for (int col = 0; col < width; col++) {
					mapElementStringArray[row][col] = String.valueOf(elements[col]);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * save elements into a 2 demensional MapElement arrat
	 */
	private void intElementCoordinates() {

		int ps = 0;// player spawn point counter
		int gs = 0;// ghost spawn point counter
		int item = 0;// item counter
		try {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					// j*MAPDENSITY as x coordinate, i*MAPDENSITY as y
					// coordinate
					float[] xy = { j * MAPDENSITY, i * MAPDENSITY };
					// add coordinates in to right category
					if (mapElementStringArray[i][j].equals("X")) {
						mapElementArray[i][j] = new Wall(new Vector2f(xy), getWallType(
								i, j));
					} else if (mapElementStringArray[i][j].equals(" ")) {
						mapElementArray[i][j] = new Dot(new Vector2f(xy), getForksForPacman(i, j), getForksForGhost(i, j));
						item++;
					} else if (mapElementStringArray[i][j].equals("P")) {
						mapElementArray[i][j] = new PlayerSpawnPoint(new Vector2f(xy), getForksForPacman(i, j), getForksForGhost(i, j));
						playerSpawnPoints.add((PlayerSpawnPoint)mapElementArray[i][j]);
						aPlayerSpawnPointRow=i;
						aPlayerSpawnPointCol=j;
						ps++;
					} else if (mapElementStringArray[i][j].equals("G")) {
						mapElementArray[i][j] = new GhostSpawnPoint(new Vector2f(xy),getForksForPacman(i, j), getForksForGhost(i, j));
						ghostSpawnPoints.add((GhostSpawnPoint)mapElementArray[i][j]);
						gs++;
					} else if (mapElementStringArray[i][j].equals("B"))
						mapElementArray[i][j] = new InvisibleWall(new Vector2f(xy), getForksForPacman(i, j), getForksForGhost(i, j));
					else if (mapElementStringArray[i][j].equals("S")) {
						mapElementArray[i][j] = new SpeedUp(new Vector2f(xy), getForksForPacman(i, j), getForksForGhost(i, j));
						item++;
					} else if (mapElementStringArray[i][j].equals("T"))
						mapElementArray[i][j] = new Teleporter(new Vector2f(xy),getForksForPacman(i, j), getForksForGhost(i, j));
					else if (mapElementStringArray[i][j].equals("U")) {
						mapElementArray[i][j] = new PowerUp(new Vector2f(xy), getForksForPacman(i, j), getForksForGhost(i, j));
						item++;
					} else
						// thrwo invalidLevelCharacterException
						throw new InvalidLevelCharacterException(
								mapElementStringArray[i][j].charAt(0));

				}
			}
			// check for PacmanSpawnPoint, GhostSpawnPoint and item exceptions
			if (gs == 0)
				throw new NoGhostSpawnPointException();
			if (ps == 0)
				throw new NoPacmanSpawnPointException();
			if (item == 0)
				throw new NoItemsException();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private boolean isAllAreaAchievable() {
		// mark all point that the player could reach as 1, else as 0
		int[][] tempMap = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (mapElementArray[i][j] instanceof Item
						|| mapElementArray[i][j] instanceof Teleporter
						|| mapElementArray[i][j] instanceof PlayerSpawnPoint)
					tempMap[i][j] = 1;
				else
					tempMap[i][j] = 0;
			}
		}

		reachAllValidPoint(tempMap, aPlayerSpawnPointRow, aPlayerSpawnPointCol);

		int unreachablePoint = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (tempMap[i][j] == 1) {
					unreachablePoint++;
				}
			}
		}

		try {
			if (unreachablePoint > 0) {
				System.out
						.println(unreachablePoint + " points are unreachable");
				throw new ReachabilityException();
			}
		} catch (ReachabilityException e) {
			System.out.println(e);
			return false;
		}

		return true;

	}

	private void reachAllValidPoint(int[][] tempMap, int i, int j) {

		tempMap[i][j] = 0;

		if (j > 0
				&& tempMap[i][j - 1] == 1
				&& (mapElementArray[i][j - 1] instanceof Item
						|| mapElementArray[i][j - 1] instanceof Teleporter || mapElementArray[i][j - 1] instanceof PlayerSpawnPoint)) {
			reachAllValidPoint(tempMap, i, j - 1);
		}
		if (j + 1 < width
				&& tempMap[i][j + 1] == 1
				&& (mapElementArray[i][j + 1] instanceof Item
						|| mapElementArray[i][j + 1] instanceof Teleporter || mapElementArray[i][j + 1] instanceof PlayerSpawnPoint)) {
			reachAllValidPoint(tempMap, i, j + 1);
		}
		if (i - 1 >= 0
				&& tempMap[i - 1][j] == 1
				&& (mapElementArray[i - 1][j] instanceof Item
						|| mapElementArray[i - 1][j] instanceof Teleporter || mapElementArray[i][j - 1] instanceof PlayerSpawnPoint)) {
			reachAllValidPoint(tempMap, i - 1, j);
		}
		if (i + 1 < height
				&& tempMap[i + 1][j] == 1
				&& (mapElementArray[i + 1][j] instanceof Item
						|| mapElementArray[i + 1][j] instanceof Teleporter || mapElementArray[i + 1][j] instanceof PlayerSpawnPoint)) {
			reachAllValidPoint(tempMap, i + 1, j);
		}

		if (i == 0 && tempMap[height - 1][j] == 1
				&& !(mapElementArray[height - 1][j] instanceof Wall)
				&& !(mapElementArray[height - 1][j] instanceof InvisibleWall)) {
			reachAllValidPoint(tempMap, height - 1, j);
		} else if (i == height - 1 && tempMap[0][j] == 1
				&& !(mapElementArray[0][j] instanceof Wall)
				&& !(mapElementArray[0][j] instanceof InvisibleWall)) {
			reachAllValidPoint(tempMap, 0, j);
		} else if (j == 0 && tempMap[i][width - 1] == 1
				&& !(mapElementArray[i][width - 1] instanceof Wall)
				&& !(mapElementArray[i][width - 1] instanceof InvisibleWall)) {
			reachAllValidPoint(tempMap, i, width - 1);
		} else if (j == width - 1 && tempMap[i][0] == 1
				&& !(mapElementArray[i][0] instanceof Wall)
				&& !(mapElementArray[i][0] instanceof Wall)) {
			reachAllValidPoint(tempMap, i, 0);
		}
	}

	/**
	 * check if a given point is a fork road
	 * 
	 * def: a fork is a point, that a player can turn into different direction
	 * (not including turning back). And specially, we consider a dead end also a fork, at which figur can only turn back.
	 * 
	 * if size is 0, means it is not a fork
	 * 
	 * consider this situation:
	 * XXXXXXXXXXX
	 *   GGGB    P
	 * XX XX XXXX 
	 *            
	 * XXXXXXXXXXX
	 * 
	 * if we want ghost to have a better AI, we need a new fork list which contains more turn choince.
	 * it would be a lot easier that we build 2 fork list, one for pacman and one for ghost
	 * @param i
	 *            row of MapElement[][]
	 * @param j
	 *            col of MapElement[][]
	 * @return if it is a fork road
	 */
	private List<Direction> getForksForPacman(int i, int j) {
		
		List<Direction> forks=new LinkedList<>();
		if (isLeftWalkable(i, j))
			forks.add(Direction.LEFT);
		if (isRightWalkable(i, j))
			forks.add(Direction.RIGHT);
		if (isUpWalkable(i, j))
			forks.add(Direction.UP);
		if (isDownWalkable(i, j))
			forks.add(Direction.DOWN);
		
		//if this point is between a straight road, it is not a fork, unless it's a spawn point
		if(forks.size()==2&&!mapElementStringArray[i][j].equals("P")){
			if(forks.contains(Direction.LEFT)&&!forks.contains(Direction.RIGHT))
				return forks;
			else if(forks.contains(Direction.RIGHT)&&!forks.contains(Direction.LEFT))
				return forks;
			else if(forks.contains(Direction.UP)&&!forks.contains(Direction.DOWN))
				return forks;
			else if(forks.contains(Direction.DOWN)&&!forks.contains(Direction.UP))
				return forks;
			else {
				forks.clear();
				return forks;
			}
		}else
			return forks;
		
	}
	
	private List<Direction> getForksForGhost(int i, int j) {
		
		List<Direction> forks=new LinkedList<>();
		if(i>0&&i<height-1&&j>0&&j<width-1){
		if (isLeftWalkable(i, j)||mapElementStringArray[i][j-1].equals("G")||mapElementStringArray[i][j-1].equals("B"))
			forks.add(Direction.LEFT);
		if (isRightWalkable(i, j)||mapElementStringArray[i][j+1].equals("G")||mapElementStringArray[i][j+1].equals("B"))
			forks.add(Direction.RIGHT);
		if (isUpWalkable(i, j)||mapElementStringArray[i-1][j].equals("G")||mapElementStringArray[i-1][j].equals("B"))
			forks.add(Direction.UP);
		if (isDownWalkable(i, j)||mapElementStringArray[i+1][j].equals("G")||mapElementStringArray[i+1][j].equals("B"))
			forks.add(Direction.DOWN);
		}
		
		//if this point is between a straight road, it is not a fork, unless it's a spawn point
		if(forks.size()==2){
			if(forks.contains(Direction.LEFT)&&!forks.contains(Direction.RIGHT))
				return forks;
			else if(forks.contains(Direction.RIGHT)&&!forks.contains(Direction.LEFT))
				return forks;
			else if(forks.contains(Direction.UP)&&!forks.contains(Direction.DOWN))
				return forks;
			else if(forks.contains(Direction.DOWN)&&!forks.contains(Direction.UP))
				return forks;
			else {
				forks.clear();
				return forks;
			}
		}else
			return forks;
		
	}

	private boolean isLeftWalkable(int i, int j) {
		if (j == 0) {
			return false;
		} else {
			return (mapElementStringArray[i][j - 1].equals(" ")||mapElementStringArray[i][j - 1].equals("P")
					|| mapElementStringArray[i][j - 1].equals("S") || mapElementStringArray[i][j - 1]
					.equals("U")||mapElementStringArray[i][j - 1].equals("T"));
		}
	}

	private boolean isRightWalkable(int i, int j) {
		if (j == width - 1) {
			return false;
		} else {
			return (mapElementStringArray[i][j + 1].equals(" ")||mapElementStringArray[i][j + 1].equals("P")
					|| mapElementStringArray[i][j + 1].equals("S") || mapElementStringArray[i][j + 1]
					.equals("U")||mapElementStringArray[i][j + 1].equals("T"));
		}
	}

	private boolean isUpWalkable(int i, int j) {
		if (i == 0) {
			return false;
		} else {
			return (mapElementStringArray[i-1][j].equals(" ")||mapElementStringArray[i-1][j].equals("P")
					|| mapElementStringArray[i-1][j].equals("S") || mapElementStringArray[i-1][j]
					.equals("U")||mapElementStringArray[i-1][j].equals("T"));
		}
	}

	private boolean isDownWalkable(int i, int j) {
		if (i == height - 1) {
			return false;
		} else {
			return (mapElementStringArray[i+1][j].equals(" ")||mapElementStringArray[i+1][j].equals("P")
					|| mapElementStringArray[i+1][j].equals("S") || mapElementStringArray[i+1][j]
					.equals("U")||mapElementStringArray[i+1][j].equals("T"));
		}
	}

	private WallType getWallType(int i, int j) {

		if (isLeftWall(i, j) && isRightWall(i, j) && isUpWall(i, j)
				&& isDownWall(i, j)) {
			return WallType.UDLR;
		} else if (!isLeftWall(i, j) && isRightWall(i, j) && isUpWall(i, j)
				&& isDownWall(i, j)) {
			return WallType.UDR;
		} else if (isLeftWall(i, j) && !isRightWall(i, j) && isUpWall(i, j)
				&& isDownWall(i, j)) {
			return WallType.UDL;
		} else if (isLeftWall(i, j) && isRightWall(i, j) && !isUpWall(i, j)
				&& isDownWall(i, j)) {
			return WallType.DLR;
		} else if (isLeftWall(i, j) && isRightWall(i, j) && isUpWall(i, j)
				&& !isDownWall(i, j)) {
			return WallType.ULR;
		} else if (!isLeftWall(i, j) && !isRightWall(i, j) && isUpWall(i, j)
				&& isDownWall(i, j)) {
			return WallType.UD;
		} else if (!isLeftWall(i, j) && isRightWall(i, j) && !isUpWall(i, j)
				&& isDownWall(i, j)) {
			return WallType.DR;
		} else if (!isLeftWall(i, j) && isRightWall(i, j) && isUpWall(i, j)
				&& !isDownWall(i, j)) {
			return WallType.UR;
		} else if (isLeftWall(i, j) && !isRightWall(i, j) && !isUpWall(i, j)
				&& isDownWall(i, j)) {
			return WallType.DL;
		} else if (isLeftWall(i, j) && !isRightWall(i, j) && isUpWall(i, j)
				&& !isDownWall(i, j)) {
			return WallType.UL;
		} else if (isLeftWall(i, j) && isRightWall(i, j) && !isUpWall(i, j)
				&& !isDownWall(i, j)) {
			return WallType.LR;
		} else if (!isLeftWall(i, j) && !isRightWall(i, j) && !isUpWall(i, j)
				&& isDownWall(i, j)) {
			return WallType.D;
		} else if (!isLeftWall(i, j) && !isRightWall(i, j) && isUpWall(i, j)
				&& !isDownWall(i, j)) {
			return WallType.U;
		} else if (!isLeftWall(i, j) && isRightWall(i, j) && !isUpWall(i, j)
				&& !isDownWall(i, j)) {
			return WallType.R;
		} else if (isLeftWall(i, j) && !isRightWall(i, j) && !isUpWall(i, j)
				&& !isDownWall(i, j)) {
			return WallType.L;
		} else
			return WallType.ALONE;

	}

	private boolean isLeftWall(int i, int j) {
		if (j == 0) {
			return false;
		} else {
			return mapElementStringArray[i][j - 1].equals("X");
		}

	}

	private boolean isRightWall(int i, int j) {
		if (j == width - 1) {
			return false;
		} else {
			return mapElementStringArray[i][j + 1].equals("X");
		}
	}

	private boolean isUpWall(int i, int j) {
		if (i == 0) {
			return false;
		} else {
			return mapElementStringArray[i - 1][j].equals("X");
		}
	}

	private boolean isDownWall(int i, int j) {
		if (i == height - 1) {
			return false;
		} else {
			return mapElementStringArray[i + 1][j].equals("X");
		}
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				sb.append(mapElementStringArray[i][j]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public MapElement[][] getMapData() {
		return mapElementArray;
	}

	public List<PlayerSpawnPoint> getPlayerSpawnPoints() {
		return playerSpawnPoints;
	}

	public List<GhostSpawnPoint> getGhostSpawnPoints() {
		return ghostSpawnPoints;
	}

}
