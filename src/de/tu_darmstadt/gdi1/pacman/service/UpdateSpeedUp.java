package de.tu_darmstadt.gdi1.pacman.service;

import java.util.List;

import de.tu_darmstadt.gdi1.pacman.model.Pacman;
import de.tu_darmstadt.gdi1.pacman.model.SpeedUp;

public class UpdateSpeedUp {
	
	List<SpeedUp> speedUps;
	Pacman pacman;

	public UpdateSpeedUp(List<SpeedUp> s, Pacman p) {
		
		this.speedUps=s;
		this.pacman=p;
		
	}
	
	public void update(int delta){
		
		for(SpeedUp s:speedUps){
			if(s.isAffecting()){
				s.tikTok(delta);
			}
			if (s.getActiveTime()<0&&s.isAffecting()) {
				s.deactivate();
				float t=pacman.getSpeedUpFactor();
				pacman.setSpeedUpFactor(t-s.getSpeedUpFactor());
			}
		}
	}

}
