package de.tu_darmstadt.gdi1.pacman.control;

import java.util.List;
import java.util.Random;

import org.lwjgl.Sys;
import org.newdawn.slick.Music;

import de.tu_darmstadt.gdi1.pacman.model.Direction;
import de.tu_darmstadt.gdi1.pacman.model.Figur;
import de.tu_darmstadt.gdi1.pacman.model.Ghost;
import de.tu_darmstadt.gdi1.pacman.model.MapElement;
import de.tu_darmstadt.gdi1.pacman.model.MapReader;
import de.tu_darmstadt.gdi1.pacman.model.Pacman;
import de.tu_darmstadt.gdi1.pacman.model.PowerUp;
import de.tu_darmstadt.gdi1.pacman.model.Road;
import de.tu_darmstadt.gdi1.pacman.model.SpeedUp;
import de.tu_darmstadt.gdi1.pacman.service.ActivateItem;
import de.tu_darmstadt.gdi1.pacman.service.CollusionDetect;
import de.tu_darmstadt.gdi1.pacman.service.GenerateDirection;
import de.tu_darmstadt.gdi1.pacman.service.RespawnTimer;
import de.tu_darmstadt.gdi1.pacman.service.UpdateFigurPosition;
import de.tu_darmstadt.gdi1.pacman.service.UpdateGhostPosition;
import de.tu_darmstadt.gdi1.pacman.service.UpdatePacmanPosition;
import de.tu_darmstadt.gdi1.pacman.service.UpdatePowerUp;
import de.tu_darmstadt.gdi1.pacman.service.UpdateSpeedUp;

public class Control {
	
	MapElement[][] mapElements;
	List<Ghost> ghosts;
	Pacman pacman;
	public Direction pacmanTurnDirection;
	
	List<SpeedUp> speedUps;
	List<PowerUp> powerUps;
	
	Random random;
	
	//soundboard
	Music pacman_die_music;
	Music pacman_eat_dot_music;
	Music ghost_die_music;

	public Control(MapReader mr, List<Ghost> g, Pacman p, Random r) {
		
		super();
		this.mapElements=mr.getMapData();
		this.ghosts=g;
		this.pacman=p;
		this.random=r;
		this.pacmanTurnDirection=Direction.STOP;
		
		this.speedUps=mr.getSpeedUps();
		this.powerUps=mr.getPowerUps();
		
		try {
			pacman_die_music=new Music("res/soundboard/die.wav");
			pacman_eat_dot_music=new Music("res/soundboard/waka_waka.wav");
			ghost_die_music=new Music("res/soundboard/eating_ghost.wav");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void updatePacmanPosition(Direction turnDirection, int delta){
		
		UpdatePacmanPosition updater=new UpdatePacmanPosition(pacman, mapElements);
		if(turnDirection!=null){
			pacmanTurnDirection=turnDirection;
		}
		updater.update(pacmanTurnDirection, delta);

	}
	
	public void updateGhostPosition(int delta){
		//check if a power up is affecting pacman, if yes, ghost must run away form him
		boolean mustRunAway=false;
		for(PowerUp p:powerUps){
			if (p.isAffecting())
				mustRunAway=true;
		}
		//update all ghosts' position
		for(Ghost g:ghosts){

			if(g.isRespawning()){
				RespawnTimer rt=new RespawnTimer();
				rt.update(g, delta);
			}else{
				UpdateGhostPosition updater=new UpdateGhostPosition(g, mapElements);
				GenerateDirection grd=new GenerateDirection(g, mapElements, random, pacman);
				Direction turnDirection=grd.generateDirection(mustRunAway);
				updater.update(turnDirection, delta);
			}
		}
		
	}
	
	/**
	 * eat Item, activate special effect, update effect timer
	 */
	public void PacmanEatItem() {
		
		ActivateItem ai=new ActivateItem(pacman, mapElements);
		ai.activateItem(random, ghosts, this, pacman_eat_dot_music);
		
	}
	
	public void updateSpeedUp(int delta){
		
		UpdateSpeedUp us=new UpdateSpeedUp(speedUps, pacman);
		us.update(delta);
		
	}

	public void updatePowerUp(int delta) {
		
		UpdatePowerUp up=new UpdatePowerUp(powerUps, pacman);
		up.update(delta);
		
	}
	
	/**
	 * check if pacman eats ghost or ghost eat pacman
	 */
	public void collisionDetect(int delta){
		
		//check is pacman being affected by powerUp
		boolean pacmanCanEatGhost=false;
		for(PowerUp p:powerUps){
			if(p.isAffecting())
				pacmanCanEatGhost=true;
		}
		
		CollusionDetect cd=new CollusionDetect(ghosts, pacman);
		cd.update(pacmanCanEatGhost, delta, speedUps, powerUps, this, pacman_die_music, ghost_die_music);
		
	}
	
	/**
	 * update respawn timer
	 */
	public void updateRespawnTimer(Figur f, int delta) {
		RespawnTimer rt=new RespawnTimer();
		rt.update(f, delta);
	}
	
	public void resetTurnDirection(){
		this.pacmanTurnDirection=Direction.STOP;
	}

}
