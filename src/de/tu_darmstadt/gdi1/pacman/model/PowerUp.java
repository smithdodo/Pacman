package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

public class PowerUp extends SpecialItem {

	public PowerUp(Vector2f position, List<Direction> forksP, List<Direction> forksG) {
	
		super(position, forksP, forksG);	
		
	}

	@Override
	public void activateItem(Pacman p) {

		if (!super.isEaten) {
			p.setPowerUp(true);
			p.setTimeStillPoweredUp(5000);
		}
		super.isEaten = true;
	}

}
