package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

public abstract class MapElement {
	
	protected Vector2f position;
	
	public MapElement(Vector2f position){
		
		this.position=position;
		
	}

	public Vector2f getPosition() {
		return position;
	}	

}
