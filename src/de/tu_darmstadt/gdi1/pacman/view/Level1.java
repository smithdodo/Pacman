package de.tu_darmstadt.gdi1.pacman.view;

import java.io.File;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Level1 extends Game{
	

	public Level1() throws SlickException {
		
		this.mapFile = new File("res/levels/testMap.txt");
		super.init(gContainer, sbGame);//reload the game with level 1 map
		
	}

	@Override
	protected void enterNextLevel(Integer i) {
		
		try {
			sbGame.getState(Pacman.Level2).init(gContainer, sbGame);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		((Game)sbGame.getState(Pacman.Level2)).pacman.setScore(i);
		System.out.println("level cleared! loading next level...");
		sbGame.enterState(Pacman.Level2, new FadeOutTransition(), new FadeInTransition());
		
	}

	@Override
	public int getID() {
		
		return Pacman.Level1;
		
	}

}
