package de.tu_darmstadt.gdi1.pacman.model;


import org.newdawn.slick.geom.Vector2f;

public class Ghost extends Figur {

	public Ghost(Vector2f startPosition) {
		
		super(startPosition);
		
	}
	
	@Override
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(" Ghost.");
		return sb.toString();
		
	}
	
}
