package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

public class Road extends MapElement{
	
	List<Direction> forksForPacman;
	List<Direction> forksForGhost;
	
	public Road(Vector2f position, List<Direction> forksP, List<Direction> forksG) {
		
		super(position);
		this.forksForPacman=forksP;
		this.forksForGhost=forksG;
	}

	public List<Direction> getForksForPacman() {
		return forksForPacman;
	}


	public List<Direction> getForksForGhost() {
		return forksForGhost;
	}
	
	

}
