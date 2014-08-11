package de.tu_darmstadt.gdi1.pacman.service;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.pacman.model.Direction;
import de.tu_darmstadt.gdi1.pacman.model.GhostSpawnPoint;
import de.tu_darmstadt.gdi1.pacman.model.InvisibleWall;
import de.tu_darmstadt.gdi1.pacman.model.MapElement;
import de.tu_darmstadt.gdi1.pacman.model.Pacman;
import de.tu_darmstadt.gdi1.pacman.model.Road;

public class UpdatePacmanPosition {

	int height;
	int width;

	Pacman pacman;
	int checkPointRow;
	int checkPointCol;
	int speedUpFactor = 1;
	Vector2f currentPosition;
	Direction currentDirection;
	Shape hitBox;

	MapElement[][] mapElementArray;


	public UpdatePacmanPosition(Pacman pacman, int speedUpFactor,
			MapElement[][] m) {

		super();
		this.pacman=pacman;
		this.mapElementArray = m;
		height = mapElementArray.length;
		try {
			width = mapElementArray[0].length;
		} catch (Exception e) {
			width = 0;
			e.printStackTrace();
		}
		this.currentPosition = pacman.getCurrentPosition();
		this.checkPointRow = pacman.getCheckPointRow();
		this.checkPointCol = pacman.getCheckPointCol();
		this.currentDirection = pacman.getCurrentDirection();
		this.hitBox = pacman.getHitBox();

	}

	public void update(Direction turnDirection, int delta) {
		if (currentPosition.equals(new Vector2f(checkPointCol * 35,
				checkPointRow * 35))) {
			updateCheckPoint(turnDirection);
		} else {
			updateTurnAround(turnDirection);
		}
		if (speedUpFactor < 4)
			updateCurrentPosition(delta * speedUpFactor);
		else
			updateCurrentPosition(delta * 3);// pacman can only eat maximal 2
												// speedup at a time
		
		pacman.setCheckPointRow(checkPointRow);
		pacman.setCheckPointCol(checkPointCol);
		pacman.setCurrentDirection(currentDirection);
		pacman.setCurrentPosition(currentPosition);
		System.out.println("can turn -> "+((Road)mapElementArray[checkPointRow][checkPointCol]).getForksForPacman().toString()+"@"+checkPointRow+" "+checkPointCol);
		
	}

	/**
	 * update the check point
	 * 
	 * @param turnDirection
	 */
	public void updateCheckPoint(Direction turnDirection) {
//		System.out.println("updateCheckPoint-> recieve turnDir->"+turnDirection);
		switch (turnDirection) {
		case LEFT:
			if (checkPointCol == 0
					&& isElementWalkable(checkPointRow, width - 1)) {
				checkPointCol = width - 1;
				currentDirection = turnDirection;
			} else if (canTurnToDirection(turnDirection)) {
				currentDirection = turnDirection;
				setCheckPointToNextFork();
			} else {
				setCheckPointToNextFork();
			}
			break;
		case RIGHT:
			if (checkPointCol == width - 1
					&& isElementWalkable(checkPointRow, 0)) {
				checkPointCol = 0;
				currentDirection = turnDirection;
			} else if (canTurnToDirection(turnDirection)) {
				currentDirection = turnDirection;
				setCheckPointToNextFork();
//				System.out.println("next checkpoint: "+checkPointRow+" "+checkPointCol);

			} else {
				setCheckPointToNextFork();
			}
			break;
		case UP:
			if (checkPointRow == 0
					&& isElementWalkable(height - 1, checkPointCol)) {
				checkPointRow = height - 1;
				currentDirection = turnDirection;
			} else if (canTurnToDirection(turnDirection)) {
				currentDirection = turnDirection;
				setCheckPointToNextFork();
			} else {
				setCheckPointToNextFork();
			}
			break;
		case DOWN:
			if (checkPointRow == height - 1
					&& isElementWalkable(0, checkPointCol)) {
				checkPointRow = 0;
				currentDirection = turnDirection;
			} else if (canTurnToDirection(turnDirection)) {
				currentDirection = turnDirection;
				setCheckPointToNextFork();
			} else {
				setCheckPointToNextFork();
			}
			break;
		default:
			break;
		}
	}

	/*
	 * 
	 * check if the element at given indext for pacman/ghost walkable
	 */
	protected boolean isElementWalkable(int row, int col) {

		if (mapElementArray[row][col] instanceof Road
				&& !(mapElementArray[row][col] instanceof GhostSpawnPoint)
				&& !(mapElementArray[row][col] instanceof InvisibleWall))
			return true;
		else
			return false;

	}

