package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

public abstract class SpecialItem extends Item {

	protected long activeTime;
	protected boolean isAffecting;//is item affecting Pacman/Ghost
	
	public SpecialItem(Vector2f position, List<Direction> forksP,List<Direction> forksG) {
		
		super(position, forksP, forksG);
		isAffecting=false;
		
		}
		
	public long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(long activeTime) {
		this.activeTime = activeTime;
		isAffecting=true;
	}
	
	//activeTime count down
	public void tikTok(int delta){
		activeTime-=delta;
	}
	
	public void deactivate(){
		isAffecting=false;
		this.activeTime=-1;
	}
	
	public boolean isAffecting(){
		return isAffecting;
	}

	//public abstract void setEffect();

}
