package de.tu_darmstadt.gdi1.pacman.model;

import org.newdawn.slick.geom.Vector2f;

public class Pacman extends Figur {

	private int lives;
	//the index of next element on pacman's radar
	int radarElementRow, radarElementCol;
	private int speedUpFactor;//how much will pacman speeded up

	public Pacman(Vector2f startPosition, MapElement[][] mapElementArray) {
		
		super(startPosition, mapElementArray);
		this.lives = 3;
		this.radarElementRow=checkPointRow;
		this.radarElementCol=checkPointCol;
		speedUpFactor=1;//max. is 4
		
	}

	@Override
	public void update(Direction turnDirection, int delta) {		
		
		if (currentPosition.equals(new Vector2f(checkPointCol * 35,
				checkPointRow * 35))) {
		updateCheckPoint(turnDirection);
		}else {
			updateTurnAround(turnDirection);
		}
		if(this.speedUpFactor<4)
			updateCurrentPosition(delta*this.speedUpFactor);
		else
			updateCurrentPosition(delta*3);//pacman can only eat maximal 2 speedup at a time
		updateRadar();
		
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
	
	/*
	 * 
	 * check if the element at given indext for pacman/ghost walkable
	 */
	public boolean isElementWalkable(int row, int col){
		
		if(mapElementArray[row][col] instanceof Road&&!(mapElementArray[row][col] instanceof GhostSpawnPoint)
				&&!(mapElementArray[row][col] instanceof InvisibleWall))
			return true;
		else 
			return false;
	
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
			
//			System.out.println("canTurnToDirection--> "+checkPointRow+" "+checkPointCol);
			return true;
		}
		else{
//			System.out.println("canTurnToDirection--> can't turn"+checkPointRow+" "+checkPointCol);

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
			if(isElementWalkable(checkPointRow, checkPointCol-1)){
			while(((Road)mapElementArray[checkPointRow][checkPointCol-1]).getForksForPacman().isEmpty())
				checkPointCol-=1;
			checkPointCol-=1;
			}
//			System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
			break;
		case RIGHT:
			if(isElementWalkable(checkPointRow, checkPointCol+1)){
			while(((Road)mapElementArray[checkPointRow][checkPointCol+1]).getForksForPacman().isEmpty())
				checkPointCol+=1;
			checkPointCol+=1;
			}
//			System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
			break;
		case UP:
			if(isElementWalkable(checkPointRow-1, checkPointCol)){
			while(((Road)mapElementArray[checkPointRow-1][checkPointCol]).getForksForPacman().isEmpty())
				checkPointRow-=1;
			checkPointRow-=1;
			}
//			System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
			break;
		case DOWN:
			if(isElementWalkable(checkPointRow+1, checkPointCol)){
			while(((Road)mapElementArray[checkPointRow+1][checkPointCol]).getForksForPacman().isEmpty())
				checkPointRow+=1;
			checkPointRow+=1;
			}
//			System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
			break;
		default:
			System.out.println("setCheckPointToNextFork-> did'n update");
			break;
		}
		}catch(Exception e){
		}
		
	}
	
	/**
	 * give pacman a radar to detect if it eats the item ahead
	 * 
	 * pacman only "look" at the first element ahead of it, if this element
	 * is a item, use Shape.intersected method to calculate if pacman eats it.
	 * 
	 * we use nextElementRow and nextElementCol to store the index of this element that pacman always aim at
	 * 
	 * the more dots there are on the map, the more effective this method is than iterating all dots on the map
	 * 
	 * 
	 */
	private void updateRadar(){
		
		//pacman always looks ahead, so we need to calculate this element's index first
		switch (currentDirection) {
		case LEFT:
			radarElementRow=(int)currentPosition.y/35;
			radarElementCol=(int)currentPosition.x/35;
			break;
		case RIGHT:
			radarElementRow=(int)currentPosition.y/35;
			radarElementCol=(int)currentPosition.x/35+1;
			//if nextElementCol out of array bounds
			if(radarElementCol==mapArrayWidth&&mapElementArray[radarElementRow][0] instanceof Road){
				radarElementCol=0;
			}else if(radarElementCol==mapArrayWidth){
				radarElementCol=mapArrayWidth-1;
			}
			break;
		case UP:
			radarElementRow=(int)currentPosition.y/35;
			radarElementCol=(int)currentPosition.x/35;
			break;
		case DOWN:
			radarElementRow=(int)currentPosition.y/35+1;
			radarElementCol=(int)currentPosition.x/35;
			//if nextElementCol out of array bounds
			if(radarElementRow==mapArrayHeight&&mapElementArray[0][radarElementRow] instanceof Road){
				radarElementRow=0;
			}else if(radarElementRow==mapArrayHeight){
				radarElementRow=mapArrayHeight-1;
			}
			break;
		default:
			break;
		}
		
		/*//if it is a dot and pacman hits it, set eaten
		if(mapElementArray[nextElementRow][nextElementCol] instanceof Item){
			if(hitBox.contains(((Item)mapElementArray[nextElementRow][nextElementCol]).getPosition().x,
					((Item)mapElementArray[nextElementRow][nextElementCol]).getPosition().y)){
				((Item)mapElementArray[nextElementRow][nextElementCol]).activateItem(this);
			}
		}*/

		
	}
	
	public String toString() {
		String statusString = "current position: " + currentPosition.toString()
				+ "heading: " + currentDirection + " checkpoint row: "
				+ checkPointRow + " checkpoint col: " + checkPointCol;
		return statusString;
	}


	public int getLives() {
		return lives;
	}

	public void setSpeedUpFactor(int speedUpFactor) {
		this.speedUpFactor = speedUpFactor;
	}

	public int getRadarElementRow() {
		return radarElementRow;
	}

	public int getRadarElementCol() {
		return radarElementCol;
	}

	public int getSpeedUpFactor() {
		return speedUpFactor;
	}


}
