package de.tu_darmstadt.gdi1.pacman.control;

import java.util.List;

import de.tu_darmstadt.gdi1.pacman.model.Direction;
import de.tu_darmstadt.gdi1.pacman.model.Ghost;
import de.tu_darmstadt.gdi1.pacman.model.MapElement;
import de.tu_darmstadt.gdi1.pacman.model.Pacman;
import de.tu_darmstadt.gdi1.pacman.service.UpdatePacmanPosition;

public class Control {
	
	MapElement[][] mapElements;
	List<Ghost> ghosts;
	Pacman pacman;
	Direction pacmanTurnDirection;

	public Control(MapElement[][] m, List<Ghost> g, Pacman p) {
		
		super();
		this.mapElements=m;
		this.ghosts=g;
		this.pacman=p;
		pacmanTurnDirection=Direction.STOP;
		
	}
	
	public void updatePosition(Direction turnDirection, int delta){
		
		UpdatePacmanPosition updater=new UpdatePacmanPosition(pacman, 1, mapElements);
		if(turnDirection!=null){
			pacmanTurnDirection=turnDirection;
		}
		updater.update(pacmanTurnDirection, delta);

	}

}
