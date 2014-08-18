package de.tu_darmstadt.gdi1.pacman.model;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public abstract class Figur {
	
	//current coordinate of this figur on the screen
	protected Vector2f currentPosition;
	//index of the element in MapElementArray, which the figur is moving towards to;
	protected int checkPointRow, checkPointCol;
	protected Vector2f spawnPoint;

	protected Direction currentDirection;
//	protected Direction turnDirection;
	protected Shape hitBox;	//collision detect
	
	private float SpeedUpFactor;//how much will pacman be speeded up
	
	private boolean isRespawning;
	private float respawnTimer;//respawn dauert 5000ms
	
	protected final float RADIUS = 16f;

	
	public Figur(Vector2f startPosition) {
		
		this.hitBox = new Circle (startPosition.x, startPosition.y, this.RADIUS);
		this.currentPosition = startPosition.copy();
		this.checkPointRow=((int)startPosition.y)/35;
		this.checkPointCol=((int)startPosition.x)/35;
		this.spawnPoint=startPosition.copy();
		this.currentDirection=Direction.RIGHT;
//		this.turnDirection=Direction.STOP;
		this.SpeedUpFactor=1;//max. is  4
		isRespawning=false;
		
	}
	
	@Override
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		sb.append("currentPositionX: "+currentPosition.x+" currentPositionY: "+currentPosition.y+" checkPointRow: "+checkPointRow+" checkPointCol: "+checkPointCol+" spawnPointR: "+(int)spawnPoint.y/35+" spawnPointC: "+(int)spawnPoint.x/35+" currentDirection: "+currentDirection+" hitBoxX: "+hitBox.getX()+" hitBoxY: "+hitBox.getY()+" respawnTimer: "+respawnTimer);
		return sb.toString();
		
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


	public void setCurrentPosition(Vector2f currentPosition) {
		this.currentPosition = currentPosition;
	}


	public float getSpeedUpFactor() {
		return SpeedUpFactor;
	}


	public void setSpeedUpFactor(float speedUpFactor) {
		SpeedUpFactor = speedUpFactor;
	}


	public boolean isRespawning() {
		return isRespawning;
	}


	public void setRespawning(boolean isRespawning) {
		this.isRespawning = isRespawning;
		setRespawnTimer(5000);
	}


	public float getRespawnTimer() {
		return respawnTimer;
	}


	public void setRespawnTimer(float respawnTimer) {
		this.respawnTimer = respawnTimer;
	}

	public Vector2f getSpawnPoint() {
		return spawnPoint;
	}
	
}
