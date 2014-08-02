package de.tu_darmstadt.gdi1.pacman.model;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
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
	private int numberofGhost;
	
	private List<SpecialItem> specialItems;
	
	Random random;
	Direction turnDirection=Direction.STOP;
	
	
	public Model(String mapPath){
		
		mapReader=new MapReader(new File(mapPath));
		mapElementArray=mapReader.getMapData();
		height=mapReader.height;
		width=mapReader.width;
		random=new Random();
		specialItems=new LinkedList<>();
		
		pacman=new Pacman(getRandomPlayerSpawnPoint(), mapElementArray);
		numberofGhost=mapReader.getGhostSpawnPoints().size();
		Vector2f ghostStartPoint=mapReader.getGhostSpawnPoints().get(0).getPosition();
		ghosts=new ArrayList<>();
		initGhosts();
	}
	
	private void initGhosts(){
		
		//TODO use iterater
		for (int i=0; i < numberofGhost; i++) {
			ghosts.add(new Ghost(mapReader.getGhostSpawnPoints().get(i).getPosition(), mapElementArray, random));
		}
		
	}
	
	public void update(GameContainer gc, int delta) {
		
		MapElement t=mapElementArray[pacman.getRadarElementRow()][pacman.getRadarElementCol()];
		if(pacman.getHitBox().contains(t.getPosition().x, t.getPosition().y)){
			if(t instanceof Dot)
				((Dot)t).setEaten(true);
			else if(t instanceof SpecialItem)
				specialItems.add((SpecialItem) t);
			//TODO teleport
		}
		
		updateAllSpecialItem(delta);
		
		if(gc.getInput().isKeyPressed(Keyboard.KEY_LEFT))
			turnDirection=Direction.LEFT;
		else if(gc.getInput().isKeyPressed(Keyboard.KEY_RIGHT))
			turnDirection=Direction.RIGHT;
		else if(gc.getInput().isKeyPressed(Keyboard.KEY_UP))
			turnDirection=Direction.UP;
		else if(gc.getInput().isKeyPressed(Keyboard.KEY_DOWN))
			turnDirection=Direction.DOWN;
		pacman.update(turnDirection, delta);
		
		for (int i = 0; i < numberofGhost; i++) {
			
			ghosts.get(i).update(delta);		
	}
		
}
	
	private void updateAllSpecialItem(int delta){
		
		for(SpecialItem s:specialItems){
				s.update(pacman, ghosts, delta);
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
		return numberofGhost;
	}

	public List<Ghost> getGhosts() {
		return ghosts;
	}
	
	
	

}
