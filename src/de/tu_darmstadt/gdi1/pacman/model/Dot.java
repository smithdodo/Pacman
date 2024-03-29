package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;


public class Dot extends Item{

	public Dot(Vector2f position, List<Direction> forksP, List<Direction> forksG) {
		
		super(position, forksP, forksG);
		
	}
	
	@Override
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(" Dot.");
		return sb.toString();
		
	}
	
}
