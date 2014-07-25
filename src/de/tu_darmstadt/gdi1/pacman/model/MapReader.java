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
	private String[][] cacheMapData;
	private MapElement[][] mapData;

	int aPlayerSpawnPointCol, aPlayerSpawnPointRow;

	public MapReader(File mapFile) {

		this.mapFile = mapFile;
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
		cacheMapData = new String[height][width];
		mapData = new MapElement[height][width];

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
					cacheMapData[row][col] = String.valueOf(elements[col]);

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
					if (cacheMapData[i][j].equals("X")) {
						mapData[i][j] = new Wall(new Vector2f(xy), getWallType(
								i, j));
					} else if (cacheMapData[i][j].equals(" ")) {
						mapData[i][j] = new Dot(new Vector2f(xy), forks(i, j));
						item++;
					} else if (cacheMapData[i][j].equals("P")) {
						mapData[i][j] = new PlayerSpawnPoint(new Vector2f(xy));
						ps++;
						aPlayerSpawnPointCol = j;
						aPlayerSpawnPointRow = i;
					} else if (cacheMapData[i][j].equals("G")) {
						mapData[i][j] = new GhostSpawnPoint(new Vector2f(xy));
						gs++;
					} else if (cacheMapData[i][j].equals("B"))
						mapData[i][j] = new InvisibleWall(new Vector2f(xy));
					else if (cacheMapData[i][j].equals("S")) {
						mapData[i][j] = new SpeedUp(new Vector2f(xy), forks(i, j));
						item++;
					} else if (cacheMapData[i][j].equals("T"))
						mapData[i][j] = new Teleporter(new Vector2f(xy));
					else if (cacheMapData[i][j].equals("U")) {
						mapData[i][j] = new PowerUp(new Vector2f(xy), forks(i, j));
						item++;
					} else
						// thrwo invalidLevelCharacterException
						throw new InvalidLevelCharacterException(
								cacheMapData[i][j].charAt(0));

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
				if (mapData[i][j] instanceof Item
						|| mapData[i][j] instanceof Teleporter
						|| mapData[i][j] instanceof PlayerSpawnPoint)
					tempMap[i][j] = 1;
				else
					tempMap[i][j] = 0;
			}
		}

		eatAllItems(tempMap, aPlayerSpawnPointRow, aPlayerSpawnPointCol);

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

	private void eatAllItems(int[][] tempMap, int i, int j) {

		tempMap[i][j] = 0;

		if (j > 0
				&& tempMap[i][j - 1] == 1
				&& (mapData[i][j - 1] instanceof Item
						|| mapData[i][j - 1] instanceof Teleporter || mapData[i][j - 1] instanceof PlayerSpawnPoint)) {
			eatAllItems(tempMap, i, j - 1);
		}
		if (j + 1 < width
				&& tempMap[i][j + 1] == 1
				&& (mapData[i][j + 1] instanceof Item
						|| mapData[i][j + 1] instanceof Teleporter || mapData[i][j + 1] instanceof PlayerSpawnPoint)) {
			eatAllItems(tempMap, i, j + 1);
		}
		if (i - 1 >= 0
				&& tempMap[i - 1][j] == 1
				&& (mapData[i - 1][j] instanceof Item
						|| mapData[i - 1][j] instanceof Teleporter || mapData[i][j - 1] instanceof PlayerSpawnPoint)) {
			eatAllItems(tempMap, i - 1, j);
		}
		if (i + 1 < height
				&& tempMap[i + 1][j] == 1
				&& (mapData[i + 1][j] instanceof Item
						|| mapData[i + 1][j] instanceof Teleporter || mapData[i + 1][j] instanceof PlayerSpawnPoint)) {
			eatAllItems(tempMap, i + 1, j);
		}

		if (i == 0 && tempMap[height - 1][j] == 1
				&& !(mapData[height - 1][j] instanceof Wall)
				&& !(mapData[height - 1][j] instanceof InvisibleWall)) {
			eatAllItems(tempMap, height - 1, j);
		} else if (i == height - 1 && tempMap[0][j] == 1
				&& !(mapData[0][j] instanceof Wall)
				&& !(mapData[0][j] instanceof InvisibleWall)) {
			eatAllItems(tempMap, 0, j);
		} else if (j == 0 && tempMap[i][width - 1] == 1
				&& !(mapData[i][width - 1] instanceof Wall)
				&& !(mapData[i][width - 1] instanceof InvisibleWall)) {
			eatAllItems(tempMap, i, width - 1);
		} else if (j == width - 1 && tempMap[i][0] == 1
				&& !(mapData[i][0] instanceof Wall)
				&& !(mapData[i][0] instanceof Wall)) {
			eatAllItems(tempMap, i, 0);
		}
	}

	/**
	 * check if a given point is a fork road
	 * 
	 * @param i
	 *            row of MapElement[][]
	 * @param j
	 *            col of MapElement[][]
	 * @return if it is a fork road
	 */
	private List<int[]> forks(int i, int j) {
		
		//a point is a fork point, only if it has mindestens 3 neighbour walkablepoint
		List<int[]> forks=new ArrayList<>();
		if (isLeftWalkable(i, j)){
			forks.add(new int[]{i,j-1});
		}
		if (isRightWalkable(i, j))
			forks.add(new int[]{i,j+1});
		if (isUpWalkable(i, j))
			forks.add(new int[]{i-1,j});
		if (isDownWalkable(i, j))
			forks.add(new int[]{i+1,j});
		
		if(forks.size()<3)
			 forks.clear();
		
		return forks;
	}

	private boolean isLeftWalkable(int i, int j) {
		if (j == 0) {
			return false;
		} else {
			return (cacheMapData[i][j - 1].equals("P")
					|| cacheMapData[i][j - 1].equals("S") || cacheMapData[i][j - 1]
					.equals("U"));
		}
	}

	private boolean isRightWalkable(int i, int j) {
		if (j == width - 1) {
			return false;
		} else {
			return (cacheMapData[i][j + 1].equals("P")
					|| cacheMapData[i][j + 1].equals("S") || cacheMapData[i][j + 1]
					.equals("U"));
		}
	}

	private boolean isUpWalkable(int i, int j) {
		if (i == 0) {
			return false;
		} else {
			return (cacheMapData[i - 1][j].equals("P")
					|| cacheMapData[i - 1][j].equals("S") || cacheMapData[i - 1][j]
					.equals("U"));
		}
	}

	private boolean isDownWalkable(int i, int j) {
		if (i == height - 1) {
			return false;
		} else {
			return (cacheMapData[i + 1][j].equals("P")
					|| cacheMapData[i + 1][j].equals("S") || cacheMapData[i + 1][j]
					.equals("U"));
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
			return cacheMapData[i][j - 1].equals("X");
		}

	}

	private boolean isRightWall(int i, int j) {
		if (j == width - 1) {
			return false;
		} else {
			return cacheMapData[i][j + 1].equals("X");
		}
	}

	private boolean isUpWall(int i, int j) {
		if (i == 0) {
			return false;
		} else {
			return cacheMapData[i - 1][j].equals("X");
		}
	}

	private boolean isDownWall(int i, int j) {
		if (i == height - 1) {
			return false;
		} else {
			return cacheMapData[i + 1][j].equals("X");
		}
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				sb.append(cacheMapData[i][j]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public MapElement[][] getMapData() {
		return mapData;
	}

}
