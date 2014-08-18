package de.tu_darmstadt.gdi1.pacman.view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameMenu extends BasicGameState {

	PButton restartButton;
	PButton homeMenuButton;
	PButton resumeButton;
	
	public int gameStateID;//game state that this pause menu belongs to

	Image background;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		restartButton = new PButton(40,"Restart");
		homeMenuButton = new PButton(95,"Home");
		resumeButton = new PButton(150,"Resume");
		background = new Image("res/pictures/theme1/ui/background.jpg");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {

		g.drawImage(background, 0, 0, 700, 435, 0, 0, 1400, 870);

		// draw restart button
		restartButton.render(g, gc);

		// draw back to main menu button
		homeMenuButton.render(g, gc);

		// draw resume button
		resumeButton.render(g, gc);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int arg2)
			throws SlickException {
		
		// restart game
		restartButton.update(arg1, gc, this.gameStateID, true);
		
		// back to home
		homeMenuButton.update(arg1, gc, Pacman.HOMEMENUE, true);
		
		// back to game
		resumeButton.update(arg1, gc, this.gameStateID, false);

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Pacman.GAMEMENUE;
	}

}
