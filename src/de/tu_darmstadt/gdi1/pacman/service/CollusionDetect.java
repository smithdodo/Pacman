package de.tu_darmstadt.gdi1.pacman.service;

import java.util.List;

import org.newdawn.slick.Music;
import org.newdawn.slick.geom.Shape;

import de.tu_darmstadt.gdi1.pacman.control.Control;
import de.tu_darmstadt.gdi1.pacman.model.Direction;
import de.tu_darmstadt.gdi1.pacman.model.Ghost;
import de.tu_darmstadt.gdi1.pacman.model.Pacman;
import de.tu_darmstadt.gdi1.pacman.model.PowerUp;
import de.tu_darmstadt.gdi1.pacman.model.SpeedUp;

public class CollusionDetect {

	List<Ghost> ghosts;
	Pacman pacman;
	
	public CollusionDetect(List<Ghost> g, Pacman p) {
		
		this.ghosts=g;
		this.pacman=p;
		
		
	}
	
	/**
	 * detect the collusion betwenn pacman, ghosts and other map elements, then trigger activity
	 * 
	 * @param pacmanCanEatGhost if pacman is being powered up
	 * @param delta 
	 * @param s list of all speedups on the map
	 * @param p list of all powerups on the map
	 * @param c instance of Control, use to reset pacmanTurnDirection
	 * @param pd pacman dies music
	 * @param gd ghost dies music
	 */
	public void update(List<SpeedUp> s, List<PowerUp> p, Control c, Music pd, Music gd){
				
		if(pacman.isPowerUp()){
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
					//play music
					gd.play();
					//add score
					pacman.setScore(pacman.getScore()+500);
					pacman.setKillGhost(pacman.getKillGhost()+1);
				}
			}
		}else {
			for(Ghost g:ghosts){
				if(g.getHitBox().intersects(pacman.getHitBox())){
					
					pacman.setRespawning(true);
					pacman.setCurrentPosition(pacman.getSpawnPoint().copy());
					pacman.setCheckPointRow((int)pacman.getSpawnPoint().y/35);
					pacman.setCheckPointCol((int)pacman.getSpawnPoint().x/35);
					pacman.setCurrentDirection(Direction.STOP);
					c.resetTurnDirection();
					Shape hitBox=pacman.getHitBox();
					hitBox.setLocation(pacman.getCurrentPosition());
					pacman.setHitBox(hitBox);
					pacman.dead();//live-=1;
					pd.play();
					
					//reset all item
					for(PowerUp pu:p){
						pu.deactivate();
						float t=pacman.getSpeedUpFactor();
						if(t>1)
							t-=0.3;
						else {
							t=1;
						}
					}
					for(SpeedUp su:s){
						su.deactivate();
						float t=pacman.getSpeedUpFactor();
						if(t>1)
							t-=0.7;
						else {
							t=1;
						}
						pacman.setSpeedUpFactor(t);
					}
					
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
