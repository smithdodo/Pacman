package de.tu_darmstadt.gdi1.pacman.view;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class HomeMenu extends BasicGameState{
	
	Shape startButton;
	Image backgroundImage;
	Music backgroundMusic;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		startButton=new Rectangle(240, 40, 120, 40);
		backgroundImage=new Image("res/pictures/theme1/ui/background.jpg");
		backgroundMusic=new Music("res/soundboard/intro.wav");
		backgroundMusic.loop();//loop the music
		
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int arg2)
			throws SlickException {
		//start game
		if(startButton.contains(gc.getInput().getMouseX(),gc.getInput().getMouseY())&&gc.getInput().isMousePressed(0)){
			//init state and enter
			arg1.getState(Pacman.GAME).init(gc, arg1);
			arg1.enterState(Pacman.GAME);
			backgroundMusic.stop();
		}
	}
	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
	
		g.drawImage(backgroundImage,0,0,700,435,0,0,1400,870);
		
		if(startButton.contains(gc.getInput().getMouseX(),gc.getInput().getMouseY())){
			g.setColor(Color.yellow);
			g.fill(startButton);
		}else {
			g.setColor(Color.white);
			g.fill(startButton);
		}
		
		g.setColor(Color.black);
		g.drawString("START GAME", 245, 50);
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Pacman.HOMEMENUE;
	}


}
