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
	
	protected final int RADIUS = 17;
	protected Shape hitBox;
	
	public Figur(Vector2f startPosition, MapElement[][] mapElementArray) {
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
	protected abstract boolean isPointWalkable(int row, int col);
	

	public void updateCheckPoint(Direction turnDirection){
		
		//figur can turn back any time
		switch (turnDirection) {
		case LEFT:
			if(currentDirection==Direction.RIGHT){
				currentDirection=Direction.RIGHT;
				setCheckPointToNextFork();
			}
			break;
		case RIGHT:
			if(currentDirection==Direction.LEFT){
				currentDirection=Direction.LEFT;
				setCheckPointToNextFork();
			}
			break;
		case UP:
			if(currentDirection==Direction.DOWN){
				currentDirection=Direction.DOWN;
				setCheckPointToNextFork();
			}
			break;
		case DOWN:
			if(currentDirection==Direction.UP){
				currentDirection=Direction.UP;
				setCheckPointToNextFork();
			}
			break;
		default:
			break;
		}
		
		//a figur can turn it direction(not including turning back) only when it arrives a check point
		if (currentPosition.equals(new Vector2f(checkPointCol * 35,
				checkPointRow * 35))) {
			
			switch (turnDirection) {
			case LEFT:
				if(checkPointCol==0&&mapElementArray[checkPointRow][mapArrayWidth-1] instanceof Road){
					checkPointCol=mapArrayWidth-1;
					currentDirection=Direction.LEFT;
				}
				else if (((Road)mapElementArray[checkPointRow][checkPointCol]).getForks().contains(Direction.LEFT)) {
					
				}
				break;
			case RIGHT:
				if(checkPointCol==mapArrayWidth-1&&mapElementArray[checkPointRow][0] instanceof Road)
					checkPointCol=0;
				else
					setCheckPointToNextFork();
				break;
			case UP:
				if(checkPointRow==0&&mapElementArray[mapArrayHeight-1][checkPointCol] instanceof Road)
					checkPointRow=mapArrayHeight-1;
				else
					setCheckPointToNextFork();
				break;
			case DOWN:
				if(checkPointRow==mapArrayHeight-1&&mapElementArray[0][checkPointCol] instanceof Road)
					checkPointRow=0;
				else
					setCheckPointToNextFork();
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * if the next element along current moving direction is a Fork point, then set the check point to it
	 * 
	 * no arrayoutofboundsexception because element(instance of Road class) on edge is always fork
	 * 
	 * @param checkpointRow
	 * @param checkpointCol
	 * @param currentDirection
	 */
	private void setCheckPointToNextFork(){
		
		switch (currentDirection) {
		case LEFT:
			while(!((Road)mapElementArray[checkPointRow][checkPointCol-1]).getForks().isEmpty()){
				checkPointCol-=1;
			}
			break;
		case RIGHT:
			while(!((Road)mapElementArray[checkPointRow][checkPointCol+11]).getForks().isEmpty()){
				checkPointCol+=1;
			}
			break;
		case UP:
			while(!((Road)mapElementArray[checkPointRow][checkPointCol-1]).getForks().isEmpty()){
				checkPointRow-=1;
			}
			break;
		case DOWN:
			while(!((Road)mapElementArray[checkPointRow][checkPointCol-1]).getForks().isEmpty()){
				checkPointRow+=1;
			}
			break;

		default:
			break;
		}
	}
	
	/**
	 * check if figur can turn to given direction at current position
	 * 
	 * @param turn 
	 *             the direction that figur wants to turn
	 * @return boolean
	 */
	private boolean canTurnToDirection(Direction turn) {
		
		if(((Road)mapElementArray[checkPointRow][checkPointCol]).getForks().contains(turn))
			return true;
		else
			return false;
		
		
		
	}
	
}
