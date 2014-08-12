package de.tu_darmstadt.gdi1.pacman.model;

import org.newdawn.slick.geom.Vector2f;

public class Pacman extends Figur {

	private int lives;
	//the index of next element on pacman's radar
	int radarElementRow, radarElementCol;
	
	public Pacman(Vector2f startPosition) {
		
		super(startPosition);
		this.lives = 3;
		this.radarElementRow=checkPointRow;
		this.radarElementCol=checkPointCol;
		
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


	public int getRadarElementRow() {
		return radarElementRow;
	}

	public int getRadarElementCol() {
		return radarElementCol;
	}



}
