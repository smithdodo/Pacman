package de.tu_darmstadt.gdi1.pacman.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Ranking extends BasicGameState{
	
	List<String> records;
	PButton homeMenu;
	PButton tryAgain;
	
	Image backgroundImage;
	Music backgroundMusic;
	
	int gameStateID;//game state that player come from

	public Ranking() {
		
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		records = new ArrayList<>();
		
		FileReader fr;
		try {
			fr = new FileReader(new File("res/levels/records.txt"));
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			while(line!=null){
				
				this.records.add(line);
				line = br.readLine();
				
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		homeMenu = new PButton(350, "Home");
		tryAgain = new PButton(350, "Try Again");
		homeMenu.set(200);
		tryAgain.set(350);
		
		backgroundImage=new Image("res/pictures/theme1/ui/background.jpg");
		backgroundMusic = new Music("res/soundboard/intermission.wav");
		
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		g.drawImage(backgroundImage,0,0,700,435,0,0,1400,870);
		
		g.setColor(Color.green);
		
		int i = 0;
		for(Iterator<String> it=records.iterator();it.hasNext();i++){
			
			g.drawString(it.next(), 275, 20+i*22);
			
		}
		
		homeMenu.render(g, gc);
		tryAgain.render(g, gc);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		homeMenu.update(sbg, gc, Pacman.HOMEMENUE, true);
		tryAgain.update(sbg, gc, this.gameStateID, true);
		
		if(!backgroundMusic.playing()&&sbg.isAcceptingInput())
			backgroundMusic.play();
		
		
	}

	@Override
	public int getID() {

		return Pacman.RANKING;
		
	}

}
