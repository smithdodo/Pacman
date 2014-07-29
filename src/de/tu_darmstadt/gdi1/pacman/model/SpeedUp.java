package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

public class SpeedUp extends SpecialItem {

	public SpeedUp(Vector2f position, List<Direction> forksP, List<Direction> forksG) {

		super(position, forksP, forksG);

	}

	@Override
	public void activateItem(Pacman p) {

		if (!super.isEaten) {
			p.setSpeedUp(true);
			p.setTimeStillSpeededUp(5000);
			int sf = p.getSpeedUpFactor();
			//die Geschwindigkeit von Pacman darf nur maximal 3 mal so
			//gross wie originale Geschwindigkeit
			if (sf < 3) {
				p.setSpeedUpFactor(++sf);
			}
		}
		super.isEaten = true;
	}

}
