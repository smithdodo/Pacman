package de.tu_darmstadt.gdi1.pacman.view;

import java.awt.Button;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class HomeMenu extends BasicGameState{
	
	PButton startButton;
	Image backgroundImage;
	Music backgroundMusic;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		startButton=new PButton(20, "START GAME");
		backgroundImage=new Image("res/pictures/theme1/ui/background.jpg");
		backgroundMusic=new Music("res/soundboard/intro.wav");

	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2)
			throws SlickException {
		//start game
		startButton.update(sbg, gc, Pacman.GAME, true);
		
		if(!backgroundMusic.playing()&&sbg.isAcceptingInput())
			backgroundMusic.play();
		
	}
		
	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
	
		g.drawImage(backgroundImage,0,0,700,435,0,0,1400,870);
		
		startButton.render(g, gc);
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Pacman.HOMEMENUE;
	}


}
