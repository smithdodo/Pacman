package de.tu_darmstadt.gdi1.pacman.model;

import java.io.File;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Model {
	
	public MapReader mapReader;
	private MapElement[][] mapElementArray;
	public int height, width;
	
	private Pacman pacman;
	
	Random random;
	Direction turnDirection=Direction.STOP;
	
	
	public Model(String mapPath){
		
		mapReader=new MapReader(new File(mapPath));
		mapElementArray=mapReader.getMapData();
		height=mapReader.height;
		width=mapReader.width;
		
		random=new Random();
		int ps=random.nextInt(mapReader.getPlayerSpawnPoints().size());
		Vector2f playerStartPoint=mapReader.getPlayerSpawnPoints().get(ps).getPosition();
		pacman=new Pacman(playerStartPoint, mapElementArray);
		
	}
	
	public void initMapElements(){
		
		int h=mapReader.height;
		int w=mapReader.width;
		
		
	}

	public MapElement[][] getMapElements() {
		return mapElementArray;
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
	}
	
	public Vector2f getPacmanPosition(){
		return pacman.getCurrentPosition();
	}
	
	public Shape getHitbox(){
		return pacman.getHitBox();
	}
	
	
	

}
