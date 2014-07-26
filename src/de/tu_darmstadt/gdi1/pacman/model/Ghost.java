package de.tu_darmstadt.gdi1.pacman.model;

import org.newdawn.slick.geom.Vector2f;

public class Ghost extends Figur {

	public Ghost(Vector2f startPosition, MapElement[][] mapElementArray) {
		super(startPosition, mapElementArray);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Direction turnDirection, int delta) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isPointWalkable(int row, int col) {
		if ((mapElementArray[row][col] instanceof Item)
				|| (mapElementArray[row][col] instanceof Teleporter)
				|| (mapElementArray[row][col] instanceof PlayerSpawnPoint)
				|| (mapElementArray[row][col] instanceof GhostSpawnPoint)
				|| (mapElementArray[row][col] instanceof InvisibleWall))
			return true;
		else
			return false;
	}

}
