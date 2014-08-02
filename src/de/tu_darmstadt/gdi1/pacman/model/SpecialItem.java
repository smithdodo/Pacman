package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

public abstract class SpecialItem extends Item {

	protected long activeTime;
	
	public SpecialItem(Vector2f position, List<Direction> forksP,List<Direction> forksG) {
		
		super(position, forksP, forksG);
		
		}
	
	public abstract void update(Pacman p, List<Ghost> g, int delta);
	
	public long getActiveTime() {
		return activeTime;
	}

	protected void setActiveTime(long activeTime) {
		this.activeTime = activeTime;
	}

	//public abstract void setEffect();

}
