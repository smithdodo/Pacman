package de.tu_darmstadt.gdi1.pacman.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.geom.Vector2f;

public class Ghost extends Figur {
	
	Random random;
	//a list that stores all fork direction of a checkpoint
	private List<Direction> forks;

	public Ghost(Vector2f startPosition, MapElement[][] mapElementArray, Random random) {
		
		super(startPosition, mapElementArray);
		this.random=random;
		
	}
	
	

	@Override
	public void update(Direction turnDirection, int delta) {
		
		if (currentPosition.equals(new Vector2f(checkPointCol * 35,
				checkPointRow * 35))) {
		updateCheckPoint(getRandomDirection());
		}
		updateCurrentPosition(delta);
		
	}
	
	/**
	 * get a random direction from a checkpoint's forklist
	 * 
	 */
	private Direction getRandomDirection(){
		
		forks=((Road)mapElementArray[checkPointRow][checkPointCol]).getForksForGhost();
		int size=forks.size();
		Direction aRandomDirection;
		aRandomDirection=forks.get(random.nextInt(size));
		return aRandomDirection;
	}

	/**
	 * check if figur can turn to given direction at current position
	 * 
	 * @param turn 
	 *             the direction that figur wants to turn
	 * @return boolean
	 */
	@Override
	protected boolean canTurnToDirection(Direction turn) {
		if(((Road)mapElementArray[checkPointRow][checkPointCol]).getForksForGhost().contains(turn)){
			return true;
		}
		else{
			return false;
		}
		
	}

	/**
	 * if the next element along current moving direction is a Fork point, then set the check point to it
	 * 
	 * no arrayoutofboundsexception because element(instance of Road class) on edge is always fork
	 * 
	 * @param checkpointRow
	 * @param checkpointCol
	 * @param currentDirection
	 */
	@Override		
		protected void setCheckPointToNextFork() {
			
			try{
				switch (currentDirection) {
			case LEFT:
				while(((Road)mapElementArray[checkPointRow][checkPointCol-1]).getForksForPacman().isEmpty())
					checkPointCol-=1;
				checkPointCol-=1;
				System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
				break;
			case RIGHT:
				while(((Road)mapElementArray[checkPointRow][checkPointCol+1]).getForksForPacman().isEmpty())
					checkPointCol+=1;
				checkPointCol+=1;
				System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
				break;
			case UP:
				while(((Road)mapElementArray[checkPointRow-1][checkPointCol]).getForksForPacman().isEmpty())
					checkPointRow-=1;
				checkPointRow-=1;
				System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
				break;
			case DOWN:
				while(((Road)mapElementArray[checkPointRow+1][checkPointCol]).getForksForPacman().isEmpty())
					checkPointRow+=1;
				checkPointRow+=1;
				System.out.println("next checkPoint: "+checkPointRow+" "+checkPointCol);
				break;
			default:
				System.out.println("setCheckPointToNextFork-> did'n update");
				break;
			}
			}catch(Exception e){}
			
		}

}
