package de.tu_darmstadt.gdi1.pacman.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Model {
	
	public MapReader mapReader;
	private MapElement[][] mapElementArray;
	public int height, width;
	
	private Pacman pacman;
	private List<Ghost> ghosts;
	private int ghostsSize;
	
	Random random;
	Direction turnDirection=Direction.STOP;
	
	
	public Model(String mapPath){
		
		mapReader=new MapReader(new File(mapPath));
		mapElementArray=mapReader.getMapData();
		height=mapReader.height;
		width=mapReader.width;
		random=new Random();
		
		pacman=new Pacman(getRandomPlayerSpawnPoint(), mapElementArray);
		ghostsSize=mapReader.getGhostSpawnPoints().size();
		Vector2f ghostStartPoint=mapReader.getGhostSpawnPoints().get(0).getPosition();
		ghosts=new ArrayList<>();
		initGhosts();
	}
	
	public void update(GameContainer gc, int delta) {
		
		if(gc.getInput().isKeyPressed(Keyboard.KEY_LEFT))
			turnDirection=Direction.LEFT;
		else if(gc.getInput().isKeyPressed(Keyboard.KEY_RIGHT))
			turnDirection=Direction.RIGHT;
		else if(gc.getInput().isKeyPressed(Keyboard.KEY_UP))
			turnDirection=Direction.UP;
		else if(gc.getInput().isKeyPressed(Keyboard.KEY_DOWN))
			turnDirection=Direction.DOWN;
		pacman.update(turnDirection, delta);
		
		for (int i = 0; i < ghostsSize; i++) {
			
			ghosts.get(i).update(delta);
			
		}
		
		
	}
	
	private void initGhosts(){
		
		//TODO use iterater
		for (int i=0; i < ghostsSize; i++) {
			ghosts.add(new Ghost(mapReader.getGhostSpawnPoints().get(i).getPosition(), mapElementArray, random));
		}
		
	}
	
	private Vector2f getRandomPlayerSpawnPoint(){
		
		int ps=random.nextInt(mapReader.getPlayerSpawnPoints().size());
		Vector2f aPlayerStartPoint=mapReader.getPlayerSpawnPoints().get(ps).getPosition();
		return aPlayerStartPoint;

	}
	
	public Pacman getPacman(){
		return pacman;
	}
	
	
	public MapElement[][] getMapElements() {
		
		return mapElementArray;
		
	}
	
	public Vector2f getPacmanPosition(){
		return pacman.getCurrentPosition();
	}
	
	
	public Shape getHitbox(){
		return pacman.getHitBox();
	}

	public int getGhostsSize() {
		return ghostsSize;
	}

	public List<Ghost> getGhosts() {
		return ghosts;
	}
	
	
	

}
