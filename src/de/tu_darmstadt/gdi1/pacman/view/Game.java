package de.tu_darmstadt.gdi1.pacman.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.tu_darmstadt.gdi1.pacman.control.Control;
import de.tu_darmstadt.gdi1.pacman.exceptions.InvalidLevelCharacterException;
import de.tu_darmstadt.gdi1.pacman.exceptions.NoGhostSpawnPointException;
import de.tu_darmstadt.gdi1.pacman.exceptions.NoItemsException;
import de.tu_darmstadt.gdi1.pacman.exceptions.NoPacmanSpawnPointException;
import de.tu_darmstadt.gdi1.pacman.exceptions.ReachabilityException;
import de.tu_darmstadt.gdi1.pacman.model.*;

public abstract class Game extends BasicGameState {
	
	public Control control;
	
	Random random;
	
	Vector2f setoff;
	
	File mapFile = new File("res/levels/testMap.txt");
	MapReader mapReader;
	MapElement[][] mapElement;
	
	StateBasedGame sbGame;
	GameContainer gContainer;
	
	de.tu_darmstadt.gdi1.pacman.model.Pacman pacman;
	List<Ghost> ghosts;
	
	//images
	List<Image> wallImages;
	Image dotImage, powerUpImage, speedUpImage,invisibleWallImage, teleporterImage;
	Image pacmanImageUP, pacmanImageDP,pacmanImageLP,pacmanImageRP;
	Image pacmanImageU,pacmanImageD,pacmanImageL,pacmanImageR;
	List<Image> ghostImages;
	Image life;
	
	String playerName = "";
	TextField textField;
	
	float startTimer=3000;//count down before game start
	boolean isPlaying;
	
	protected int nextLevel_StateID;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		sbGame=arg1;
		gContainer=arg0;		
		
		this.random=new Random();

