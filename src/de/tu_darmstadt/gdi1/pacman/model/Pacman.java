package de.tu_darmstadt.gdi1.pacman.model;

import org.newdawn.slick.geom.Vector2f;

public class Pacman extends Figur {

	private int lives;
	//the index of next element on pacman's radar
	int radarElementRow, radarElementCol;
	private int speedUpFactor;//how much will pacman speeded up

	public Pacman(Vector2f startPosition) {
		
		super(startPosition);
		this.lives = 3;
		this.radarElementRow=checkPointRow;
		this.radarElementCol=checkPointCol;
		speedUpFactor=1;//max. is 4
		
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
			if(currentPosition.x!=0)
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
			if(currentPosition.y==0){
				System.out.println(this.toString());
			}
			if(currentPosition.y!=0)
				radarElementRow=(int)currentPosition.y/35+1;
			radarElementCol=(int)currentPosition.x/35;
			
				
			//if nextElementCol out of array bounds
			if(radarElementRow==mapArrayHeight&&mapElementArray[0][radarElementCol] instanceof Road){
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
