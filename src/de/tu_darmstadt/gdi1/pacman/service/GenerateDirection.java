package de.tu_darmstadt.gdi1.pacman.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import de.tu_darmstadt.gdi1.pacman.model.Direction;
import de.tu_darmstadt.gdi1.pacman.model.Ghost;
import de.tu_darmstadt.gdi1.pacman.model.MapElement;
import de.tu_darmstadt.gdi1.pacman.model.Pacman;
import de.tu_darmstadt.gdi1.pacman.model.Road;

public class GenerateDirection {
	
	Ghost ghost;
	MapElement[][] mapElementArray;
	Random random;
	Pacman pacman;
	
	int checkPointRow, checkPointCol;
	Direction currentDirection;
	
	int width, height;

	public GenerateDirection(Ghost g, MapElement[][] m, Random r, Pacman p) {
		
		this.ghost=g;
		this.mapElementArray=m;
		this.random=r;
		this.pacman=p;
		this.checkPointRow=g.getCheckPointRow();
		this.checkPointCol=g.getCheckPointCol();
		this.currentDirection=g.getCurrentDirection();
		try{
			width=mapElementArray[0].length;
		}catch(Exception e){
			e.printStackTrace();
		}
		height=mapElementArray.length;
		
	}
	
	/**
	 * return a direction that ghost should turn to
	 * if ghost sees pacman, it will chase him(turn keep moving in current direction, no turning)
	 * if not, ghost will just turn into a random direction
	 * 
	 * @return Direction
	 * 
	 */
	public Direction generateDirection() {
		if (noticedPacman()) {
			if(pacman.isPowerUp()){
				return runAway();
			}else{
				return this.currentDirection;
			}
		}else if(ghost.getCurrentPosition().equals(mapElementArray[checkPointRow][checkPointCol].getPosition())){
			return randomDirection();
		}else {
			return currentDirection;
		}
		
	}
	
	/**
	 * check if ghost has an eye on pacman, if yes, ghost will chase pacman
	 * @param pacman
	 * @return
	 */
	private boolean noticedPacman(){
		
		boolean result=false;
			
		//is pacman on left
			if(pacman.getCurrentPosition().y==ghost.getCurrentPosition().y&&pacman.getCurrentPosition().x<ghost.getCurrentPosition().x){
				while (checkPointCol>0&&mapElementArray[checkPointRow][checkPointCol-1] instanceof Road) {
					checkPointCol--;
					if(((Road)mapElementArray[checkPointRow][checkPointCol]).getPosition().x<=pacman.getCurrentPosition().x){
						result=true;
						currentDirection=Direction.LEFT;
					}
				}
				if(mapElementArray[checkPointRow][checkPointCol].getPosition().x<=pacman.getCurrentPosition().x){
					result=true;
					currentDirection=Direction.LEFT;
				}
			}
			
		//is pacman on right
			if(pacman.getCurrentPosition().y==ghost.getCurrentPosition().y&&pacman.getCurrentPosition().x>ghost.getCurrentPosition().x){
				while (checkPointCol<width-1&&mapElementArray[checkPointRow][checkPointCol+1] instanceof Road) {
					checkPointCol++;
					if(((Road)mapElementArray[checkPointRow][checkPointCol]).getPosition().x>=pacman.getCurrentPosition().x){
						result=true;
						currentDirection=Direction.RIGHT;
					}
				}
				if(mapElementArray[checkPointRow][checkPointCol].getPosition().x>=pacman.getCurrentPosition().x){
					result=true;
					currentDirection=Direction.RIGHT;
				}
			}
			
			//is pacman on up
			if (pacman.getCurrentPosition().x==ghost.getCurrentPosition().x&&pacman.getCurrentPosition().y<ghost.getCurrentPosition().y) {
				while(checkPointRow>0&&mapElementArray[checkPointRow-1][checkPointCol] instanceof Road){
					checkPointRow--;
					if(((Road)mapElementArray[checkPointRow][checkPointCol]).getPosition().y<=pacman.getCurrentPosition().y){
						result=true;
						currentDirection=Direction.UP;
					}
				}
				if(mapElementArray[checkPointRow][checkPointCol].getPosition().y<=pacman.getCurrentPosition().y){
					result=true;
					currentDirection=Direction.UP;
				}
			}
			
			//is pacman on down
			if (pacman.getCurrentPosition().x==ghost.getCurrentPosition().x&&pacman.getCurrentPosition().y>ghost.getCurrentPosition().y) {
				while(checkPointRow<height-1&&mapElementArray[checkPointRow+1][checkPointCol] instanceof Road){
					checkPointRow++;
					if(((Road)mapElementArray[checkPointRow][checkPointCol]).getPosition().y>=pacman.getCurrentPosition().y){
						result=true;
						currentDirection=Direction.DOWN;
					}
				}
				if(mapElementArray[checkPointRow][checkPointCol].getPosition().y>=pacman.getCurrentPosition().y){
					result=true;
					currentDirection=Direction.DOWN;
				}
			}
			
			return result;

	}
	
	/**
	 * generate a random direction from a checkpoint's forklist
	 * 
	 */
	public Direction randomDirection(){
		
		Direction turnDirection;
		List<Direction> forks=new ArrayList<>();
//		System.out.println("checkpoint r/c->"+checkPointRow+" "+checkPointCol+" element->"+mapElementArray[checkPointRow][checkPointCol]);
		forks=((Road)mapElementArray[checkPointRow][checkPointCol]).getForksForGhost();
//		System.out.println("choicie: "+forks.toString()+"@r/c: "+ checkPointRow+" "+checkPointCol);
		int size=forks.size();
		Direction aRandomDirection = null;
		//ghost won't turn back at a fork, unless it is a dead end
		if(size>1){
			switch (currentDirection) {
			case LEFT:
				do {
					aRandomDirection=forks.get(random.nextInt(size));
				} while (aRandomDirection==Direction.RIGHT);
				break;
			case RIGHT:
				do {
					aRandomDirection=forks.get(random.nextInt(size));
				} while (aRandomDirection==Direction.LEFT);
				break;
			case UP:
				do {
					aRandomDirection=forks.get(random.nextInt(size));
				} while (aRandomDirection==Direction.DOWN);
				break;
			case DOWN:
				do {
					aRandomDirection=forks.get(random.nextInt(size));
				} while (aRandomDirection==Direction.UP);
				break;
				default:
					aRandomDirection=forks.get(random.nextInt(size));
			}
		}else {
			
			aRandomDirection=forks.get(0);

		}
		turnDirection=aRandomDirection;
		return aRandomDirection;
	}
	
	private Direction runAway(){
		
		switch (currentDirection) {
		case LEFT:
			return Direction.RIGHT;

		case RIGHT:
			return Direction.LEFT;
			
		case UP:
			return Direction.DOWN;
			
		case DOWN:
			return Direction.UP;
		
		default:
			return currentDirection;
		}
	}

}
