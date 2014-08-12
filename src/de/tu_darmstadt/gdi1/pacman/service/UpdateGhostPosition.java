package de.tu_darmstadt.gdi1.pacman.service;

import de.tu_darmstadt.gdi1.pacman.model.Direction;
import de.tu_darmstadt.gdi1.pacman.model.Figur;
import de.tu_darmstadt.gdi1.pacman.model.MapElement;
import de.tu_darmstadt.gdi1.pacman.model.Road;

public class UpdateGhostPosition extends UpdateFigurPosition{

	public UpdateGhostPosition(Figur figur,
			MapElement[][] m) {
		
		super(figur, m);
		
	}

	@Override
	protected boolean isElementWalkable(int row, int col) {

		return mapElementArray[row][col] instanceof Road;
		
	}

	@Override
	protected boolean canTurnToDirection(Direction turn) {
		
		if(((Road)mapElementArray[checkPointRow][checkPointCol]).getForksForGhost().contains(turn)){
			return true;
		}
		else{
			return false;
		}

	}

	@Override
	protected boolean isFork(int row, int col) {
		
		return !((Road)mapElementArray[row][col]).getForksForGhost().isEmpty();
		
	}

}
