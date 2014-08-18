package de.tu_darmstadt.gdi1.pacman.service;

import de.tu_darmstadt.gdi1.pacman.model.Pacman;
import de.tu_darmstadt.gdi1.pacman.model.PowerUp;

import java.util.List;

public class UpdatePowerUp {
	
	List<PowerUp> powerUps;
	Pacman pacman;

	public UpdatePowerUp(List<PowerUp> pu, Pacman p) {
		
		this.powerUps=pu;
		this.pacman=p;
		
	}
	
	public void update(int delta){
		
		boolean flag=false;
		for(PowerUp u:powerUps){
			if(u.isAffecting()){
				u.tikTok(delta);
				flag=true;
			}
			if (u.getActiveTime()<0&&u.isAffecting()) {
				u.deactivate();
				float t=pacman.getSpeedUpFactor();
				t-=u.getSpeedUpFactor();
				if(t>1){
					pacman.setSpeedUpFactor(t);
				}else {
					pacman.setSpeedUpFactor(1f);
				}
			}
		}
		if(flag){
			
			pacman.setPowerUp(true);
			
		}
	}

}
