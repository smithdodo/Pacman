package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

public class SpeedUp extends SpecialItem {
		
	public SpeedUp(Vector2f position, List<Direction> forksP, List<Direction> forksG) {

		super(position, forksP, forksG, 0.7f);

	}




}
