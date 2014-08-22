package de.tu_darmstadt.gdi1.pacman.view;

import org.newdawn.slick.Animation;
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

public class AboutUs extends BasicGameState{
	
	Image[] aboutus_split=new Image[17];
	Animation aboutusGif;
	Shape background;
	Image nameBoard;
	
	Music bgm;
	
	PButton backtoHomeButton;
	

	@Override
	public void init(GameContainer arg0, StateBasedGame sbg)
			throws SlickException {
		
		for(int i=0; i<17;i++){
			
			aboutus_split[i]=new Image("res/pictures/theme1/ui/AboutUs/"+i+".gif");
			
		}
		
		aboutusGif=new Animation(aboutus_split, 50);
		
		background=new Rectangle(0, 0, 700, 435);
		
		nameBoard=new Image("res/pictures/theme1/ui/AboutUs_name.jpg");
		
		backtoHomeButton=new PButton(0, "Back", "Zurück", "返回");
		backtoHomeButton.set(0);
		
		bgm=new Music("res/soundboard/intro.wav");

		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		if(!HomeMenu.backgroundMusic.playing()&&sbg.isAcceptingInput()&&!this.bgm.playing())
			bgm.play();
		
		g.setColor(Color.white);
		g.fill(background);
		
		g.drawAnimation(aboutusGif, 45, 200);
		g.drawImage(nameBoard, 0,5);
		backtoHomeButton.render(g, gc);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2)
			throws SlickException {

		backtoHomeButton.update(sbg, gc, Pacman.HOMEMENUE, true);
		
	}

	@Override
	public int getID() {
		return Pacman.ABOUTUS;
	}


}
