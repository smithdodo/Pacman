package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

public class SpeedUp extends SpecialItem {
	
	int SpeedUpFactor;//new speed will be SpeedUpFactor*normal speed
	
	public SpeedUp(Vector2f position, List<Direction> forksP, List<Direction> forksG) {

		super(position, forksP, forksG);
		this.SpeedUpFactor=1;

	}

	public int getSpeedUpFactor() {
		return SpeedUpFactor;
	}



}
