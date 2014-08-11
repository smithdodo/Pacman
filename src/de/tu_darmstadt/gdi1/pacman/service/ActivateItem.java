package de.tu_darmstadt.gdi1.pacman.service;

import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.pacman.model.Direction;
import de.tu_darmstadt.gdi1.pacman.model.Item;
import de.tu_darmstadt.gdi1.pacman.model.MapElement;
import de.tu_darmstadt.gdi1.pacman.model.Pacman;
import de.tu_darmstadt.gdi1.pacman.model.Road;

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
		if(pacman.getCheckPointRow()%35==0&&pacman.getCheckPointCol()%35==0){
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
	
	public void activateItem(){
		float aimAtX=mapElementArray[aimAtRow][aimAtCol].getPosition().x;
		float aimAtY=mapElementArray[aimAtRow][aimAtCol].getPosition().y;
		if(mapElementArray[aimAtRow][aimAtCol] instanceof Item&&pacman.getHitBox().contains(aimAtX, aimAtY)){
			((Item)mapElementArray[aimAtRow][aimAtCol]).setEaten(true);
		}
	}

}
