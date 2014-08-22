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
	PButton aboutButton;
	Image backgroundImage;
	Image[] homeGif_split=new Image[26];
	Animation homeGif;
	static Music backgroundMusic;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		newGame=new PButton(20, "NEW GAME");
		reloadGame=new PButton(70, "RELOAD");
		ranking=new PButton(120, "HIGH SCORE");
		exit = new PButton(350,"EXIT");
		exit.set(450);
		aboutButton=new PButton(170, "About US");
		
		this.backgroundImage=new Image("res/pictures/theme1/ui/background.jpg");
		
		//initate home background gif
		for(int i=0;i<26;i++){
			
			homeGif_split[i]=new Image("res/pictures/theme1/ui/HomeGif/"+i+".gif");
			
		}
		homeGif=new Animation(homeGif_split, 100);
		
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
		aboutButton.update(sbg, gc, Pacman.ABOUTUS, true);
		
		if(!backgroundMusic.playing()&&sbg.isAcceptingInput())
			backgroundMusic.play();
		
	}
		
	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
	
		g.drawImage(this.backgroundImage,0,0,700,435,0,0,1400,870);
		
		newGame.render(g, gc);
		reloadGame.render(g, gc);
		ranking.render(g, gc);
		exit.render(g, gc);
		aboutButton.render(g, gc);
		
		g.drawAnimation(homeGif, -15, 315);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Pacman.HOMEMENUE;
	}


}
