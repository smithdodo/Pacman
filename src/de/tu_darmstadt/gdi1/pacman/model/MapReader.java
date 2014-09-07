package de.tu_darmstadt.gdi1.pacman.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
	
	private List<Dot> dots;
	private List<PowerUp> powerUps;
	private List<SpeedUp> speedUps;
	private List<Teleporter> teleporters;
	
	private Random random=new Random();
	
	private Pacman pacman;
	private List<Ghost> ghosts;

	public MapReader(File mapFile) throws ReachabilityException, InvalidLevelCharacterException, NoGhostSpawnPointException, NoPacmanSpawnPointException, NoItemsException {

		this.mapFile = mapFile;
		
		playerSpawnPoints=new LinkedList<>();
		ghostSpawnPoints=new LinkedList<>();
		dots=new ArrayList<>();
		powerUps=new ArrayList<>();
		speedUps=new ArrayList<>();
		teleporters=new ArrayList<>();
		
		initArrayHeightAndWidth();
		initMapData();
		intElementCoordinates();
		isAllAreaAchievable();
		
		//initialize pacman on a random spawn point
		int ps=random.nextInt(getPlayerSpawnPoints().size());
		Vector2f aPlayerStartPoint=getPlayerSpawnPoints().get(ps).getPosition();
		this.pacman=new de.tu_darmstadt.gdi1.pacman.model.Pacman(aPlayerStartPoint);
		
		//initiallize ghosts
		ghosts=new ArrayList<>();
		for (int i=0; i < getGhostSpawnPoints().size(); i++) {
			ghosts.add(new Ghost(getGhostSpawnPoints().get(i).getPosition()));
		}

	}
	
	/**
	 * calculate the width and height of mapElement array
	 */
	private void initArrayHeightAndWidth() {

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
			br.close();

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

			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(mapFile));
			
			for (int row = 0; row < height; row++) {
				
				// read a line
				String line = br.readLine();
				
				// if length of this line is different from width, then throw
				if (line.length() != width)
					throw new InvalidLevelFormatException();
				
				// split all single characters of this string into array
				char[] elements = line.toCharArray();
				
				// save these single characters into mapData array
				for (int col = 0; col < width; col++) {
					mapElementStringArray[row][col] = String.valueOf(elements[col]);
				}
				
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * save elements into a 2 demensional MapElement arrat
	 * @throws InvalidLevelCharacterException 
	 * @throws NoGhostSpawnPointException 
	 * @throws NoPacmanSpawnPointException 
	 * @throws NoItemsException 
	 */
	private void intElementCoordinates() throws InvalidLevelCharacterException, NoGhostSpawnPointException, NoPacmanSpawnPointException, NoItemsException {

		int ps = 0;// player spawn point counter
		int gs = 0;// ghost spawn point counter
		int item = 0;// item counter
		
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
						dots.add((Dot)mapElementArray[i][j]);
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
						speedUps.add((SpeedUp)mapElementArray[i][j]);
						item++;
					} else if (mapElementStringArray[i][j].equals("T")){
						mapElementArray[i][j] = new Teleporter(new Vector2f(xy),getForksForPacman(i, j), getForksForGhost(i, j));
						teleporters.add((Teleporter)mapElementArray[i][j]);
					}
					else if (mapElementStringArray[i][j].equals("U")) {
						mapElementArray[i][j] = new PowerUp(new Vector2f(xy), getForksForPacman(i, j), getForksForGhost(i, j));
						powerUps.add((PowerUp)mapElementArray[i][j]);
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
		
	}
	
	/**
	 * check if all area of the map is valid
	 * 
	 * @return
	 * @throws ReachabilityException 
	 */
	private boolean isAllAreaAchievable() throws ReachabilityException {
		
		// mark all points that the player should reach as 1, else as 0
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
		
		//try to reach all area
		reachAllValidPoint(tempMap, aPlayerSpawnPointRow, aPlayerSpawnPointCol);
		
		//check if all area have been reached
		int unreachablePoint = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (tempMap[i][j] == 1) {
					unreachablePoint++;
				}
			}
		}
		
