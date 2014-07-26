package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;
import java.util.Random;

import org.newdawn.slick.geom.Vector2f;

public class Teleporter extends Road {

	public Teleporter(Vector2f position, List<Direction> forks) {

		super(position,forks);

	}

	/*public Vector2f getTeleportPosition(MapElement[][] mapData, Random a) {
		int i,j;
		do {
			 i = a.nextInt(mapData.length);
			 j = a.nextInt(mapData[0].length);
		} while (!(mapData[i][j] instanceof Item)&& !mapData[i][j].isEffected());
		return new Vector2f(mapData[i][j].getPosition());
	}*/
}
