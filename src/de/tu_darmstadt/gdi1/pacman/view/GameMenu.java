package de.tu_darmstadt.gdi1.pacman.view;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameMenu extends BasicGameState {

	Shape restartButton;
	Shape mainMenuButton;
	Shape resumeButton;

	Image background;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		restartButton = new Rectangle(240, 40, 120, 40);
		mainMenuButton = new Rectangle(240, 95, 120, 40);
		resumeButton = new Rectangle(240, 150, 120, 40);
		background = new Image("res/pictures/theme1/ui/background.jpg");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {

		g.drawImage(background, 0, 0, 700, 435, 0, 0, 1400, 870);

		// draw restart button
		if (restartButton.contains(gc.getInput().getMouseX(), gc.getInput()
				.getMouseY())) {
			g.setColor(Color.yellow);
			g.fill(restartButton);
		} else {
			g.setColor(Color.white);
			g.fill(restartButton);
		}

		g.setColor(Color.black);
		g.drawString("RESTART", 245, 50);

		// draw back to main menu button
		if (mainMenuButton.contains(gc.getInput().getMouseX(), gc.getInput()
				.getMouseY())) {
			g.setColor(Color.yellow);
			g.fill(mainMenuButton);
		} else {
			g.setColor(Color.white);
			g.fill(mainMenuButton);
		}

		g.setColor(Color.black);
		g.drawString("Main Menu", 245, 110);

		// draw resume button
		if (resumeButton.contains(gc.getInput().getMouseX(), gc.getInput()
				.getMouseY())) {
			g.setColor(Color.yellow);
			g.fill(resumeButton);
		} else {
			g.setColor(Color.white);
			g.fill(resumeButton);
		}

		g.setColor(Color.black);
		g.drawString("Back to Game", 245, 170);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int arg2)
			throws SlickException {
		// restart game
		if (restartButton.contains(gc.getInput().getMouseX(), gc.getInput()
				.getMouseY())
				&& gc.getInput().isMousePressed(0)){
			arg1.getState(Pacman.GAME).init(gc, arg1);
			arg1.enterState(Pacman.GAME);
		}
		// start game
		if (resumeButton.contains(gc.getInput().getMouseX(), gc.getInput()
				.getMouseY())
				&& gc.getInput().isMousePressed(0))
			arg1.enterState(Pacman.GAME);

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Pacman.GAMEMENUE;
	}

}
