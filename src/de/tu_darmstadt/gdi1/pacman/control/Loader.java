package de.tu_darmstadt.gdi1.pacman.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.gdi1.pacman.model.Direction;
import de.tu_darmstadt.gdi1.pacman.model.Dot;
import de.tu_darmstadt.gdi1.pacman.model.Figur;
import de.tu_darmstadt.gdi1.pacman.model.Ghost;
import de.tu_darmstadt.gdi1.pacman.model.MapElement;
import de.tu_darmstadt.gdi1.pacman.model.SpecialItem;
import de.tu_darmstadt.gdi1.pacman.view.Game;
import de.tu_darmstadt.gdi1.pacman.view.Pacman;

public class Loader {

	StateBasedGame stateBasedGame;
	String mapPath;
	Game targetState;//the state that the saved file points to
	List<String> gameData;

	de.tu_darmstadt.gdi1.pacman.model.Pacman pacman;
	List<Ghost> ghosts;
	MapElement[][] mapElements;

	public Loader(StateBasedGame sbg)
			throws IOException {

		this.stateBasedGame = sbg;

		File file = new File("res/levels/save.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		this.mapPath = line;
		this.gameData=new ArrayList<>();
		
		// read datas into ram
		line = br.readLine();
		while (line != null) {

			gameData.add(line);
			line = br.readLine();

		}
		br.close();

		if (mapPath.indexOf("test") > -1) {

			this.targetState = (Game) stateBasedGame.getState(Pacman.Level1);
			init(targetState);
		} else if (mapPath.indexOf("Extended 2") > -1) {

			this.targetState = (Game) stateBasedGame.getState(Pacman.Level2);
			init(targetState);
		} else if (mapPath.indexOf("Extended 3") > -1) {

			this.targetState = (Game) stateBasedGame.getState(Pacman.Level3);
			init(targetState);
		}

	}

	/**
	 * wirte datas into class instances
	 * 
	 */
	public void loadGame() {
		
		int i = 0;
		for (String s:gameData) {
			
			String[] dataArray = s.split(" ");
			
			if (s.indexOf("SpecialItem") > -1) {
				int r = Integer.parseInt(dataArray[1]);
				int c = Integer.parseInt(dataArray[3]);
				((SpecialItem) mapElements[r][c]).setEaten(Boolean
						.parseBoolean(dataArray[5]));
				((SpecialItem) mapElements[r][c]).setActiveTime(Float
						.parseFloat(dataArray[7]));
				((SpecialItem) mapElements[r][c]).setAffecting(Boolean
						.parseBoolean(dataArray[9]));
			} else if (s.indexOf("Dot.") > -1) {
				int r = Integer.parseInt(dataArray[1]);
				int c = Integer.parseInt(dataArray[3]);
				System.out.println("r/c->"+r+" "+c);
				((Dot) mapElements[r][c]).setEaten(Boolean
						.parseBoolean(dataArray[5]));
				
			} else if (s.indexOf("Pacman") > -1) {
				
				loadFigur(this.pacman, dataArray);
				pacman.setRespawnTimer(Float.parseFloat(dataArray[19]));
				pacman.setLives(Integer.parseInt(dataArray[21]));
				pacman.setPowerUp(Boolean.parseBoolean(dataArray[23]));
				pacman.setNumOfDots(Integer.parseInt(dataArray[25]));
				pacman.setScore(Integer.parseInt(dataArray[27]));

			}else if (s.indexOf("Ghost") > -1) {
				
				loadFigur(this.ghosts.get(i++), dataArray);
				
			}
		}
	}

	/**
	 * init variables of this class
	 * 
	 * @param g
	 *            pacman basic game state
	 */
	private void init(Game g) {

		this.pacman = g.getPacman();
		this.ghosts = g.getGhosts();
		this.mapElements = g.getMapElement();

	}
	
	
	/**
	 * write datas into figur instance(Pacman/Ghost)
	 * @param f fighur
	 * @param dataArray data that need to wirte
	 */
	private void loadFigur(Figur f, String[] dataArray){
		
		Vector2f position = new Vector2f(
				Float.parseFloat(dataArray[1]),
				Float.parseFloat(dataArray[3]));
		f.setCurrentPosition(position);
		f.setCheckPointRow(Integer.parseInt(dataArray[5]));
		f.setCheckPointCol(Integer.parseInt(dataArray[7]));
		Vector2f spawnPoint = new Vector2f(
				Float.parseFloat(dataArray[11]) * 35,
				Float.parseFloat(dataArray[9]) * 35);
		f.setSpawnPoint(spawnPoint);
		String currentDirection = dataArray[13];
		if(currentDirection.equals("LEFT")){
			f.setCurrentDirection(Direction.LEFT);
		}else if(currentDirection.equals("RIGHT")){
			f.setCurrentDirection(Direction.RIGHT);
		}else if(currentDirection.equals("UP")){
			f.setCurrentDirection(Direction.UP);
		}else if(currentDirection.equals("DOWN")){
			f.setCurrentDirection(Direction.DOWN);
		}
		
		Vector2f hitBox=new Vector2f(Float.parseFloat(dataArray[15]) * 35,
				Float.parseFloat(dataArray[17]) * 35);
		pacman.getHitBox().setLocation(hitBox);
		
	}

	public Game getTargetState() {
		return targetState;
	}

}
