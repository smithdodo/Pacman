package de.tu_darmstadt.gdi1.pacman.view;

import java.io.File;
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
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.gdi1.pacman.control.Control;
import de.tu_darmstadt.gdi1.pacman.model.*;


public class Game extends BasicGameState {
	
	Control control;
	
	Random random;
	
	Vector2f setoff;
	
	MapReader mapReader;
	MapElement[][] me;
	
	de.tu_darmstadt.gdi1.pacman.model.Pacman pacman;
	List<Ghost> ghosts;
		
	List<Image> wallImages;
	Image dotImage, powerUpImage, speedUpImage,invisibleWallImage, teleporterImage, pacmanImage, pinkyImage;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		this.random=new Random();
		
		this.mapReader=new MapReader(new File("res/levels/testMap.txt"));
		this.me=mapReader.getMapData();
		
		//initialize pacman on a random spawn point
		int ps=random.nextInt(mapReader.getPlayerSpawnPoints().size());
		Vector2f aPlayerStartPoint=mapReader.getPlayerSpawnPoints().get(ps).getPosition();
		this.pacman=new de.tu_darmstadt.gdi1.pacman.model.Pacman(aPlayerStartPoint);
		
		//initiallize ghosts
		ghosts=new ArrayList<>();
		for (int i=0; i < mapReader.getGhostSpawnPoints().size(); i++) {
			ghosts.add(new Ghost(mapReader.getGhostSpawnPoints().get(i).getPosition()));
		}
		
		control=new Control(me, ghosts, pacman,random);
		
		//define randering setoff
		setoff=new Vector2f((700-mapReader.width_on_display)/2-17.5f,30+(420-mapReader.height_on_display)/2-17.5f);
		
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
		pacmanImage=new Image("res/pictures/theme1/entities/P0.png");
		pinkyImage=new Image("res/pictures/theme1/entities/G1.png");
		
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int delta)
			throws SlickException {
		
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
		
		
		if(gc.getInput().isKeyPressed(Keyboard.KEY_ESCAPE))
			arg1.enterState(Pacman.GAMEMENUE);
		
	}
	
	

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		
		g.translate(setoff.x, setoff.y);
		
		//draw mapElement textur
		for (int i = 0; i < mapReader.height; i++) {
			for (int j = 0; j < mapReader.width; j++) {
				if(me[i][j] instanceof Wall)
					drawWall(i, j, g);
				else if (me[i][j] instanceof Dot&&!((Dot)me[i][j]).isEaten())
					g.drawImage(dotImage, me[i][j].getPosition().x, me[i][j].getPosition().y);
				else if (me[i][j] instanceof SpeedUp&&!((SpeedUp)me[i][j]).isEaten()) 
					g.drawImage(speedUpImage, me[i][j].getPosition().x, me[i][j].getPosition().y);
				else if (me[i][j] instanceof PowerUp&&!((PowerUp)me[i][j]).isEaten()) 
					g.drawImage(powerUpImage, me[i][j].getPosition().x, me[i][j].getPosition().y);
				else if (me[i][j] instanceof Teleporter) 
					g.drawImage(teleporterImage, me[i][j].getPosition().x, me[i][j].getPosition().y);
				else {
					g.drawImage(invisibleWallImage, me[i][j].getPosition().x, me[i][j].getPosition().y);
				}
			}
			
		}
		
		//draw ghosts
		for(int i=0;i<ghosts.size();i++){
			Ghost gs=ghosts.get(i);
			Vector2f p=gs.getCurrentPosition();
			g.setColor(Color.green);
			g.translate(17.5f, 17.5f);
			g.drawLine(p.x, p.y, gs.getCheckPointCol()*35, gs.getCheckPointRow()*35);
			g.translate(-17.5f, -17.5f);
			g.drawImage(pinkyImage, p.x, p.y);
			
		}
		
		//draw pacman
		g.drawImage(pacmanImage, pacman.getCurrentPosition().x, pacman.getCurrentPosition().y);
		
		g.translate(-setoff.x, -setoff.y);

	}


	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Pacman.GAME;
	}
	
	/**
	 * draw wall textur depends on its type
	 * 
	 * @param i row
	 * @param j col
	 * @param g 
	 */
	private void drawWall(int i, int j, Graphics g){
		
			switch (((Wall) me[i][j]).getType()) {
			case ALONE:
				g.drawImage(wallImages.get(0), me[i][j].getPosition().x, me[i][j].getPosition().y);
				break;
			case U:
				g.drawImage(wallImages.get(1), me[i][j].getPosition().x, me[i][j].getPosition().y);
				break;
			case R:
				g.drawImage(wallImages.get(2), me[i][j].getPosition().x, me[i][j].getPosition().y);
				break;
			case UR:
				g.drawImage(wallImages.get(3), me[i][j].getPosition().x, me[i][j].getPosition().y);
				break;
			case D:
				g.drawImage(wallImages.get(4), me[i][j].getPosition().x, me[i][j].getPosition().y);
				break;
			case UD:
				g.drawImage(wallImages.get(5), me[i][j].getPosition().x, me[i][j].getPosition().y);
				break;
			case DR:
				g.drawImage(wallImages.get(6), me[i][j].getPosition().x, me[i][j].getPosition().y);
				break;
			case UDR:
				g.drawImage(wallImages.get(7), me[i][j].getPosition().x, me[i][j].getPosition().y);
				break;
			case L:
				g.drawImage(wallImages.get(8), me[i][j].getPosition().x, me[i][j].getPosition().y);
				break;
			case UL:
				g.drawImage(wallImages.get(9), me[i][j].getPosition().x, me[i][j].getPosition().y);
				break;
			case LR:
				g.drawImage(wallImages.get(10), me[i][j].getPosition().x, me[i][j].getPosition().y);
				break;
			case ULR:
				g.drawImage(wallImages.get(11), me[i][j].getPosition().x, me[i][j].getPosition().y);
				break;
			case DL:
				g.drawImage(wallImages.get(12), me[i][j].getPosition().x, me[i][j].getPosition().y);
				break;
			case UDL:
				g.drawImage(wallImages.get(13), me[i][j].getPosition().x, me[i][j].getPosition().y);
				break;
			case DLR:
				g.drawImage(wallImages.get(14), me[i][j].getPosition().x, me[i][j].getPosition().y);
				break;
			case UDLR:
				g.drawImage(wallImages.get(15), me[i][j].getPosition().x, me[i][j].getPosition().y);
				break;
			default:
				break;
			}
			
	}

}
