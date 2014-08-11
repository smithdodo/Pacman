package de.tu_darmstadt.gdi1.pacman.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.tu_darmstadt.gdi1.pacman.model.Direction;
import de.tu_darmstadt.gdi1.pacman.model.Ghost;
import de.tu_darmstadt.gdi1.pacman.model.MapElement;
import de.tu_darmstadt.gdi1.pacman.model.Road;

public class GenerateRandomDirectionForGhost {
	
	Ghost ghost;
	MapElement[][] mapElementArray;
	Random random;
	
	int checkPointRow, checkPointCol;
	Direction currentDirection;

	public GenerateRandomDirectionForGhost(Ghost g, MapElement[][] m, Random r) {
		
		this.ghost=g;
		this.mapElementArray=m;
		this.random=r;
		this.checkPointRow=g.getCheckPointRow();
		this.checkPointCol=g.getCheckPointCol();
		this.currentDirection=g.getCurrentDirection();
	}
	
	/**
	 * get a random direction from a checkpoint's forklist
	 * 
	 */
	public Direction generateATurnDirection(){
		
		Direction turnDirection;
		List<Direction> forks=new ArrayList<>();
		forks=((Road)mapElementArray[checkPointRow][checkPointCol]).getForksForGhost();
		System.out.println("choicie: "+forks.toString()+"@r/c: "+ checkPointRow+" "+checkPointCol);
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
		return aRandomDirection;
	}

}
