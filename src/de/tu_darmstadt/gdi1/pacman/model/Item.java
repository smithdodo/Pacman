package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;


public abstract class Item extends Road{

	protected boolean isEaten;
	
	public Item(Vector2f position, List<Direction> forksP, List<Direction> forksG) {
		super(position, forksP, forksG);
	}


	public boolean isEaten() {
		
		return isEaten;
	
	}

	public void setEaten(boolean isEaten) {
		this.isEaten = isEaten;
	}
	
	@Override
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		sb.append("positionRow: "+(int)this.getPosition().y/35+" positionCol: "+(int)this.getPosition().x/35+" isEaten: "+isEaten);
		return sb.toString();
		
	}
	

	


	
}
