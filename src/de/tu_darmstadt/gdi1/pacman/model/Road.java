package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

public class Road extends MapElement{
	
	List<Direction> forks;

	
	public Road(Vector2f position, List<Direction> forks) {
		
		super(position);
		this.forks=forks;
		
	}


	public List<Direction> getForks() {
		
		return forks;
		
	}
	
	

}
