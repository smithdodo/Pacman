package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;


public abstract class Item extends Road{
	
	private boolean isEaten;
	
	public Item(Vector2f position, List<Direction> forks){
		
		super(position,forks);
		this.isEaten=false;
		
	}


	public boolean isEaten() {
		return isEaten;
	}


	public void setEaten(boolean isEaten) {
		this.isEaten = isEaten;
	}
	


	
}
