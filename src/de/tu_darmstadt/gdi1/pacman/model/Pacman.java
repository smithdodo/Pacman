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
