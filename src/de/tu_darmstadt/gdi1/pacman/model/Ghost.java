package de.tu_darmstadt.gdi1.pacman.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.newdawn.slick.geom.Vector2f;

public class Ghost extends Figur {
	
	Random random;
	private int spawnPoingRow, spawnPointCol;
	//a list that stores all fork direction of a checkpoint
	private List<Direction> forks;

	public Ghost(Vector2f startPosition) {
		
		super(startPosition);
		spawnPoingRow=checkPointRow;
		spawnPointCol=checkPointCol;
		
	}
	
	
	/**
	 * get a random direction from a checkpoint's forklist
	 * 
	 */
	private void setRandomDirection(){
		
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
			}
		}else {
			aRandomDirection=forks.get(0);

		}
		turnDirection=aRandomDirection;
	}
	
	/*
	 * 
	 * check if the element at given indext for pacman/ghost walkable
	 * 
	 * @return yes/no
	 */
	protected boolean isElementWalkable(int row, int col){
		
		return mapElementArray[row][col] instanceof Road;
		
	}

	/**
	 * check if figur can turn to given direction at current position
	 * 
	 * @param turn 
	 *             the direction that figur wants to turn
	 * @return boolean
	 */
	

	/**
	 * if the next element along current moving direction is a Fork point, then set the check point to it
	 * 
	 * no arrayoutofboundsexception because element(instance of Road class) on edge is always fork
	 * 
	 * @param checkpointRow
	 * @param checkpointCol
	 * @param currentDirection
	 */
	protected void setCheckPointToNextFork() {
		
		try{
			switch (currentDirection) {
		case LEFT:
			while(((Road)mapElementArray[checkPointRow][checkPointCol-1]).getForksForGhost().isEmpty())
				checkPointCol-=1;
			checkPointCol-=1;
			//System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
			break;
		case RIGHT:
			while(((Road)mapElementArray[checkPointRow][checkPointCol+1]).getForksForGhost().isEmpty())
				checkPointCol+=1;
			checkPointCol+=1;
			//System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
			break;
		case UP:
			while(((Road)mapElementArray[checkPointRow-1][checkPointCol]).getForksForGhost().isEmpty())
				checkPointRow-=1;
			checkPointRow-=1; 
//			System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
			break;
		case DOWN:
			while(((Road)mapElementArray[checkPointRow+1][checkPointCol]).getForksForGhost().isEmpty())
				checkPointRow+=1;
			checkPointRow+=1;
//			System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
			break;
		default:
//			System.out.println("setCheckPointToNextFork-> did'n update");
			break;
		}
		}catch(Exception e){
		}
		
	}
}
