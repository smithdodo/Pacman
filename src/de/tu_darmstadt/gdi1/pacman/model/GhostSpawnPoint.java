package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

public class GhostSpawnPoint extends SpawnPoint{

	public GhostSpawnPoint(Vector2f position, List<Direction> forksP, List<Direction> forksG) {
		
		super(position, forksP, forksG);
		
	}


}
