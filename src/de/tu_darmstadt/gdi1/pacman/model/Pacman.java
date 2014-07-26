package de.tu_darmstadt.gdi1.pacman.model;

import org.newdawn.slick.geom.Vector2f;

public class Pacman extends Figur {

	private int lives;

	public Pacman(Vector2f startPosition, MapElement[][] mapElementArray) {
		super(startPosition, mapElementArray);

		this.lives = 3;
	}

	@Override
	public void update(Direction turnDirection, int delta) {

		updateCheckPoint(turnDirection);
		updateCurrentPosition(delta);
		//
		System.out.println(toString());
		// next position can only be a instance of Item
		if ((mapElementArray[checkPointRow][checkPointCol] instanceof Item)
				&& hitBox.contains(
						mapElementArray[checkPointRow][checkPointCol]
								.getPosition().x,
						mapElementArray[checkPointRow][checkPointCol]
								.getPosition().y)) {
			((Item) mapElementArray[checkPointRow][checkPointCol])
					.setEaten(true);
			System.out.println("dot eaten!");
		}

	}


	protected boolean isPointWalkable(int row, int col) {
		if ((mapElementArray[row][col] instanceof Item)
				|| (mapElementArray[row][col] instanceof Teleporter)
				|| (mapElementArray[row][col] instanceof PlayerSpawnPoint))
			return true;
		else
			return false;
	}

	public String toString() {
		String statusString = "current position" + currentPosition.toString()
				+ "heading: " + currentDirection + " checkpoint row: "
				+ checkPointRow + " checkpoint col: " + checkPointCol;
		return statusString;
	}
}
