package de.tu_darmstadt.gdi1.pacman.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

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
import de.tu_darmstadt.gdi1.pacman.control.Loader;
import de.tu_darmstadt.gdi1.pacman.model.MapReader;

/**
 * Game button Auto change color wenn coursor on button. Enter state
 * 
 */
public class PButton {

	Shape button;
	String buttonName;
	float y;// y coordinate

	public PButton(float y, String name) {

		button = new Rectangle(260, y, 120, 40);
		this.buttonName = name;
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
		g.drawString(buttonName, button.getX() + 10, y + 10);

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

	public void updateRandom(StateBasedGame sbg, GameContainer gc, int StateID,
			boolean reinit) throws IOException, SlickException {
		if (button.contains(gc.getInput().getMouseX(), gc.getInput()
				.getMouseY())
				&& gc.getInput().isMousePressed(0)) {
			this.randomMap();
			if (reinit)
				sbg.getState(StateID).init(gc, sbg);

			sbg.enterState(StateID, new FadeOutTransition(),
					new FadeInTransition());
		}
	}

	public void randomMap() throws IOException {
		Random a = new Random();
		String[] mapElement = new String[8];
		mapElement[0] = " ";
		mapElement[1] = "P";
		mapElement[2] = "X";
		mapElement[3] = "S";
		mapElement[4] = "G";
		mapElement[5] = "B";
		mapElement[6] = "U";
		mapElement[7] = "T";
		int width = a.nextInt(10) + 1;
		int height = a.nextInt(20) + 1;
		String[][] randomMap = new String[height][width];
		File file = new File("res/levels/randomMap.txt");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		System.out.println(width+ "   "+ height+"   ");
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {				
				randomMap[i][j] = mapElement[a.nextInt(7)];				
				bw.write(randomMap[i][j]);
				System.out.print(randomMap[i][j]);
			}
			System.out.println();
			bw.newLine();
		}
		bw.close();
		try {			
			MapReader mr = new MapReader(new File(
					"res/levels/randomMap.txt"));
		} catch (Exception e) {
			this.randomMap();
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

	public void set(float x) {

		this.button.setX(x);

	}

	public Shape getButton() {
		return button;
	}

}
