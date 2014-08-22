package de.tu_darmstadt.gdi1.pacman.tests;

public class PacmanTestAdapterExtended1 extends PacmanTestAdapterMinimal implements PacmanTestInterfaceExtended1{

	public PacmanTestAdapterExtended1() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getViewDirection() {
		switch (control.getPacman().getCurrentDirection()) {
		case RIGHT:
			return 0;
		case LEFT:
			return 2;
		case UP:
			return 1;
		case DOWN:
			return 3;
		default:
			return -1;
		}
	}

}