//		try {
			if (unreachablePoint > 0) {
				System.out.println(unreachablePoint + " points are unreachable");
				throw new ReachabilityException();
			}else {
				return true;
			}
	/*	} catch (ReachabilityException e) {
			System.out.println(e);
			//print out map with invalid area
			for (int i = 0; i < height; i++) {
				for (int j = 0; j <width; j++) {
					System.out.print(tempMap[i][j]);
				}
				System.out.print("\n");
			}
			return false;
		}*/

	}

	/**
	 * simulate pacman to reach all area of this map, points that has been reached will be marked as 0
	 * 1 in map(here is 2 dimensional array) means point that pacman should reach
	 * @param tempMap
	 * @param i row
	 * @param j col
	 */
	private void reachAllValidPoint(int[][] tempMap, int i, int j) {

		tempMap[i][j] = 0;
		
		//moving in edge
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
		
		//moving at edge, must consider if this point is a RandPaar
		if (i == 0 && tempMap[height - 1][j] == 1
				&& !(mapElementArray[height - 1][j] instanceof Wall)
				&& !(mapElementArray[height - 1][j] instanceof InvisibleWall)
				&& !(mapElementArray[height - 1][j] instanceof GhostSpawnPoint)) {
			reachAllValidPoint(tempMap, height - 1, j);
		} else if (i == height - 1 && tempMap[0][j] == 1
				&& !(mapElementArray[0][j] instanceof Wall)
				&& !(mapElementArray[0][j] instanceof InvisibleWall)
				&& !(mapElementArray[0][j] instanceof GhostSpawnPoint)) {
			reachAllValidPoint(tempMap, 0, j);
		} else if (j == 0 && tempMap[i][width - 1] == 1
				&& !(mapElementArray[i][width - 1] instanceof Wall)
				&& !(mapElementArray[i][width - 1] instanceof InvisibleWall)
				&& !(mapElementArray[i][width - 1] instanceof GhostSpawnPoint)) {
			reachAllValidPoint(tempMap, i, width - 1);
		} else if (j == width - 1 && tempMap[i][0] == 1
				&& !(mapElementArray[i][0] instanceof Wall)
				&& !(mapElementArray[i][0] instanceof InvisibleWall)
				&& !(mapElementArray[i][0] instanceof GhostSpawnPoint)) {
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
	 * if we want ghost to move more intelligentlly, we need a new fork list which contains more turn choince.
	 * it would be a lot easier that we build 2 fork list, one for pacman and one for ghost
	 * @param i
	 *            row of MapElement[][]
	 * @param j
	 *            col of MapElement[][]
	 * @return if it is a fork road
	 */
	private List<Direction> getForksForPacman(int i, int j) {
		
		List<Direction> forks=new LinkedList<>();
		if (isLeftWalkableP(i, j))
			forks.add(Direction.LEFT);
		if (isRightWalkableP(i, j))
			forks.add(Direction.RIGHT);
		if (isUpWalkableP(i, j))
			forks.add(Direction.UP);
		if (isDownWalkableP(i, j))
			forks.add(Direction.DOWN);
		
		//check if it is a rand paar(edge that teleport pacman to the otherside of map)
		if(i==0&&isDownWalkableP(height-2, j)&&isLeftWalkableP(height-1, j+1)&&isRightWalkableP(height-1, j-1)){
			forks.add(Direction.UP);
		}else if(i==height-1&&isUpWalkableP(1, j)&&isLeftWalkableP(0, j+1)&&isRightWalkableP(0, j-1)){
			forks.add(Direction.DOWN);
		}else if(j==0&&isRightWalkableP(i, width-2)&isDownWalkableP(i-1, j)&&isUpWalkableP(i+1, j)){
			forks.add(Direction.LEFT);
		}else if(j==width-1&&isLeftWalkableP(i, 1)&isDownWalkableP(i-1, 0)&&isUpWalkableP(i+1, 0)){
			forks.add(Direction.RIGHT);
		}
				
			return forks;
		
	}
	/**
	 * check if element on given direction is for pacman walkable
	 * @param i
	 * @param j
	 * @return
	 */
	private boolean isLeftWalkableP(int i, int j) {
		if (j == 0) {
			return false;
		} else {
			return (mapElementStringArray[i][j - 1].equals(" ")||mapElementStringArray[i][j - 1].equals("P")
					|| mapElementStringArray[i][j - 1].equals("S") || mapElementStringArray[i][j - 1]
					.equals("U")||mapElementStringArray[i][j - 1].equals("T")||mapElementStringArray[i][j - 1].equals("G"));
		}
	}

	private boolean isRightWalkableP(int i, int j) {
		if (j == width - 1) {
			return false;
		} else {
			return (mapElementStringArray[i][j + 1].equals(" ")||mapElementStringArray[i][j + 1].equals("P")
					|| mapElementStringArray[i][j + 1].equals("S") || mapElementStringArray[i][j + 1]
					.equals("U")||mapElementStringArray[i][j + 1].equals("T")|mapElementStringArray[i][j + 1].equals("G"));
		}
	}

	private boolean isUpWalkableP(int i, int j) {
		if (i == 0) {
			return false;
		} else {
			return (mapElementStringArray[i-1][j].equals(" ")||mapElementStringArray[i-1][j].equals("P")
					|| mapElementStringArray[i-1][j].equals("S") || mapElementStringArray[i-1][j]
					.equals("U")||mapElementStringArray[i-1][j].equals("T")||mapElementStringArray[i-1][j].equals("G"));
		}
	}

	private boolean isDownWalkableP(int i, int j) {
		if (i == height - 1) {
			return false;
		} else {
			return (mapElementStringArray[i+1][j].equals(" ")||mapElementStringArray[i+1][j].equals("P")
					|| mapElementStringArray[i+1][j].equals("S") || mapElementStringArray[i+1][j]
					.equals("U")||mapElementStringArray[i+1][j].equals("T")||mapElementStringArray[i+1][j].equals("G"));
		}
	}
	
private List<Direction> getForksForGhost(int i, int j) {
		
		List<Direction> forks=new LinkedList<>();
		if (isLeftWalkableG(i, j))
			forks.add(Direction.LEFT);
		if (isRightWalkableG(i, j))
			forks.add(Direction.RIGHT);
		if (isUpWalkableG(i, j))
			forks.add(Direction.UP);
		if (isDownWalkableG(i, j))
			forks.add(Direction.DOWN);
		
		//check if it is a rand paar(edge that teleport pacman to the otherside of map)
		if(i==0&&isDownWalkableG(height-2, j)&&isLeftWalkableG(height-1, j+1)&&isRightWalkableG(height-1, j-1)){
			forks.add(Direction.UP);
		}else if(i==height-1&&isUpWalkableG(1, j)&&isLeftWalkableG(0, j+1)&&isRightWalkableG(0, j-1)){
			forks.add(Direction.DOWN);
		}else if(j==0&&isRightWalkableG(i, width-2)&isDownWalkableG(i-1, j)&&isUpWalkableG(i+1, j)){
			forks.add(Direction.LEFT);
		}else if(j==width-1&&isLeftWalkableG(i, 1)&isDownWalkableG(i-1, 0)&&isUpWalkableG(i+1, 0)){
			forks.add(Direction.RIGHT);
		}
		
		return forks;
		
		
	}

/**
 * check if element in given direction is for ghost walkable
 * @param i
 * @param j
 * @return
 */
private boolean isLeftWalkableG(int i, int j) {
	if (j == 0) {
		return false;
	} else {
		return (mapElementStringArray[i][j - 1].equals(" ")||mapElementStringArray[i][j - 1].equals("P")
				|| mapElementStringArray[i][j - 1].equals("S") || mapElementStringArray[i][j - 1]
				.equals("U")||mapElementStringArray[i][j - 1].equals("T")||mapElementStringArray[i][j - 1].equals("G")
				||mapElementStringArray[i][j - 1].equals("B"));
	}
}

private boolean isRightWalkableG(int i, int j) {
	if (j == width - 1) {
		return false;
	} else {
		return (mapElementStringArray[i][j + 1].equals(" ")||mapElementStringArray[i][j + 1].equals("P")
				|| mapElementStringArray[i][j + 1].equals("S") || mapElementStringArray[i][j + 1]
				.equals("U")||mapElementStringArray[i][j + 1].equals("T")||mapElementStringArray[i][j + 1].equals("G")
				||mapElementStringArray[i][j + 1].equals("B"));
	}
}

private boolean isUpWalkableG(int i, int j) {
	if (i == 0) {
		return false;
	} else {
		return (mapElementStringArray[i-1][j].equals(" ")||mapElementStringArray[i-1][j].equals("P")
				|| mapElementStringArray[i-1][j].equals("S") || mapElementStringArray[i-1][j]
				.equals("U")||mapElementStringArray[i-1][j].equals("T")||mapElementStringArray[i-1][j].equals("G")
				||mapElementStringArray[i-1][j].equals("B"));
	}
}

private boolean isDownWalkableG(int i, int j) {
	if (i == height - 1) {
		return false;
	} else {
		return (mapElementStringArray[i+1][j].equals(" ")||mapElementStringArray[i+1][j].equals("P")
				|| mapElementStringArray[i+1][j].equals("S") || mapElementStringArray[i+1][j]
				.equals("U")||mapElementStringArray[i+1][j].equals("T")||mapElementStringArray[i+1][j].equals("G")
				||mapElementStringArray[i+1][j].equals("B"));
	}
}

/**
 * check which type does this wall belong to, from 16 different types
 * @param i row
 * @param j col
 * @return type of wall
 */
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
	
	/**
	 * find out all points, from which figur can go from one side of map to the other
	 * and add richtige directions to forksForPacman list and forksForGhost list
	 * 
	 * z.B. 
	 * XXXXXXXXXX
	 * @        @
	 * XXXXXXXXXX
	 * the 2 '@' symbol have after initElementCoordinate only one direction in fork list:LEFT(RIGHT)
	 * LEFT and RIGHT will be added to their forks list after this method
	 * 
	 */
	/*private void addDirectionToRandPaar(){
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if(i==0&&mapElementArray[i][j] instanceof Road&&mapElementArray[height-1][j] instanceof Road){
					((Road)mapElementArray[i][j]).getForksForGhost().add(Direction.UP);
					((Road)mapElementArray[i][j]).getForksForGhost().add(Direction.DOWN);
					((Road)mapElementArray[height-1][j]).getForksForGhost().add(Direction.UP);
					((Road)mapElementArray[height-1][j]).getForksForGhost().add(Direction.DOWN);
					//check if this point is also for pacman walkable
					//to reduce the code, we used a different if here
					if(isUpWalkableP(1, j)&&isDownWalkableP(height-2, j)){
						((Road)mapElementArray[i][j]).getForksForPacman().add(Direction.UP);
						((Road)mapElementArray[i][j]).getForksForPacman().add(Direction.DOWN);
						((Road)mapElementArray[height-1][j]).getForksForPacman().add(Direction.UP);
						((Road)mapElementArray[height-1][j]).getForksForPacman().add(Direction.DOWN);
					}
					removeDuplicateDirection(i, j);
					removeDuplicateDirection(height-1, j);
				}
				if(j==0&&mapElementArray[i][j] instanceof Road&&mapElementArray[i][width-1] instanceof Road){
					((Road)mapElementArray[i][j]).getForksForGhost().add(Direction.LEFT);
					((Road)mapElementArray[i][j]).getForksForGhost().add(Direction.RIGHT);
					((Road)mapElementArray[i][width-1]).getForksForGhost().add(Direction.LEFT);
					((Road)mapElementArray[i][width-1]).getForksForGhost().add(Direction.RIGHT);
					//check if this point is also for pacman walkable
					//to reduce the code, we used a different if here
					if(isUpWalkableP(1, j)&&isDownWalkableP(height-2, j)){
						((Road)mapElementArray[i][j]).getForksForPacman().add(Direction.LEFT);
						((Road)mapElementArray[i][j]).getForksForPacman().add(Direction.RIGHT);
						((Road)mapElementArray[i][width-1]).getForksForPacman().add(Direction.LEFT);
						((Road)mapElementArray[i][width-1]).getForksForPacman().add(Direction.RIGHT);
					}
					removeDuplicateDirection(i, j);
					removeDuplicateDirection(i, width-1);
				}
			}
			
		}
	}*/
	
	/**
	 * delet duplicate fork directions of a Road Object
	 * duplicate directions in forksForGhost list may affect ghost's random turning choice
	 * all directions(except turning back) should have same chance to get choosen
	 * 
	 */
	/*private void removeDuplicateDirection(int row, int col){
		//remove duplicate fork direction in forkForPacman(dosen't really matter if there are duplicate
		//because pac man don't get turn direction randomly, but ghost does
		HashSet<Direction> t_P = new HashSet<Direction>(((Road)mapElementArray[row][col]).getForksForPacman());
		((Road)mapElementArray[row][col]).getForksForPacman().clear();
		((Road)mapElementArray[row][col]).getForksForPacman().addAll(t_P);
		
		//remove duplicate fork direction in forkForGhos
		HashSet<Direction> t_G = new HashSet<Direction>(((Road)mapElementArray[row][col]).getForksForGhost());
		((Road)mapElementArray[row][col]).getForksForGhost().clear();
		((Road)mapElementArray[row][col]).getForksForGhost().addAll(t_G);

	}*/

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				sb.append(mapElementStringArray[i][j]);
			}
			if(i!=height-1)
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

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public List<PowerUp> getPowerUps() {
		return powerUps;
	}

	public List<SpeedUp> getSpeedUps() {
		return speedUps;
	}

	public List<Teleporter> getTeleporters() {
		return teleporters;
	}

	public List<Dot> getDots() {
		return dots;
	}
	
	public String getFilePath(){
		
		return mapFile.getPath();
		
	}

	public Pacman getPacman() {
		return pacman;
	}

	public List<Ghost> getGhosts() {
		return ghosts;
	}

	public String[][] getMapElementStringArray() {
		return mapElementStringArray;
	}

	public void setMapElementStringArray(String[][] mapElementStringArray) {
		this.mapElementStringArray = mapElementStringArray;
	}

}
