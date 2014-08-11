package de.tu_darmstadt.gdi1.pacman.control;

import java.util.List;
import java.util.Random;

import de.tu_darmstadt.gdi1.pacman.model.Direction;
import de.tu_darmstadt.gdi1.pacman.model.Ghost;
import de.tu_darmstadt.gdi1.pacman.model.MapElement;
import de.tu_darmstadt.gdi1.pacman.model.Pacman;
import de.tu_darmstadt.gdi1.pacman.model.Road;
import de.tu_darmstadt.gdi1.pacman.service.GenerateRandomDirectionForGhost;
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
		
		for(Ghost g:ghosts){
			UpdateGhostPosition updater=new UpdateGhostPosition(g, 1, mapElements);
			if(!((Road)mapElements[g.getCheckPointRow()][g.getCheckPointCol()]).getForksForGhost().isEmpty()){
				GenerateRandomDirectionForGhost grd=new GenerateRandomDirectionForGhost(g, mapElements, random);
				Direction turnDirection=grd.generateATurnDirection();
				updater.update(turnDirection, delta);
			}else {
				updater.update(g.getCurrentDirection(), delta);
			}
			

		}
	}

}
