package de.tu_darmstadt.gdi1.pacman.service;

import de.tu_darmstadt.gdi1.pacman.model.Direction;
import de.tu_darmstadt.gdi1.pacman.model.Figur;
import de.tu_darmstadt.gdi1.pacman.model.GhostSpawnPoint;
import de.tu_darmstadt.gdi1.pacman.model.InvisibleWall;
import de.tu_darmstadt.gdi1.pacman.model.MapElement;
import de.tu_darmstadt.gdi1.pacman.model.Road;

public class UpdatePacmanPosition extends UpdateFigurPosition{

	public UpdatePacmanPosition(Figur figur, int speedUpFactor,
			MapElement[][] m) {
		super(figur, speedUpFactor, m);
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
		
		return !((Road)mapElementArray[row][col]).getForksForPacman().isEmpty();

	}

}
