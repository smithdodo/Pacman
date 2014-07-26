package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

public class PlayerSpawnPoint extends Road {
	
	List<Direction> forks;

	public PlayerSpawnPoint(Vector2f position, List<Direction> forks) {
		
		super(position, forks);
		
	}

}
