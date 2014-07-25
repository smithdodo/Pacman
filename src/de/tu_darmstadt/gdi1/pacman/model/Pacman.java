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

		updateNextPosition(turnDirection);
		updateCurrentPosition(delta);

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

	public void updateNextPosition(Direction turnDirection) {

		MapElement nextElementL = mapElementArray[checkPointRow][checkPointCol - 1];
		MapElement nextElementR = mapElementArray[checkPointRow][checkPointCol + 1];
		MapElement nextElementU = mapElementArray[checkPointRow - 1][checkPointCol];
		MapElement nextElementD = mapElementArray[checkPointRow + 1][checkPointCol];

		if (currentPosition.equals(new Vector2f(checkPointCol * 35,
				checkPointRow * 35))) {
			switch (turnDirection) {
			case LEFT:
				if ((nextElementL instanceof Item)
						|| (nextElementL instanceof Teleporter)) {
					checkPointCol -= 1;
					currentDirection = turnDirection;
				}
				break;
			case RIGHT:
				if ((nextElementR instanceof Item)
						|| (nextElementR instanceof Teleporter)) {
					checkPointCol += 1;
					currentDirection = turnDirection;
				}
				break;
			case UP:
				if ((nextElementU instanceof Item)
						|| (nextElementU instanceof Teleporter)) {
					checkPointRow -= 1;
					currentDirection = turnDirection;
				}
				break;
			case DOWN:
				if ((nextElementD instanceof Item)
						|| (nextElementD instanceof Teleporter)) {
					checkPointRow += 1;
					currentDirection = turnDirection;
				}
				break;

			default:
				break;
			}

		}

		// if the next check point has not been updated(figur didn't turn)
		// than update it to the next point along current direction
		if (turnDirection != Direction.STOP
				&& currentPosition.equals(new Vector2f(checkPointCol * 35,
						checkPointRow * 35))) {

			switch (currentDirection) {
			case LEFT:
				if (!(nextElementL instanceof Wall))
					checkPointCol -= 1;
				break;
			case RIGHT:
				if (!(nextElementR instanceof Wall))
					checkPointCol += 1;
				break;
			case UP:
				if (!(nextElementU instanceof Wall))
					checkPointRow -= 1;
				break;
			case DOWN:
				if (!(nextElementD instanceof Wall))
					checkPointRow += 1;
				break;

			default:
				break;
			}
		}

	}
}
