package de.tu_darmstadt.gdi1.pacman.model;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

public class SpeedUp extends SpecialItem {
	
	boolean isDeactivated;

	public SpeedUp(Vector2f position, List<Direction> forksP, List<Direction> forksG) {

		super(position, forksP, forksG);
		this.isDeactivated=false;

	}

	/*@Override
	public void activateItem(Pacman p) {

		if (!super.isEaten) {
			p.setSpeedUp(true);
			p.setTimeStillSpeededUp(5000);
			int sf = p.getSpeedUpFactor();
			//die Geschwindigkeit von Pacman darf nur maximal 3 mal so
			//gross wie originale Geschwindigkeit
			if (sf < 3) {
				p.setSpeedUpFactor(++sf);
			}
		}
		super.isEaten = true;
	}*/

	@Override
	public void update(Pacman p, List<Ghost> g, int delta) {
		
		if(!isEaten){
			setEaten(true);
			setActiveTime(50000);//activate for 5 seconds
			int t=p.getSpeedUpFactor();
			 p.setSpeedUpFactor(++t);
			System.out.println(System.currentTimeMillis());
		}else if (activeTime>0) {
				activeTime-=delta;
			}
		
		if(isEaten&&activeTime<0&&!isDeactivated){
			System.out.println(System.currentTimeMillis());
			int t=p.getSpeedUpFactor();
			p.setSpeedUpFactor(--t);
			isDeactivated=true;
		}
	}

	@Override
	public void activateItem(Pacman p) {
		// TODO Auto-generated method stub
		
	}


}
