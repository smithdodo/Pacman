package de.tu_darmstadt.gdi1.pacman.service;

import de.tu_darmstadt.gdi1.pacman.model.Figur;

/*
 * update the spawning time of a given figur
 */
public class RespawnTimer {

	public RespawnTimer() {
		// TODO Auto-generated constructor stub
	}
	
	public void update(Figur f, int delta){
		
		if(f.isRespawning()){
			float t=f.getRespawnTimer();
			f.setRespawnTimer(t-delta);
		}
		if(f.isRespawning()&&f.getRespawnTimer()<0){
			f.setRespawning(false);
		}
	}

}
