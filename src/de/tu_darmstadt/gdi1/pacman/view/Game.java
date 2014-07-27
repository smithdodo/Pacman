package de.tu_darmstadt.gdi1.pacman.view;

import java.util.LinkedList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.gdi1.pacman.model.*;


public class Game extends BasicGameState {
	
	Vector2f setoff;
	
	private Model model;
	MapElement[][] me;
	
	List<Image> wallImages;
	Image dot, powerUp, speedUp,invisibleWall, teleporter, pacman, pinky;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		model=new Model("res/levels/testMap.txt");
		me=model.getMapElements();
		setoff=new Vector2f((700-model.mapReader.width_on_display)/2-17.5f,30+(420-model.mapReader.height_on_display)/2-17.5f);
		
		wallImages=new LinkedList<>();
		for (int i = 0; i < 16; i++) {
			wallImages.add(new Image("res/pictures/theme1/map/X"+i+".png"));
		}
		dot=new Image("res/pictures/theme1/entities/dot.png");
		powerUp=new Image("res/pictures/theme1/entities/powerup.png");
		speedUp=new Image("res/pictures/theme1/entities/speedup.png");
		teleporter=new Image("res/pictures/theme1/entities/teleporter.png");
		invisibleWall=new Image("res/pictures/theme1/map/B.png");
		pacman=new Image("res/pictures/theme1/entities/P0.png");
		pinky=new Image("res/pictures/theme1/entities/G1.png");
		
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int delta)
			throws SlickException {
		
		model.update(gc, delta);
		
		if(gc.getInput().isKeyPressed(Keyboard.KEY_ESCAPE))
			arg1.enterState(Pacman.GAMEMENUE);
		
	}
	
	

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		g.translate(setoff.x, setoff.y);
		for (int i = 0; i < model.height; i++) {
			for (int j = 0; j < model.width; j++) {
				if(me[i][j] instanceof Wall)
					drawWall(i, j, g);
				else if (me[i][j] instanceof Dot&&!((Dot)me[i][j]).isEaten())
					g.drawImage(dot, me[i][j].getPosition().x, me[i][j].getPosition().y);
				else if (me[i][j] instanceof SpeedUp&&!((SpeedUp)me[i][j]).isEaten()) 
					g.drawImage(speedUp, me[i][j].getPosition().x, me[i][j].getPosition().y);
				else if (me[i][j] instanceof PowerUp&&!((PowerUp)me[i][j]).isEaten()) 
					g.drawImage(powerUp, me[i][j].getPosition().x, me[i][j].getPosition().y);
				else if (me[i][j] instanceof Teleporter) 
					g.drawImage(teleporter, me[i][j].getPosition().x, me[i][j].getPosition().y);
				else 
					g.drawImage(invisibleWall, me[i][j].getPosition().x, me[i][j].getPosition().y);
			}
			
		}
		
		g.drawImage(pacman, model.getPacmanPosition().x, model.getPacmanPosition().y);
		g.drawImage(pinky, model.getPinkyPosition().x, model.getPinkyPosition().y);
		g.translate(-setoff.x, -setoff.y);

	}


	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Pacman.GAME;
	}
	
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
