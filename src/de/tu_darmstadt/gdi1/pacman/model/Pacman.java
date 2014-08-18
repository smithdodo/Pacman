package de.tu_darmstadt.gdi1.pacman.model;

import org.newdawn.slick.geom.Vector2f;

public class Pacman extends Figur {

	private int lives;
	private boolean isPowerUp;
	
	public Pacman(Vector2f startPosition) {
		
		super(startPosition);
		this.lives = 3;
		
	}	

	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(" Lives: "+lives).append(" PowerUp: "+isPowerUp);
		sb.append(" Pacman.");
		return sb.toString();
	}
	
	public void dead(){
		lives--;
	}

	public int getLives() {
		return lives;
	}

	public boolean isPowerUp() {
		return isPowerUp;
	}


	public void setPowerUp(boolean isPowerUp) {
		this.isPowerUp = isPowerUp;
	}


	public void setLives(int lives) {
		this.lives = lives;
	}



}
