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
	
	public Figur(Vector2f startPosition) {
		this.mapElementArray=mapElementArray;
		this.hitBox = new Circle (startPosition.x, startPosition.y, this.RADIUS);
		this.currentPosition = startPosition;
		checkPointRow=((int)startPosition.y)/35;
		checkPointCol=((int)startPosition.x)/35;
		this.speed = 0;
		this.currentDirection=Direction.RIGHT;
		this.turnDirection=Direction.STOP;
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

	public void setCurrentPosition(Vector2f currentPosition) {
		this.currentPosition = currentPosition;
	}
	
}
