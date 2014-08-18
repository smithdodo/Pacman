package de.tu_darmstadt.gdi1.pacman.view;

import java.io.File;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Level2 extends Game{

	public Level2() throws SlickException {
		
		super.mapFile = new File("res/levels/Extended 2.txt");
		super.init(gContainer, sbGame);//reload the game with level 1 map
		
	}

	@Override
	protected void enterNextLevel(Integer i) {

		try {
			sbGame.getState(Pacman.Level2).init(gContainer, sbGame);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		((Game)sbGame.getState(Pacman.Level3)).control.setScore(i);
		sbGame.enterState(Pacman.Level3, new FadeOutTransition(), new FadeInTransition());

	}

	@Override
	public int getID() {
		
		return Pacman.Level2;
		
	}

}
