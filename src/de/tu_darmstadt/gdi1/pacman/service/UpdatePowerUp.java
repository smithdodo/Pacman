package de.tu_darmstadt.gdi1.pacman.service;

import de.tu_darmstadt.gdi1.pacman.model.Pacman;
import de.tu_darmstadt.gdi1.pacman.model.PowerUp;
import de.tu_darmstadt.gdi1.pacman.model.SpeedUp;

import java.util.List;

public class UpdatePowerUp {
	
	List<PowerUp> powerUps;
	Pacman pacman;

	public UpdatePowerUp(List<PowerUp> pu, Pacman p) {
		
		this.powerUps=pu;
		this.pacman=p;
		
	}
	
	public void update(int delta){
		
		for(PowerUp u:powerUps){
			if(u.isAffecting()){
				u.tikTok(delta);
			}
			if (u.getActiveTime()<0&&u.isAffecting()) {
				u.deactivate();
				pacman.setPowerUp(false);
			}
		}
	}

}