		try {
			mapReader=new MapReader(mapFile);
		} catch (ReachabilityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidLevelCharacterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoGhostSpawnPointException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoPacmanSpawnPointException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoItemsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mapElement=mapReader.getMapData();
		
		this.pacman=mapReader.getPacman();
		this.ghosts=mapReader.getGhosts();
		
		control=new Control(mapReader, ghosts, pacman,random);
		
		//define randering setoff
		setoff=new Vector2f((700-mapReader.width_on_display)/2-17.5f,30+(420-mapReader.height_on_display)/2-17.5f);

		isPlaying=true;
		
		//initiallize texturs
		wallImages=new LinkedList<>();
		for (int i = 0; i < 16; i++) {
			wallImages.add(new Image("res/pictures/theme1/map/X"+i+".png"));
		}
		dotImage=new Image("res/pictures/theme1/entities/dot.png");
		powerUpImage=new Image("res/pictures/theme1/entities/powerup.png");
		speedUpImage=new Image("res/pictures/theme1/entities/speedup.png");
		teleporterImage=new Image("res/pictures/theme1/entities/teleporter.png");
		invisibleWallImage=new Image("res/pictures/theme1/map/B.png");
		life=new Image("res/pictures/theme1/ui/life.png");
		ghostImages = new ArrayList<>();
		for(int i=0;i<ghosts.size();i++){
			
			Image image = new Image("res/pictures/theme1/entities/G"+i%4+".png");
			ghostImages.add(image);
			
		}
		
		//texturs for pacman
		pacmanImageUP=new Image("res/pictures/theme1/entities/P1_powerup.png");
		pacmanImageDP=new Image("res/pictures/theme1/entities/P3_powerup.png");
		pacmanImageLP=new Image("res/pictures/theme1/entities/P2_powerup.png");
		pacmanImageRP=new Image("res/pictures/theme1/entities/P0_powerup.png");
		pacmanImageU=new Image("res/pictures/theme1/entities/P1.png");
		pacmanImageD=new Image("res/pictures/theme1/entities/P3.png");
		pacmanImageL=new Image("res/pictures/theme1/entities/P2.png");
		pacmanImageR=new Image("res/pictures/theme1/entities/P0.png");
		
		System.out.println("initilizing state "+getID()+"...");
				
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int delta)
			throws SlickException {
		if(startTimer>0){
			startTimer-=delta;
			gc.getInput().clearKeyPressedRecord();
		}else{
		
		if(pacman.getLives()>0&&pacman.getNumOfDots()>0){
		if(pacman.isRespawning()){
		
			gc.getInput().clearKeyPressedRecord();
			control.updateRespawnTimer(pacman, delta);	
			
		}else{
			//update pacman direction
			Direction turnDirection = null;
			if(gc.getInput().isKeyPressed(Keyboard.KEY_LEFT))
				turnDirection=Direction.LEFT;
			else if(gc.getInput().isKeyPressed(Keyboard.KEY_RIGHT))
				turnDirection=Direction.RIGHT;
			else if(gc.getInput().isKeyPressed(Keyboard.KEY_UP))
				turnDirection=Direction.UP;
			else if(gc.getInput().isKeyPressed(Keyboard.KEY_DOWN))
				turnDirection=Direction.DOWN;
			//if no input detected, pacman will walk in same Direction as last frame
			control.updatePacmanPosition(turnDirection, delta);
			
			control.updateGhostPosition(delta);
			control.PacmanEatItem();
			control.updateSpeedUp(delta);
			control.updatePowerUp(delta);
			control.collisionDetect();
		}
		}else if(pacman.getLives()==0){
			
			isPlaying=false;		
		
		try {
			if(!isPlaying&&control.isTop20()){
				
				arg1.pauseUpdate();
				NameListner nl = new NameListner();
				org.newdawn.slick.Font font = new TrueTypeFont(new java.awt.Font(java.awt.Font.SERIF,java.awt.Font.BOLD , 26), false);
				textField = new TextField(gc, font, 250, 180, 200, 60, nl);
				textField.setConsumeEvents(true);
				textField.setFocus(true);
				
			}else {
				((Ranking)sbGame.getState(Pacman.RANKING)).gameStateID=getID();
				sbGame.enterState(Pacman.RANKING, new FadeOutTransition(), new FadeInTransition());;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		}else if (pacman.getNumOfDots()==0) {
			
			enterNextLevel(pacman.getScore());
			
		}
		
		if(gc.getInput().isKeyPressed(Keyboard.KEY_ESCAPE)){
			
			((GameMenu)arg1.getState(Pacman.GAMEMENUE)).gameStateID=getID();
			arg1.enterState(Pacman.GAMEMENUE, new FadeOutTransition(), new FadeInTransition());

		}
		}
		
		
	}
	
	/**
	 * enter the next level when player finish the current one
	 */
	abstract protected void enterNextLevel(Integer i);


	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		
		if(startTimer>0){
			g.setColor(Color.green);
			if(Pacman.language==1)
				g.drawString("Spiel startet in "+(int)(startTimer/1000+1)+" Sekunden...", 230, 10);
			else
				g.drawString("Game start in "+(int)(startTimer/1000+1)+" seconds...", 230, 10);
			
				
		}
		//draw life
		for(int i=0;i<pacman.getLives();i++){
			g.drawImage(life, 5+i*35, 2);
		}
		
		//draw score
		g.setColor(Color.white);
		if(Pacman.language==1)
			g.drawString("Punkte: "+pacman.getScore().toString(), 520, 10);
		else
			g.drawString("Total Score: "+pacman.getScore().toString(), 520, 10);
		
		g.translate(setoff.x, setoff.y);
		
		//draw mapElement textur
		for (int i = 0; i < mapReader.height; i++) {
			for (int j = 0; j < mapReader.width; j++) {
				if(mapElement[i][j] instanceof Wall)
					drawWall(i, j, g);
				else if (mapElement[i][j] instanceof Dot&&!((Dot)mapElement[i][j]).isEaten())
					g.drawImage(dotImage, mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				else if (mapElement[i][j] instanceof SpeedUp&&!((SpeedUp)mapElement[i][j]).isEaten()) 
					g.drawImage(speedUpImage, mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				else if (mapElement[i][j] instanceof PowerUp&&!((PowerUp)mapElement[i][j]).isEaten()) 
					g.drawImage(powerUpImage, mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				else if (mapElement[i][j] instanceof Teleporter) 
					g.drawImage(teleporterImage, mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				else {
					g.drawImage(invisibleWallImage, mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				}
			}
			
		}
		
		//draw ghosts
		for(int i=0;i<ghosts.size();i++){
			Ghost gs=ghosts.get(i);
			Vector2f p=gs.getCurrentPosition();
			g.setColor(Color.green);
			g.drawImage(ghostImages.get(i), p.x, p.y);
			
		}
		
		//draw pacman
		switch (pacman.getCurrentDirection()) {
		case UP:
			if(pacman.isPowerUp()){
				g.drawImage(pacmanImageUP, pacman.getCurrentPosition().x, pacman.getCurrentPosition().y);
			}else {
				g.drawImage(pacmanImageU, pacman.getCurrentPosition().x, pacman.getCurrentPosition().y);
			}
			break;
			
		case DOWN:
			if(pacman.isPowerUp()){
				g.drawImage(pacmanImageDP, pacman.getCurrentPosition().x, pacman.getCurrentPosition().y);
			}else {
				g.drawImage(pacmanImageD, pacman.getCurrentPosition().x, pacman.getCurrentPosition().y);
			}
			break;
		case LEFT:
			if(pacman.isPowerUp()){
				g.drawImage(pacmanImageLP, pacman.getCurrentPosition().x, pacman.getCurrentPosition().y);
			}else {
				g.drawImage(pacmanImageL, pacman.getCurrentPosition().x, pacman.getCurrentPosition().y);
			}
			break;
		case RIGHT:
			if(pacman.isPowerUp()){
				g.drawImage(pacmanImageRP, pacman.getCurrentPosition().x, pacman.getCurrentPosition().y);
			}else {
				g.drawImage(pacmanImageR, pacman.getCurrentPosition().x, pacman.getCurrentPosition().y);
			}
			break;
		case STOP:
				g.drawImage(pacmanImageR, pacman.getCurrentPosition().x, pacman.getCurrentPosition().y);
				break;
		default:
			break;
		}
		
		g.translate(-setoff.x, -setoff.y);
		
		if(pacman.isRespawning()&&pacman.getLives()>0){
			g.setColor(Color.red);
			if(Pacman.language==1)
				g.drawString("Weiter geht's in "+((int)pacman.getRespawnTimer()/1000+1)+" Sekunden...", 240, 10);
			else
				g.drawString("respawn in "+((int)pacman.getRespawnTimer()/1000+1)+" seconds...", 250, 10);
		}
		
				
		if(!isPlaying&&textField!=null){
			g.setColor(new Color (0.2f, 0.2f, 0.2f, 0.5f));
			Shape bg = new Rectangle(0, 0, 700, 435);
			g.fill(bg);
			
			g.setColor(Color.yellow);
			if(Pacman.language==1)
				g.drawString("NEUE TOP 10 BESTLEISTUNG\n\n Gib Deinen Namen ein:", 270, 98);
			else
				g.drawString("NEW TOP 10 SCORE!!\n\nEnter your name:", 280, 98);
			textField.render(arg0, g);
			
		}
		
	}

	
	/**
	 * draw wall textur depends on its type
	 * 
	 * @param i row
	 * @param j col
	 * @param g 
	 */
	private void drawWall(int i, int j, Graphics g){
		
			switch (((Wall) mapElement[i][j]).getType()) {
			case ALONE:
				g.drawImage(wallImages.get(0), mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				break;
			case U:
				g.drawImage(wallImages.get(1), mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				break;
			case R:
				g.drawImage(wallImages.get(2), mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				break;
			case UR:
				g.drawImage(wallImages.get(3), mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				break;
			case D:
				g.drawImage(wallImages.get(4), mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				break;
			case UD:
				g.drawImage(wallImages.get(5), mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				break;
			case DR:
				g.drawImage(wallImages.get(6), mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				break;
			case UDR:
				g.drawImage(wallImages.get(7), mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				break;
			case L:
				g.drawImage(wallImages.get(8), mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				break;
			case UL:
				g.drawImage(wallImages.get(9), mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				break;
			case LR:
				g.drawImage(wallImages.get(10), mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				break;
			case ULR:
				g.drawImage(wallImages.get(11), mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				break;
			case DL:
				g.drawImage(wallImages.get(12), mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				break;
			case UDL:
				g.drawImage(wallImages.get(13), mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				break;
			case DLR:
				g.drawImage(wallImages.get(14), mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				break;
			case UDLR:
				g.drawImage(wallImages.get(15), mapElement[i][j].getPosition().x, mapElement[i][j].getPosition().y);
				break;
			default:
				break;
			}
			
	}
	
	private class NameListner implements ComponentListener{

		@Override
		public void componentActivated(AbstractComponent ac) {
			
			playerName = textField.getText();
			System.out.println("player name->"+playerName+" saved");
			textField.deactivate();
			try {
				control.refreshRecord(playerName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			textField = null;
			try {
				sbGame.getState(Pacman.RANKING).init(gContainer, sbGame);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			sbGame.unpauseUpdate();
			((Ranking)sbGame.getState(Pacman.RANKING)).gameStateID=getID();
			sbGame.enterState(Pacman.RANKING, new FadeOutTransition(), new FadeInTransition());
			
			
		}

	}

	public void setMapReader(MapReader mapReader) {
		this.mapReader = mapReader;
	}

	public MapElement[][] getMapElement() {
		System.out.println("returning mapelement from state->"+getID());
		return mapElement;
	}

	public de.tu_darmstadt.gdi1.pacman.model.Pacman getPacman() {
		return pacman;
	}

	public List<Ghost> getGhosts() {
		return ghosts;
	}
	
}
