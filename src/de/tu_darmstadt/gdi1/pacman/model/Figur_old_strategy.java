package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public abstract class Figur_old_strategy {
	
	protected MapElement[][] mapElementArray;
	//it has the current coordinate of this figur on the screen
	protected Vector2f currentPosition;
	//index of the element in MapElementArray, which the figur is moving towards;
	protected int checkPointRow, checkPointCol;
	int mapArrayHeight, mapArrayWidth;

	protected float speed;
	protected Direction currentDirection;
	protected Direction turnDirection;
	
	protected final int RADIUS = 17;
	protected Shape hitBox;
	
	public Figur_old_strategy(Vector2f startPosition, MapElement[][] mapElementArray) {
		this.mapElementArray=mapElementArray;
		this.hitBox = new Circle (startPosition.x, startPosition.y, this.RADIUS);
		this.currentPosition = startPosition;
		checkPointRow=((int)startPosition.y)/35;
		checkPointCol=((int)startPosition.x)/35;
		mapArrayHeight=mapElementArray.length;
		mapArrayWidth=mapElementArray[0].length;
		System.out.println("maparray height: "+mapArrayHeight+" width: "+mapArrayWidth);
		this.speed = 0;
		this.currentDirection=Direction.RIGHT;
		this.turnDirection=Direction.STOP;
	}
	
	public abstract void update(Direction turnDirection, int delta);
	

	public void updateCheckPoint(Direction turnDirection) {

		if (currentPosition.equals(new Vector2f(checkPointCol * 35,
				checkPointRow * 35))) {
			try {
				switch (turnDirection) {
				case LEFT:
					if (checkPointCol > 0
							&& isPointWalkable(checkPointRow, checkPointCol-1)) {
						checkPointCol -= 1;
						currentDirection = turnDirection;
					} else if (checkPointCol == 0
							&& isPointWalkable(checkPointRow, mapArrayWidth - 1)) {
						checkPointCol = mapArrayWidth - 1;
						currentDirection = turnDirection;
					}
					break;
				case RIGHT:
					if (checkPointCol < mapArrayWidth-1
							&& isPointWalkable(checkPointRow, checkPointCol+1)) {
						checkPointCol += 1;
						currentDirection = turnDirection;
					} else if (checkPointCol == mapArrayWidth-1
							&& isPointWalkable(checkPointRow, 0)) {
						checkPointCol = 0;
						currentDirection = turnDirection;
					}else
						System.out.println("cant turn right. checkPointCol: "+checkPointCol);
					break;
				case UP:
					if (checkPointRow >0
							&& isPointWalkable(checkPointRow-1, checkPointCol)) {
						checkPointRow -= 1;
						currentDirection = turnDirection;
					} else if (checkPointRow == 0
							&& isPointWalkable(mapArrayHeight-1, checkPointCol)) {
						checkPointRow = mapArrayHeight-1;
						currentDirection = turnDirection;
					}
					else
						System.out.println("can't go up!");
					break;
				case DOWN:
					if (checkPointRow<mapArrayHeight-1
							&& isPointWalkable(checkPointRow+1, checkPointCol)) {
						checkPointRow += 1;
						currentDirection = turnDirection;
					} else if (checkPointRow == mapArrayHeight-1
							&& isPointWalkable(0, checkPointCol)) {
						checkPointRow = 0;
						currentDirection = turnDirection;
					}
					break;
				default:
					System.out.println("first round did't updated!");
					break;
				}
			} catch (Exception e) {
				System.out.println("index went wrong: " + checkPointRow + " "
						+ checkPointCol);
			}

		}

		// if the next check point has not been updated(figur didn't turn)
		// than update it to the next point along current direction
		if (turnDirection != Direction.STOP
				&& currentPosition.equals(new Vector2f(checkPointCol * 35,
						checkPointRow * 35))) {

			if (checkPointCol != 0 && checkPointCol != mapArrayWidth - 1
					&& checkPointRow != 0
					&& checkPointRow != mapArrayHeight - 1) {

				switch (currentDirection) {
				case LEFT:
					if (isPointWalkable(checkPointRow, checkPointCol-1))
						checkPointCol -= 1;
					break;
				case RIGHT:
					if (isPointWalkable(checkPointRow, checkPointCol+1))
						checkPointCol += 1;
					break;
				case UP:
					if (isPointWalkable(checkPointRow-1, checkPointCol))
						checkPointRow -= 1;
					break;
				case DOWN:
					if (isPointWalkable(checkPointRow+1, checkPointCol))
						checkPointRow += 1;
					break;

				default:
					break;
				}
			}
		}

		// System.out.println("next check point: row: "+checkPointRow+" "+checkPointCol);

	}
	
	protected void updateCurrentPosition(int delta) {
		
		speed=delta*0.15f;
		
		switch (currentDirection) {
		case LEFT:
			currentPosition.x-=speed;
//			if(currentPosition.x<0)
//				currentPosition.x=mapElementArray[checkPointRow][mapArrayWidth-1].getPosition().x+2;
			if(currentPosition.x<checkPointCol*35)
				currentPosition.x=checkPointCol*35;
			break;
		case RIGHT:
			currentPosition.x+=speed;
//			if(currentPosition.x>(mapArrayWidth-1)*35)
//				currentPosition.x=mapElementArray[checkPointRow][0].getPosition().x-2;
			if(currentPosition.x>checkPointCol*35)
				currentPosition.x=checkPointCol*35;
			break;
		case UP:
			currentPosition.y-=speed;
//			if(currentPosition.y<0)
//				currentPosition.y=mapElementArray[mapArrayHeight-1][checkPointCol].getPosition().y;
			if(currentPosition.y<checkPointRow*35)
				currentPosition.y=checkPointRow*35;
			break;
		case DOWN:
			currentPosition.y+=speed;
//			if(currentPosition.x>(mapArrayHeight-1)*35)
//				currentPosition.y=mapElementArray[0][checkPointCol].getPosition().y;
			if(currentPosition.y>checkPointRow*35)
				currentPosition.y=checkPointRow*35;
			System.out.println("cant go throw down: check pointRow: "+checkPointRow);
			break;

		default:
			break;
		}
		//update hit box
		hitBox.setLocation(currentPosition);
		
	}
	
	protected abstract boolean isPointWalkable(int row, int col);

	/*public void autoMove (MapElement[][] mapElementArray, Direction turnDirection) {
		switch (turnDirection) {
		case LEFT: 
			if (nextPosition == currentPosition){
				if (!(mapElementArray[(int) nextPosition.x][(int) nextPosition.y-1] instanceof Wall)) {
					//set destination
					nextPosition.y-=1;
					currentPosition = new Vector2f(currentPosition.x - speed,
							currentPosition.y);
					if (currentPosition.x < nextPosition.x)
						currentPosition = nextPosition;
				} 
			} else if (nextPosition.x < currentPosition.x) {
				currentPosition = new Vector2f(currentPosition.x - speed,
						currentPosition.y);
				if (currentPosition.x < nextPosition.x)
					currentPosition = nextPosition;
			}
			hitBox.setCenterX(currentPosition.x);
			hitBox.setCenterY(currentPosition.y);
			break;
		case RIGHT:
			if (nextPosition == currentPosition){
				if (!(mapElementArray[(int) nextPosition.x + 1][(int) nextPosition.y] instanceof Wall)) {
					nextPosition = new Vector2f(nextPosition.x + 1,
							nextPosition.y);
					currentPosition = new Vector2f(currentPosition.x + speed,
							currentPosition.y);
					if (currentPosition.x > nextPosition.x)
						currentPosition = nextPosition;
				} 
			} else if (nextPosition.x > currentPosition.x) {
				currentPosition = new Vector2f(currentPosition.x - speed,
						currentPosition.y);
				if (currentPosition.x > nextPosition.x)
					currentPosition = nextPosition;
			}
			hitBox.setCenterX(currentPosition.x);
			hitBox.setCenterY(currentPosition.y);
			break;
		case DOWN:
			if (nextPosition == currentPosition){
				if (!(mapElementArray[(int) nextPosition.x][(int) nextPosition.y + 1] instanceof Wall)) {
					nextPosition = new Vector2f(nextPosition.x,
							nextPosition.y + 1);
					currentPosition = new Vector2f(currentPosition.x,
							currentPosition.y + speed);
					if (currentPosition.y > nextPosition.y)
						currentPosition = nextPosition;
				} 
			} else if (nextPosition.y > currentPosition.y) {
				currentPosition = new Vector2f(currentPosition.x - speed,
						currentPosition.y);
				if (currentPosition.y > nextPosition.y)
					currentPosition = nextPosition;
			}
			hitBox.setCenterX(currentPosition.x);
			hitBox.setCenterY(currentPosition.y);
			break;
		case UP:
			if (nextPosition == currentPosition){
				if (!(mapElementArray[(int) nextPosition.x][(int) nextPosition.y - 1] instanceof Wall)) {
					nextPosition = new Vector2f(nextPosition.x,
							nextPosition.y - 1);
					currentPosition = new Vector2f(currentPosition.x,
							currentPosition.y - speed);
					if (currentPosition.y < nextPosition.y)
						currentPosition = nextPosition;
				} 
			} else if (nextPosition.y < currentPosition.y) {
				currentPosition = new Vector2f(currentPosition.x - speed,
						currentPosition.y);
				if (currentPosition.y < nextPosition.y)
					currentPosition = nextPosition;
			}
			hitBox.setCenterX(currentPosition.x);
			hitBox.setCenterY(currentPosition.y);
			break;
		}
	}*/
	
	public Vector2f getCurrentPosition() {
		return this.currentPosition;
	}

	public Direction getTurnDirection() {
		return this.turnDirection;
	}

	public void setTurnDirection(Direction turnDirection) {
		this.turnDirection = turnDirection;
	}

	public Direction getCurrentDirection() {
		return this.currentDirection;
	}
	
	public void setCurrentDirection(Direction currentDirection) {
		this.currentDirection = currentDirection;
	}

	public Shape getHitBox() {
		return hitBox;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}


	public void setCurrentPosition(Vector2f currentPosition) {
		this.currentPosition = currentPosition;
	}

}
