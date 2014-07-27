package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public abstract class Figur {
	
	protected MapElement[][] mapElementArray;
	//it has the current coordinate of this figur on the screen
	protected Vector2f currentPosition;
	//index of the element in MapElementArray, which the figur is moving towards to;
	protected int checkPointRow, checkPointCol;
	int mapArrayHeight, mapArrayWidth;

	protected float speed;
	protected Direction currentDirection;
	protected Direction turnDirection;
	
	protected final float RADIUS = 17.5f;
	protected Shape hitBox;
	
	public Figur(Vector2f startPosition, MapElement[][] mapElementArray) {
		this.mapElementArray=mapElementArray;
		this.hitBox = new Circle (startPosition.x, startPosition.y, this.RADIUS);
		this.currentPosition = startPosition;
		checkPointRow=((int)startPosition.y)/35;
		checkPointCol=((int)startPosition.x)/35;
		mapArrayHeight=mapElementArray.length;
		mapArrayWidth=mapElementArray[0].length;
		this.speed = 0;
		this.currentDirection=Direction.RIGHT;
		this.turnDirection=Direction.STOP;
	}
	
	public abstract void update(Direction turnDirection, int delta);
	
	/**
	 * check if figur can turn to given direction at current position
	 * 
	 * @param turn 
	 *             the direction that figur wants to turn
	 * @return boolean
	 */
	protected abstract boolean canTurnToDirection(Direction turn);
	
	/**
	 * if the next element along current moving direction is a Fork point, then set the check point to it
	 * 
	 * no arrayoutofboundsexception because element(instance of Road class) on edge is always fork
	 * 
	 * @param checkpointRow
	 * @param checkpointCol
	 * @param currentDirection
	 */
	protected abstract void setCheckPointToNextFork();

	public void updateCheckPoint(Direction turnDirection){
	
			switch (turnDirection) {
			case LEFT:
				if(checkPointCol==0&&mapElementArray[checkPointRow][mapArrayWidth-1] instanceof Road){
					checkPointCol=mapArrayWidth-1;
					currentDirection=turnDirection;
				}
				else if (canTurnToDirection(turnDirection)) {
					currentDirection=turnDirection;
					setCheckPointToNextFork();
				}else {
					setCheckPointToNextFork();
				}
				break;
			case RIGHT:
				if(checkPointCol==mapArrayWidth-1&&mapElementArray[checkPointRow][0] instanceof Road){
					checkPointCol=0;
					currentDirection=turnDirection;
				}
				else if(canTurnToDirection(turnDirection)){
					currentDirection=turnDirection;
					setCheckPointToNextFork();

				}else {
					setCheckPointToNextFork();
				}
				break;
			case UP:
				if(checkPointRow==0&&mapElementArray[mapArrayHeight-1][checkPointCol] instanceof Road)
					checkPointRow=mapArrayHeight-1;
				else if (canTurnToDirection(turnDirection)) {
					currentDirection=turnDirection;
					setCheckPointToNextFork();
				}else {
					setCheckPointToNextFork();
				}
				break;
			case DOWN:
				if(checkPointRow==mapArrayHeight-1&&mapElementArray[0][checkPointCol] instanceof Road)
					checkPointRow=0;
				else if (canTurnToDirection(turnDirection)) {
					currentDirection=turnDirection;
					setCheckPointToNextFork();
				}else {
					setCheckPointToNextFork();
				}
				break;
			default:
				break;
			}
	}
	
	/**
	 * refresh figurs position on screen
	 * @param delta
	 */
	protected void updateCurrentPosition(int delta) {
		
		speed=delta*0.15f;
		
		switch (currentDirection) {
		case LEFT:
			currentPosition.x-=speed;
			if(currentPosition.x<checkPointCol*35)
				currentPosition.x=checkPointCol*35;
			break;
		case RIGHT:
			currentPosition.x+=speed;
			if(currentPosition.x>checkPointCol*35)
				currentPosition.x=checkPointCol*35;
			break;
		case UP:
			currentPosition.y-=speed;
			if(currentPosition.y<checkPointRow*35)
				currentPosition.y=checkPointRow*35;
			break;
		case DOWN:
			currentPosition.y+=speed;
			if(currentPosition.y>checkPointRow*35)
				currentPosition.y=checkPointRow*35;
			break;

		default:
			break;
		}
		//update hit box
		hitBox.setLocation(currentPosition);
		
	}
	
	/**
	 * some getter and setter that ghost and pacman both need
	 * @return
	 */
	public Vector2f getCurrentPosition() {
		return currentPosition;
	}

	public int getCheckPointRow() {
		return checkPointRow;
	}

	public void setCheckPointRow(int checkPointRow) {
		this.checkPointRow = checkPointRow;
	}

	public int getCheckPointCol() {
		return checkPointCol;
	}

	public void setCheckPointCol(int checkPointCol) {
		this.checkPointCol = checkPointCol;
	}

	public Direction getCurrentDirection() {
		return currentDirection;
	}

	public void setCurrentDirection(Direction currentDirection) {
		this.currentDirection = currentDirection;
	}

	public Shape getHitBox() {
		return hitBox;
	}

	public void setHitBox(Shape hitBox) {
		this.hitBox = hitBox;
	}

	public float getSpeed() {
		return speed;
	}
	
}
