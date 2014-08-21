package de.tu_darmstadt.gdi1.pacman.view;

import java.io.File;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Level3 extends Game{

	public Level3() throws SlickException {
		
		super.mapFile = new File("res/levels/Extended 3.txt");
		super.init(gContainer, sbGame);//reload the game with level 2 map
		
	}

	@Override
	protected void enterNextLevel(Integer i) {

		pacman.setLives(0);//finished all levels, save the score tho records if avalable
		sbGame.enterState(Pacman.RANDOMLEVEL, new FadeOutTransition(), new FadeInTransition());
		
	}

	@Override
	public int getID() {
		
		return Pacman.Level3;
		
	}

}
