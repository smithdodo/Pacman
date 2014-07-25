package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public abstract class Figur {
	
	protected MapElement[][] mapElementArray;
	//it has the current coordinate of this figur on the screen
	protected Vector2f currentPosition;
	//index of the element in MapElementArray, which the figur is moving towards;
	protected int checkPointRow, checkPointCol;

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
		this.speed = 0;
		this.currentDirection=Direction.RIGHT;
		this.turnDirection=Direction.STOP;
	}
	
	public abstract void update(Direction turnDirection, int delta);
	
	protected void updateCurrentPosition(int delta) {
		
		speed=delta*0.15f;
		
		switch (currentDirection) {
		case LEFT:
			currentPosition.x-=speed;
			if(currentPosition.x<checkPointCol*35)
				currentPosition.x=checkPointCol*35;
			break;
		case RIGHT:
			currentPosition.x+=speed;
			if(currentPosition.x>checkPointCol*35)
				currentPosition.x=checkPointCol*35;
			break;
		case UP:
			currentPosition.y-=speed;
			if(currentPosition.y<checkPointRow*35)
				currentPosition.y=checkPointRow*35;
			break;
		case DOWN:
			currentPosition.y+=speed;
			if(currentPosition.y>checkPointRow*35)
				currentPosition.y=checkPointRow*35;
			break;

		default:
			break;
		}
		//update hit box
		hitBox.setLocation(currentPosition);
		
	}

	/*public void autoMove (MapElement[][] mapElementArray, Direction turnDirection) {
		switch (turnDirection) {
		case LEFT: 
			if (nextPosition == currentPosition){
				if (!(mapElementArray[(int) nextPosition.x][(int) nextPosition.y-1] instanceof Wall)) {
					//set destination
					nextPosition.y-=1;
					currentPosition = new Vector2f(currentPosition.x - speed,
							currentPosition.y);
					if (currentPosition.x < nextPosition.x)
						currentPosition = nextPosition;
				} 
			} else if (nextPosition.x < currentPosition.x) {
				currentPosition = new Vector2f(currentPosition.x - speed,
						currentPosition.y);
				if (currentPosition.x < nextPosition.x)
					currentPosition = nextPosition;
			}
			hitBox.setCenterX(currentPosition.x);
			hitBox.setCenterY(currentPosition.y);
			break;
		case RIGHT:
			if (nextPosition == currentPosition){
				if (!(mapElementArray[(int) nextPosition.x + 1][(int) nextPosition.y] instanceof Wall)) {
					nextPosition = new Vector2f(nextPosition.x + 1,
							nextPosition.y);
					currentPosition = new Vector2f(currentPosition.x + speed,
							currentPosition.y);
					if (currentPosition.x > nextPosition.x)
						currentPosition = nextPosition;
				} 
			} else if (nextPosition.x > currentPosition.x) {
				currentPosition = new Vector2f(currentPosition.x - speed,
						currentPosition.y);
				if (currentPosition.x > nextPosition.x)
					currentPosition = nextPosition;
			}
			hitBox.setCenterX(currentPosition.x);
			hitBox.setCenterY(currentPosition.y);
			break;
		case DOWN:
			if (nextPosition == currentPosition){
				if (!(mapElementArray[(int) nextPosition.x][(int) nextPosition.y + 1] instanceof Wall)) {
					nextPosition = new Vector2f(nextPosition.x,
							nextPosition.y + 1);
					currentPosition = new Vector2f(currentPosition.x,
							currentPosition.y + speed);
					if (currentPosition.y > nextPosition.y)
						currentPosition = nextPosition;
				} 
			} else if (nextPosition.y > currentPosition.y) {
				currentPosition = new Vector2f(currentPosition.x - speed,
						currentPosition.y);
				if (currentPosition.y > nextPosition.y)
					currentPosition = nextPosition;
			}
			hitBox.setCenterX(currentPosition.x);
			hitBox.setCenterY(currentPosition.y);
			break;
		case UP:
			if (nextPosition == currentPosition){
				if (!(mapElementArray[(int) nextPosition.x][(int) nextPosition.y - 1] instanceof Wall)) {
					nextPosition = new Vector2f(nextPosition.x,
							nextPosition.y - 1);
					currentPosition = new Vector2f(currentPosition.x,
							currentPosition.y - speed);
					if (currentPosition.y < nextPosition.y)
						currentPosition = nextPosition;
				} 
			} else if (nextPosition.y < currentPosition.y) {
				currentPosition = new Vector2f(currentPosition.x - speed,
						currentPosition.y);
				if (currentPosition.y < nextPosition.y)
					currentPosition = nextPosition;
			}
			hitBox.setCenterX(currentPosition.x);
			hitBox.setCenterY(currentPosition.y);
			break;
		}
	}*/
	
	public Vector2f getCurrentPosition() {
		return this.currentPosition;
	}

	public Direction getTurnDirection() {
		return this.turnDirection;
	}

	public void setTurnDirection(Direction turnDirection) {
		this.turnDirection = turnDirection;
	}

	public Direction getCurrentDirection() {
		return this.currentDirection;
	}
	
	public void setCurrentDirection(Direction currentDirection) {
		this.currentDirection = currentDirection;
	}

	public Shape getHitBox() {
		return hitBox;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}


	public void setCurrentPosition(Vector2f currentPosition) {
		this.currentPosition = currentPosition;
	}

}
