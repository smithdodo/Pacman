package de.tu_darmstadt.gdi1.pacman.view;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class NewGameMenu extends BasicGameState{
	
	PButton enterLevel1;
	PButton enterLevel2;
	PButton enterLevel3;
	PButton home;
	PButton randomButton;
	Image backgroundImage;
	Music backgroundMusic;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		enterLevel1=new PButton(20, "Level 1");
		enterLevel2=new PButton(70, "Level 2");
		enterLevel3=new PButton(120, "Level 3");
		randomButton=new PButton(170, "I'm Pro!");
		home = new PButton(350, "HOME");
		home.set(450);
		backgroundImage=new Image("res/pictures/theme1/ui/background.jpg");
		backgroundMusic=new Music("res/soundboard/intro.wav");

	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2)
			throws SlickException {
		
		//start game
		enterLevel1.update(sbg, gc, Pacman.Level1, true);
		enterLevel2.update(sbg, gc, Pacman.Level2, true);
		enterLevel3.update(sbg, gc, Pacman.Level3, true);
		try {
			randomButton.updateRandom(sbg, gc, Pacman.RANDOMLEVEL, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		home.update(sbg, gc, Pacman.HOMEMENUE, true);
		
		if(!HomeMenu.backgroundMusic.playing()&&sbg.isAcceptingInput()&&!this.backgroundMusic.playing())
			backgroundMusic.play();
		
	}
		
	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
	
		g.drawImage(backgroundImage,0,0,700,435,0,0,1400,870);
		
		enterLevel1.render(g, gc);
		enterLevel2.render(g, gc);
		enterLevel3.render(g, gc);
		home.render(g, gc);
		randomButton.render(g, gc);
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Pacman.NEWGAMEMENU;
	}

}