	/**
	 * check if pacman can turn to given direction at current position
	 * 
	 * @param turn
	 *            the direction that figur wants to turn
	 * @return boolean
	 */
	private boolean canTurnToDirection(Direction turn) {
		if (((Road) mapElementArray[checkPointRow][checkPointCol])
				.getForksForPacman().contains(turn)) {

			return true;
		} else {

			return false;
		}

	}

	/**
	 * if the next element along current moving direction is a Fork point, then
	 * set the check point to it
	 * 
	 * no arrayoutofboundsexception because element(instance of Road class) on
	 * edge is always fork
	 * 
	 * @param checkpointRow
	 * @param checkpointCol
	 * @param currentDirection
	 */
	protected void setCheckPointToNextFork() {

		try {
			switch (currentDirection) {
			case LEFT:
				if (isElementWalkable(checkPointRow, checkPointCol - 1)) {
//					System.out.println("yo->set to left-> current checkpoint c: "+checkPointCol);
					while (((Road) mapElementArray[checkPointRow][checkPointCol - 1])
							.getForksForPacman().isEmpty())
						checkPointCol -= 1;
					checkPointCol -= 1;
					System.out.println("set to left->"+checkPointCol);
				}
				// System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
				break;
			case RIGHT:
				if (isElementWalkable(checkPointRow, checkPointCol + 1)) {
					while (((Road) mapElementArray[checkPointRow][checkPointCol + 1])
							.getForksForPacman().isEmpty())
						checkPointCol += 1;
					checkPointCol += 1;
				}
//				 System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
				break;
			case UP:
				if (isElementWalkable(checkPointRow - 1, checkPointCol)) {
					while (((Road) mapElementArray[checkPointRow - 1][checkPointCol])
							.getForksForPacman().isEmpty())
						checkPointRow -= 1;
					checkPointRow -= 1;
				}
				// System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
				break;
			case DOWN:
				if (isElementWalkable(checkPointRow + 1, checkPointCol)) {
					while (((Road) mapElementArray[checkPointRow + 1][checkPointCol])
							.getForksForPacman().isEmpty())
						checkPointRow += 1;
					checkPointRow += 1;
				}
				// System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
				break;
			default:
				System.out.println("setCheckPointToNextFork-> did'n update");
				break;
			}
		} catch (Exception e) {
		}

	}

	/**
	 * pacman can turn around at any moment if player wants
	 * 
	 */
	private void updateTurnAround(Direction turnDirection) {

		switch (turnDirection) {
		case LEFT:
			if (currentDirection == Direction.RIGHT) {
				currentDirection = turnDirection;
				setCheckPointToNextFork();
			}
			break;
		case RIGHT:
			if (currentDirection == Direction.LEFT) {
				currentDirection = turnDirection;
				setCheckPointToNextFork();
			}
			break;
		case UP:
			if (currentDirection == Direction.DOWN) {
				currentDirection = turnDirection;
				setCheckPointToNextFork();
			}
			break;
		case DOWN:
			if (currentDirection == Direction.UP) {
				currentDirection = turnDirection;
				setCheckPointToNextFork();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * refresh figurs position on screen
	 * 
	 * @param delta
	 */
	protected void updateCurrentPosition(int delta) {

		float speed = delta * 0.15f;
//		System.out.println("checkpoint-->: "+checkPointRow+" "+checkPointCol);
//		System.out.println("current direction: "+currentDirection);

		switch (currentDirection) {
		case LEFT:
			currentPosition.x -= speed;
			if (currentPosition.x < checkPointCol * 35)
				currentPosition.x = checkPointCol * 35;
			break;
		case RIGHT:
			currentPosition.x += speed;
			if (currentPosition.x > checkPointCol * 35){
//				System.out.println("ubertreten checkpoint col: "+checkPointCol);
				currentPosition.x = checkPointCol * 35;
			}

			break;
		case UP:
			currentPosition.y -= speed;
			if (currentPosition.y < checkPointRow * 35)
				currentPosition.y = checkPointRow * 35;
			break;
		case DOWN:
			currentPosition.y += speed;
			if (currentPosition.y > checkPointRow * 35)
				currentPosition.y = checkPointRow * 35;
			break;

		default:
			break;
		}
		// update hit box
		hitBox.setLocation(currentPosition);

	}

}
