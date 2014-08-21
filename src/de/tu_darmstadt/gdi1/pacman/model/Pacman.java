package de.tu_darmstadt.gdi1.pacman.model;

import org.newdawn.slick.geom.Vector2f;

public class Pacman extends Figur {

	private int lives;
	private boolean isPowerUp;
	Integer score;//current play score
	int numOfDots;//number of still eatable dots in map
	
	//for tests
	int killGhost;//number of ghost that pacman has killed
	
	public Pacman(Vector2f startPosition) {
		
		super(startPosition);
		this.lives = 3;
		this.score=new Integer(0);
		killGhost=0;
		
	}	

	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(" Lives: "+lives).append(" PowerUp: "+isPowerUp).append(" restDots: "+numOfDots).append(" totalScore: "+score);
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


	public Integer getScore() {
		return score;
	}


	public void setScore(Integer score) {
		this.score = score;
	}


	public int getNumOfDots() {
		return numOfDots;
	}


	public void setNumOfDots(int numOfDots) {
		this.numOfDots = numOfDots;
	}


	public int getKillGhost() {
		return killGhost;
	}


	public void setKillGhost(int killGhost) {
		this.killGhost = killGhost;
	}



}
