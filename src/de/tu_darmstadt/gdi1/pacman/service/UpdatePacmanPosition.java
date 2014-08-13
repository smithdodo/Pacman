package de.tu_darmstadt.gdi1.pacman.service;

import java.util.List;

import de.tu_darmstadt.gdi1.pacman.model.Direction;
import de.tu_darmstadt.gdi1.pacman.model.Figur;
import de.tu_darmstadt.gdi1.pacman.model.GhostSpawnPoint;
import de.tu_darmstadt.gdi1.pacman.model.InvisibleWall;
import de.tu_darmstadt.gdi1.pacman.model.MapElement;
import de.tu_darmstadt.gdi1.pacman.model.Road;

public class UpdatePacmanPosition extends UpdateFigurPosition{

	public UpdatePacmanPosition(Figur figur,
			MapElement[][] m) {
		super(figur, m);
	}

	@Override
	protected boolean isElementWalkable(int row, int col) {

		if (mapElementArray[row][col] instanceof Road
				&& !(mapElementArray[row][col] instanceof GhostSpawnPoint)
				&& !(mapElementArray[row][col] instanceof InvisibleWall))
			return true;
		else
			return false;
	}

	@Override
	protected boolean canTurnToDirection(Direction turn) {
		
		if (((Road) mapElementArray[checkPointRow][checkPointCol])
				.getForksForPacman().contains(turn)) {
			return true;
		} else {
			return false;
		}

	}
	
	
	@Override
	protected boolean isFork(int row, int col) {
		
		boolean result=true;
		List<Direction> forks=((Road)mapElementArray[row][col]).getForksForPacman();
		int size=forks.size();
		
		if (size==2) {
			if(forks.contains(Direction.RIGHT)&&forks.contains(Direction.LEFT)){
				result=false;
			}else if (forks.contains(Direction.UP)&&forks.contains(Direction.DOWN)) {
				result=false;
			}
		}else if (size==0) {
			result=false;
		}
		return result;
	}

}
