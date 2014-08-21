package de.tu_darmstadt.gdi1.pacman.service;

import java.util.List;
import java.util.Random;

import org.newdawn.slick.Music;
import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.pacman.control.Control;
import de.tu_darmstadt.gdi1.pacman.model.Direction;
import de.tu_darmstadt.gdi1.pacman.model.Ghost;
import de.tu_darmstadt.gdi1.pacman.model.Item;
import de.tu_darmstadt.gdi1.pacman.model.MapElement;
import de.tu_darmstadt.gdi1.pacman.model.Pacman;
import de.tu_darmstadt.gdi1.pacman.model.PowerUp;
import de.tu_darmstadt.gdi1.pacman.model.Road;
import de.tu_darmstadt.gdi1.pacman.model.SpecialItem;
import de.tu_darmstadt.gdi1.pacman.model.SpeedUp;
import de.tu_darmstadt.gdi1.pacman.model.Teleporter;

/**
 * update the Item that pacman walked over
 *
 */
public class ActivateItem {

	MapElement[][] mapElementArray;
	Pacman pacman;
	
	Direction currentDirection;
	Vector2f currentPosition;
	int checkPointRow, checkPointCol;
	int aimAtRow, aimAtCol;
	
	//height and width of MapElementArray
	int height, width;
	
	public ActivateItem(Pacman p, MapElement[][] m) {
		
		this.pacman=p;
		this.mapElementArray=m;
		this.currentDirection=p.getCurrentDirection();
		this.currentPosition=p.getCurrentPosition();
		this.checkPointRow=p.getCheckPointRow();
		this.checkPointCol=p.getCheckPointCol();
		
		this.height=m.length;
		try {
			width=m[0].length;
		} catch (Exception e) {
			width=0;
			e.printStackTrace();
		}
		
		//if pacman is standing right on an MapElement, aim at it
		//if not, aim at the next MapElement ahead
		if(pacman.getCurrentPosition().x%35==0&&pacman.getCurrentPosition().y%35==0){
			aimAtRow=(int)pacman.getCurrentPosition().y/35;
			aimAtCol=(int)pacman.getCurrentPosition().x/35;
		}else {
			switch (currentDirection) {
			case LEFT:
				aimAtRow=(int)currentPosition.y/35;
				aimAtCol=(int)currentPosition.x/35;
				break;
			case RIGHT:
				aimAtRow=(int)currentPosition.y/35;
				if(currentPosition.x!=0)
					aimAtCol=(int)currentPosition.x/35+1;
				//if nextElementCol out of array bounds
				if(aimAtCol==width&&mapElementArray[aimAtRow][0] instanceof Road){
					aimAtCol=0;
				}else if(aimAtCol==width){
					aimAtCol=width-1;
				}
				break;
			case UP:
				aimAtRow=(int)currentPosition.y/35;
				aimAtCol=(int)currentPosition.x/35;
				break;
			case DOWN:
				if(currentPosition.y==0){
					System.out.println(this.toString());
				}
				if(currentPosition.y!=0)
					aimAtRow=(int)currentPosition.y/35+1;
				aimAtCol=(int)currentPosition.x/35;
				
					
				//if nextElementCol out of array bounds
				if(aimAtRow==height&&mapElementArray[0][aimAtCol] instanceof Road){
					aimAtRow=0;
				}else if(aimAtRow==height){
					aimAtRow=height-1;
				}
				break;
			default:
				break;
			}
		}
		
	}
	
	/**
	 * activate the item that pacman reaches.(set status to EATEN, get special effect)
	 * @param r
	 * @param ghosts
	 * @param c inctans of Control class
	 * @param eatItem music
	 * @param score total score
	 */
	public void activateItem(Random r,List<Ghost> ghosts, Control c, Music eatItem, Music specialItem){
		
		float aimAtX=mapElementArray[aimAtRow][aimAtCol].getPosition().x;
		float aimAtY=mapElementArray[aimAtRow][aimAtCol].getPosition().y;
		if(mapElementArray[aimAtRow][aimAtCol] instanceof Item&&!((Item)mapElementArray[aimAtRow][aimAtCol]).isEaten()&&pacman.getHitBox().contains(aimAtX, aimAtY)){
			//check for special item
			if(mapElementArray[aimAtRow][aimAtCol] instanceof SpecialItem){	
				//special item will be activated for 6000ms
				((SpecialItem)mapElementArray[aimAtRow][aimAtCol]).setActiveTime(6000);
				if(mapElementArray[aimAtRow][aimAtCol] instanceof SpeedUp){
					float t=pacman.getSpeedUpFactor();
					pacman.setSpeedUpFactor(t+((SpeedUp)mapElementArray[aimAtRow][aimAtCol]).getSpeedUpFactor());
				}else if (mapElementArray[aimAtRow][aimAtCol] instanceof PowerUp) {
					pacman.setPowerUp(true);
					float t=pacman.getSpeedUpFactor();
					pacman.setSpeedUpFactor(t+((PowerUp)mapElementArray[aimAtRow][aimAtCol]).getSpeedUpFactor());
				}
				//play music
				if(eatItem.playing()){
					eatItem.stop();
				}
				if (!specialItem.playing()) {
					specialItem.play();
				}
				//add score
				pacman.setScore(pacman.getScore()+200);
			}else {
				pacman.setScore(pacman.getScore()+30);
				pacman.setNumOfDots(pacman.getNumOfDots()-1);
			}
			
			((Item)mapElementArray[aimAtRow][aimAtCol]).setEaten(true);
			
			if(!eatItem.playing())
				eatItem.play();
			//check for teleport
		}else if (pacman.getHitBox().contains(aimAtX, aimAtY)&&(mapElementArray[aimAtRow][aimAtCol] instanceof Teleporter)) {
			GenerateTeleport gt=new GenerateTeleport(pacman, mapElementArray, ghosts);
			gt.update(r, c);
		}

	}
	
}


