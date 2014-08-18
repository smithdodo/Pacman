package de.tu_darmstadt.gdi1.pacman.service;

import java.util.List;
import java.util.Random;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.pacman.control.Control;
import de.tu_darmstadt.gdi1.pacman.model.Direction;
import de.tu_darmstadt.gdi1.pacman.model.Ghost;
import de.tu_darmstadt.gdi1.pacman.model.Item;
import de.tu_darmstadt.gdi1.pacman.model.MapElement;
import de.tu_darmstadt.gdi1.pacman.model.Pacman;
import de.tu_darmstadt.gdi1.pacman.model.PlayerSpawnPoint;

public class GenerateTeleport {
	
	Pacman pacman;
	MapElement[][] mapElements;
	List<Ghost> ghosts;
	int width, height;

	public GenerateTeleport(Pacman p, MapElement[][] m, List<Ghost> ghosts) {
		
		this.pacman=p;
		this.mapElements=m;
		this.ghosts=ghosts;
		this.width=m[0].length;
		this.height=m.length;
		
	}
	
	/**
	 * teleport pacman to a random position on map
	 * @param r
	 * @param pacmanTurnDirection the direction that pacman is going to turn to
	 */
	public void update(Random r, Control c){
		
		Vector2f teleportPosition = getTeleport(r);
		Shape hitBox=pacman.getHitBox();
		hitBox.setLocation(teleportPosition);
		pacman.setCheckPointRow((int)teleportPosition.y/35);
		pacman.setCheckPointCol((int)teleportPosition.x/35);
		pacman.setCurrentPosition(teleportPosition);
		pacman.setCurrentDirection(Direction.STOP);
		c.resetTurnDirection();
		
	}
	
	/*
	 * generate a valid position on screen that pacman will be teleported to
	 */
	private Vector2f getTeleport(Random r){
		
		int row, col;
		row=r.nextInt(height-1);
		col=r.nextInt(width-1);
		
		//generate index until it is valid
		//not wall, not ghost spawn point, and not right on a ghost
		while (((!(mapElements[row][col] instanceof Item))&&(!(mapElements[row][col] instanceof PlayerSpawnPoint)))|| ((mapElements[row][col] instanceof Item||mapElements[row][col] instanceof PlayerSpawnPoint)&&isGhost(row, col))){
			row=r.nextInt(height-1);
			col=r.nextInt(width-1);
		}

		return mapElements[row][col].getPosition().copy();
		
	}
	
	/*
	 * check if ghost's hit box contains the given coordinate
	 * 
	 */
	private boolean isGhost(int row, int col){
		
		boolean result=false;
		Vector2f teleportPosition = new Vector2f(mapElements[row][col].getPosition());
		for(Ghost g:ghosts){
			if(g.getHitBox().contains(teleportPosition.x, teleportPosition.y))
				result = true;
		}
		return result;
				
	}

}
