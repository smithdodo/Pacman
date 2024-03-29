package de.tu_darmstadt.gdi1.pacman.view;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.tu_darmstadt.gdi1.pacman.control.Control;
import de.tu_darmstadt.gdi1.pacman.service.GenerateRandomLevel;
import de.tu_darmstadt.gdi1.pacman.service.Loader;

/**
 * Game button Auto change color wenn coursor on button. Enter state
 * 
 */
public class PButton {

	Shape button;
	String buttonName_eng;
	String buttonName_de;
	String buttonName_cn;
	float y;// y coordinate

	public PButton(float y, String eng, String de, String cn) {

		button = new Rectangle(260, y, 120, 40);
		this.buttonName_eng = eng;
		this.buttonName_de = de;
		this.buttonName_cn= cn;
		this.y = y;

	}

	/**
	 * auto change color wen coursor on button
	 * 
	 * @param g
	 * @param gc
	 */
	public void render(Graphics g, GameContainer gc) {

		if (button.contains(gc.getInput().getMouseX(), gc.getInput()
				.getMouseY())) {
			g.setColor(Color.yellow);
			g.fill(button);
		} else {
			g.setColor(Color.white);
			g.fill(button);
		}

		g.setColor(Color.black);
		switch (Pacman.language) {
		case 0:
			g.drawString(buttonName_eng, button.getX() + 10, y + 10);
			break;
		case 1:
			g.drawString(buttonName_de, button.getX() + 10, y + 10);
			break;
		case 2:
			g.drawString(buttonName_cn, button.getX() + 10, y + 10);
			break;

		default:
			break;
		}

	}

	/**
	 * enter other state with/without initialize target state
	 * 
	 * @param sbg
	 * @param gc
	 * @param StateID
	 *            target state ID
	 * @param reinit
	 *            initialize target state or not
	 * @throws SlickException
	 */
	public void update(StateBasedGame sbg, GameContainer gc, int StateID,
			boolean reinit) throws SlickException {

		if (button.contains(gc.getInput().getMouseX(), gc.getInput()
				.getMouseY())
				&& gc.getInput().isMousePressed(0)) {

			System.out.println("enter state->" + StateID);

			if (reinit)
				sbg.getState(StateID).init(gc, sbg);

			sbg.enterState(StateID, new FadeOutTransition(),
					new FadeInTransition());

		}

	}

	public void updateRandom(StateBasedGame sbg, GameContainer gc) throws IOException, SlickException {
		if (button.contains(gc.getInput().getMouseX(), gc.getInput()
				.getMouseY())
				&& gc.getInput().isMousePressed(0)) {
			
			GenerateRandomLevel grl=new GenerateRandomLevel();
			grl.generateRandomMap();
			
			sbg.getState(Pacman.RANDOMLEVEL).init(gc, sbg);
			sbg.enterState(Pacman.RANDOMLEVEL, new FadeOutTransition(),
					new FadeInTransition());
		}
	}

	

	/**
	 * update saver
	 * 
	 * @param gc
	 * @throws IOException
	 */
	public void updateSaver(StateBasedGame sbg, GameContainer gc, Control c)
			throws IOException {

		if (button.contains(gc.getInput().getMouseX(), gc.getInput()
				.getMouseY())
				&& gc.getInput().isMousePressed(0)) {

			c.saveGame();
			sbg.enterState(Pacman.HOMEMENUE, new FadeOutTransition(),
					new FadeInTransition());

		}

	}

	public void updateLoader(StateBasedGame sbg, GameContainer gc)
			throws IOException {

		if (button.contains(gc.getInput().getMouseX(), gc.getInput()
				.getMouseY())
				&& gc.getInput().isMousePressed(0)) {

			Loader loader = new Loader(sbg);
			loader.loadGame();
			sbg.enterState(loader.getTargetState().getID(),
					new FadeOutTransition(), new FadeInTransition());

		}

	}

	public void updateExit(GameContainer gc) {
		if (button.contains(gc.getInput().getMouseX(), gc.getInput()
				.getMouseY())
				&& gc.getInput().isMousePressed(0)) {
			gc.exit();
		}
	}
	
	public void updateLanguange(int language,GameContainer gc, StateBasedGame sbg){
		if (button.contains(gc.getInput().getMouseX(), gc.getInput()
				.getMouseY())
				&& gc.getInput().isMousePressed(0)) {
		
		Pacman.language=language;
		sbg.enterState(Pacman.HOMEMENUE, new FadeOutTransition(), new FadeInTransition());
		
		}
	}

	public void set(float x) {

		this.button.setX(x);

	}

	public Shape getButton() {
		return button;
	}

}
