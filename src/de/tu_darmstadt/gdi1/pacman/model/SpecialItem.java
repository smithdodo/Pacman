package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

public abstract class SpecialItem extends Item {

	private long activeTime;
	private float changedSpeed;

	public SpecialItem(Vector2f position, List<Direction> forks) {
		super(position, forks);
	}

	public long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(long activeTime) {
		this.activeTime = activeTime;
	}

	//public abstract void setEffect();

}
