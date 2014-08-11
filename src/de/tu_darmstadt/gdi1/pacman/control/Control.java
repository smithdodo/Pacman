package de.tu_darmstadt.gdi1.pacman.control;

import java.util.List;
import java.util.Random;

import de.tu_darmstadt.gdi1.pacman.model.Direction;
import de.tu_darmstadt.gdi1.pacman.model.Ghost;
import de.tu_darmstadt.gdi1.pacman.model.MapElement;
import de.tu_darmstadt.gdi1.pacman.model.Pacman;
import de.tu_darmstadt.gdi1.pacman.model.Road;
import de.tu_darmstadt.gdi1.pacman.service.ActivateItem;
import de.tu_darmstadt.gdi1.pacman.service.GenerateDirection;
import de.tu_darmstadt.gdi1.pacman.service.UpdateFigurPosition;
import de.tu_darmstadt.gdi1.pacman.service.UpdateGhostPosition;
import de.tu_darmstadt.gdi1.pacman.service.UpdatePacmanPosition;

public class Control {
	
	MapElement[][] mapElements;
	List<Ghost> ghosts;
	Pacman pacman;
	Direction pacmanTurnDirection;
	
	Random random;

	public Control(MapElement[][] m, List<Ghost> g, Pacman p, Random r) {
		
		super();
		this.mapElements=m;
		this.ghosts=g;
		this.pacman=p;
		this.random=r;
		this.pacmanTurnDirection=Direction.STOP;
		
	}
	
	public void updatePacmanPosition(Direction turnDirection, int delta){
		
		UpdatePacmanPosition updater=new UpdatePacmanPosition(pacman, 1, mapElements);
		if(turnDirection!=null){
			pacmanTurnDirection=turnDirection;
		}
		updater.update(pacmanTurnDirection, delta);

	}
	
	public void updateGhostPosition(int delta){
		//update all ghosts' position
		for(Ghost g:ghosts){
			UpdateGhostPosition updater=new UpdateGhostPosition(g, 1, mapElements);
			GenerateDirection grd=new GenerateDirection(g, mapElements, random, pacman);
			Direction turnDirection=grd.generateDirection();
			updater.update(turnDirection, delta);
		}
		
	}
	
	public void PacmanEatItem() {
		ActivateItem ai=new ActivateItem(pacman, mapElements);
		ai.activateItem();
	}

}
