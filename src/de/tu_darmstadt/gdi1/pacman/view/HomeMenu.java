package de.tu_darmstadt.gdi1.pacman.view;


import java.io.IOException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class HomeMenu extends BasicGameState{
	
	PButton newGame;
	PButton reloadGame;
	PButton ranking;
	PButton exit;
	Image backgroundImage;
	Animation pacmanGif;
	static Music backgroundMusic;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		newGame=new PButton(20, "NEW GAME");
		reloadGame=new PButton(70, "RELOAD");
		ranking=new PButton(120, "HIGH SCORE");
		exit = new PButton(350,"EXIT");
		exit.set(450);
		backgroundImage=new Image("res/pictures/theme1/ui/background.jpg");
		backgroundMusic=new Music("res/soundboard/intro.wav");

	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2)
			throws SlickException {
		
		//start game
		newGame.update(sbg, gc, Pacman.NEWGAMEMENU, true);
		try {
			reloadGame.updateLoader(sbg, gc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ranking.update(sbg, gc, Pacman.RANKING, true);
		exit.updateExit(gc);
		
		if(!backgroundMusic.playing()&&sbg.isAcceptingInput())
			backgroundMusic.play();
		
	}
		
	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
	
		g.drawImage(backgroundImage,0,0,700,435,0,0,1400,870);
		
		newGame.render(g, gc);
		reloadGame.render(g, gc);
		ranking.render(g, gc);
		exit.render(g, gc);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Pacman.HOMEMENUE;
	}


}
