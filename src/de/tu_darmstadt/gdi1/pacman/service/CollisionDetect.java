package de.tu_darmstadt.gdi1.pacman.service;

import java.util.List;

import org.newdawn.slick.geom.Shape;

import de.tu_darmstadt.gdi1.pacman.model.Ghost;
import de.tu_darmstadt.gdi1.pacman.model.Pacman;

public class CollisionDetect {

	List<Ghost> ghosts;
	Pacman pacman;
	
	public CollisionDetect(List<Ghost> g, Pacman p) {
		
		this.ghosts=g;
		this.pacman=p;
		
	}
	
	public void update(boolean pacmanCanEatGhost, int delta){
				
		if(pacmanCanEatGhost){
			for(Ghost g:ghosts){			
				//if collsion between pacman and ghost detected
				if(g.getHitBox().intersects(pacman.getHitBox())){
					g.setRespawning(true);
					g.setCurrentPosition(g.getSpawnPoint().copy());
					g.setCheckPointRow((int)g.getSpawnPoint().y/35);
					g.setCheckPointCol((int)g.getSpawnPoint().x/35);
					Shape hitBox=g.getHitBox();
					hitBox.setLocation(g.getCurrentPosition());
					g.setHitBox(hitBox);
				}
			}
		}else {
			for(Ghost g:ghosts){
				if(g.getHitBox().intersects(pacman.getHitBox())){
					pacman.setRespawning(true);
					pacman.setCurrentPosition(pacman.getSpawnPoint().copy());
					pacman.setCheckPointRow((int)pacman.getSpawnPoint().y/35);
					pacman.setCheckPointCol((int)pacman.getSpawnPoint().x/35);
					Shape hitBox=pacman.getHitBox();
					hitBox.setLocation(pacman.getCurrentPosition());
					pacman.setHitBox(hitBox);
					pacman.dead();
				}
			}
		}
		
		//if pacman has been killed, reset all ghosts to their spawn points
		if(pacman.isRespawning()){
			for(Ghost g:ghosts){
				g.setCurrentPosition(g.getSpawnPoint().copy());
				g.setCheckPointRow((int)g.getSpawnPoint().y/35);
				g.setCheckPointCol((int)g.getSpawnPoint().x/35);
				Shape hitBox=g.getHitBox();
				hitBox.setLocation(g.getCurrentPosition());
				g.setHitBox(hitBox);
			}
		}
	}

}