package de.tu_darmstadt.gdi1.pacman.view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LanguageWindow extends BasicGameState{
	
	PButton eng;
	PButton de;
	

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		eng=new PButton(10, "English", "English", "English");
		de=new PButton(60, "Deutsch", "Deutsch", "Deutsch");
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
		
		eng.render(g, gc);
		de.render(g, gc);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2)
			throws SlickException {
		
		eng.updateLanguange(0, gc, sbg);
		de.updateLanguange(1, gc, sbg);
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Pacman.LANGUAGEWINDOW;
	}

}
