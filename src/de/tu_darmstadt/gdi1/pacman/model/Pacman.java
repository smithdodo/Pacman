package de.tu_darmstadt.gdi1.pacman.model;

import org.newdawn.slick.geom.Vector2f;

public class Pacman extends Figur {
	
	//the next element in mapElementArray that pacman looking(aiming) at
	private int nextElementRow, nextElementCol;
	private int lives;

	public Pacman(Vector2f startPosition, MapElement[][] mapElementArray) {
		
		super(startPosition, mapElementArray);
		nextElementRow=checkPointRow;
		nextElementCol=checkPointCol;
		this.lives = 3;
	}

	@Override
	public void update(Direction turnDirection, int delta) {
		
		if (currentPosition.equals(new Vector2f(checkPointCol * 35,
				checkPointRow * 35))) {
		updateCheckPoint(turnDirection);
		}else {
			updateTurnAround(turnDirection);
		}
		updateCurrentPosition(delta);
		eatDot();
		
	}
	
	
/**
 * pacman can turn around at any moment if player wants
 * 
 */
	private void updateTurnAround(Direction turnDirection) {
		
		switch (turnDirection) {
		case LEFT:
			if(currentDirection==Direction.RIGHT){
				currentDirection=turnDirection;
				setCheckPointToNextFork();
			}
			break;
		case RIGHT:
			if(currentDirection==Direction.LEFT){
				currentDirection=turnDirection;
				setCheckPointToNextFork();
			}
			break;
		case UP:
			if(currentDirection==Direction.DOWN){
				currentDirection=turnDirection;
				setCheckPointToNextFork();
			}
			break;
		case DOWN:
			if(currentDirection==Direction.UP){
				currentDirection=turnDirection;
				setCheckPointToNextFork();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * check if pacman can turn to given direction at current position
	 * 
	 * @param turn 
	 *             the direction that figur wants to turn
	 * @return boolean
	 */
	@Override
	protected boolean canTurnToDirection(Direction turn) {
//		System.out.println("called canTurnToD at: "+checkPointRow+" "+checkPointCol );
//		System.out.print("try to turn: "+turn+" this point contains: ");
//		System.out.println(((Road)mapElementArray[checkPointRow][checkPointCol]).getForksForPacman().toString());
		if(((Road)mapElementArray[checkPointRow][checkPointCol]).getForksForPacman().contains(turn)){
			return true;
		}
		else{
			return false;
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
	@Override
	protected void setCheckPointToNextFork() {
		
		try{
			switch (currentDirection) {
		case LEFT:
			while(((Road)mapElementArray[checkPointRow][checkPointCol-1]).getForksForPacman().isEmpty())
				checkPointCol-=1;
			checkPointCol-=1;
			System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
			break;
		case RIGHT:
			while(((Road)mapElementArray[checkPointRow][checkPointCol+1]).getForksForPacman().isEmpty())
				checkPointCol+=1;
			checkPointCol+=1;
			System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
			break;
		case UP:
			while(((Road)mapElementArray[checkPointRow-1][checkPointCol]).getForksForPacman().isEmpty())
				checkPointRow-=1;
			checkPointRow-=1;
			System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
			break;
		case DOWN:
			while(((Road)mapElementArray[checkPointRow+1][checkPointCol]).getForksForPacman().isEmpty())
				checkPointRow+=1;
			checkPointRow+=1;
			System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
			break;
		default:
			System.out.println("setCheckPointToNextFork-> did'n update");
			break;
		}
		}catch(Exception e){
		}
		
	}
	
	/**
	 * check if pacman eats a dot when it moves
	 * 
	 * pacman only "look" at the first one element ahead of it, if this element
	 * is a dot, use Shape.intersected method to calculate if pacman eats it.
	 * 
	 * we use nextElementRow and nextElementCol to store the index of this element that pacman always aim at
	 * 
	 * the more dots there are on the map, the more effective this method is than iterating all dots on the map
	 * 
	 * 
	 */
	private void eatDot(){
		//pacman always looks ahead, so we need to calculate this element's index first
		switch (currentDirection) {
		case LEFT:
			nextElementRow=(int)currentPosition.y/35;
			nextElementCol=(int)currentPosition.x/35;
			break;
		case RIGHT:
			nextElementRow=(int)currentPosition.y/35;
			nextElementCol=Math.min((int)currentPosition.x/35+1, mapArrayWidth);
			break;
		case UP:
			nextElementRow=(int)currentPosition.y/35;
			nextElementCol=(int)currentPosition.x/35;
			break;
		case DOWN:
			nextElementRow=Math.min((int)currentPosition.y/35+1, mapArrayHeight);
			nextElementCol=(int)currentPosition.x/35;
			break;
		default:
			break;
		}
		//TODO debug
		//if it is a dot and pacman hits it, set eaten
		try{
		if(mapElementArray[nextElementRow][nextElementCol] instanceof Item){
			if(hitBox.contains(((Item)mapElementArray[nextElementRow][nextElementCol]).getPosition().x,
					((Item)mapElementArray[nextElementRow][nextElementCol]).getPosition().y)){
				((Item)mapElementArray[nextElementRow][nextElementCol]).setEaten(true);
			}
		}
		}catch(Exception e){
			System.out.println("nr: "+nextElementRow+" nc: "+nextElementCol);
		}
		
	}
	
	public String toString() {
		String statusString = "current position" + currentPosition.toString()
				+ "heading: " + currentDirection + " checkpoint row: "
				+ checkPointRow + " checkpoint col: " + checkPointCol;
		return statusString;
	}


}
