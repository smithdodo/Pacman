package de.tu_darmstadt.gdi1.pacman.tests;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.pacman.control.Control;
import de.tu_darmstadt.gdi1.pacman.exceptions.InvalidLevelCharacterException;
import de.tu_darmstadt.gdi1.pacman.exceptions.InvalidLevelFormatException;
import de.tu_darmstadt.gdi1.pacman.exceptions.NoGhostSpawnPointException;
import de.tu_darmstadt.gdi1.pacman.exceptions.NoItemsException;
import de.tu_darmstadt.gdi1.pacman.exceptions.NoPacmanSpawnPointException;
import de.tu_darmstadt.gdi1.pacman.exceptions.ReachabilityException;
import de.tu_darmstadt.gdi1.pacman.model.Direction;
import de.tu_darmstadt.gdi1.pacman.model.Figur;
import de.tu_darmstadt.gdi1.pacman.model.Ghost;
import de.tu_darmstadt.gdi1.pacman.model.MapElement;
import de.tu_darmstadt.gdi1.pacman.model.MapReader;
import de.tu_darmstadt.gdi1.pacman.model.Pacman;
import de.tu_darmstadt.gdi1.pacman.service.GenerateDirection;
import de.tu_darmstadt.gdi1.pacman.service.UpdateGhostPosition;
import de.tu_darmstadt.gdi1.pacman.service.UpdatePacmanPosition;

public class PacmanTestAdapterMinimal implements PacmanTestInterfaceMinimal{

	public Control control;
	boolean removeGhost=false;//if true, controller will not check collusion between ghost and pacman
	MapReader mapReader;
	boolean turnDown=false;
	boolean turnUp=false;
	boolean turnLeft=false;
	boolean turnRight=false;
	public PacmanTestAdapterMinimal() {
		
	}
	

	@Override
	public boolean levelIsValid(String content) {

		// write string into text file, so that mapReader can read
		File file = new File("res/levels/testMap.txt");
		
		try {

			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {

			@SuppressWarnings("unused")
			MapReader mr = new MapReader(new File("res/levels/testMap.txt"));

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	public void levelIsValidWithException(String content)
			throws InvalidLevelCharacterException, InvalidLevelFormatException,
			NoPacmanSpawnPointException, ReachabilityException,
			NoGhostSpawnPointException, NoItemsException {

		// write string into text file, so that mapReader can read
		File file = new File("res/levels/testMap.txt");
		try {

			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// will throw exceptions if not valid
		@SuppressWarnings("unused")
		MapReader mr = new MapReader(new File("res/levels/testMap.txt"));

	}

	@Override
	public int levelGetPacmanSpawnCount(String content) {
		
		MapReader mr;

		// write string into text file, so that mapReader can read
		File file = new File("res/levels/testMap.txt");
		try {

			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			mr = new MapReader(new File("res/levels/testMap.txt"));
		} catch (Exception e) {
			System.out.println(e);
			return -1;
		}
		
		
		return mr.getPlayerSpawnPoints().size();
	}

	@Override
	public int levelGetGhostSpawnCount(String content) {
		
		MapReader mr;

		// write string into text file, so that mapReader can read
		File file = new File("res/levels/testMap.txt");
		try {

			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			mr = new MapReader(new File("res/levels/testMap.txt"));
		} catch (Exception e) {
			System.out.println(e);
			return -1;
		}
		
		return mr.getGhostSpawnPoints().size();

	}

	@Override
	public void startGame(String level) {
		
		// write string into text file, so that mapReader can read
		File file = new File("res/levels/testMap.txt");
		try {

			FileWriter fw = new FileWriter(file);
			fw.write(level);
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			this.mapReader = new MapReader(new File("res/levels/testMap.txt"));
			Pacman pacman=mapReader.getPacman();
			pacman.setLives(1);
			this.control = new Control(mapReader, mapReader.getGhosts(), pacman, new Random());
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}

	@Override
	public char getLevelCharAt(int x, int y) {
		MapReader mr;
		
		try {
			mr = new MapReader(new File("res/levels/testMap.txt"));
			 return mr.getMapElementStringArray()[y][x].charAt(0);
			 
		} catch (Exception e) {
			System.out.println(e);
			char c='z';
			return c;
		}
		
	}

	@Override
	public void setLevelChar(int x, int y, char c) {
		
		MapReader mr;
		try {
			mr = new MapReader(new File("res/levels/testMap.txt"));
			 String s=String.valueOf(c);
			 String[][] mapElementStringArray=new String[mr.getHeight()][mr.getWidth()];
			 mapElementStringArray=mr.getMapElementStringArray().clone();
			 mapElementStringArray[y][x]=s;
			
			 File file = new File("res/levels/testMap.txt");
			 BufferedWriter bw=new BufferedWriter(new FileWriter(file));
			 bw.write(mr.toString());
			 bw.close();
			 
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@Override
	public String getLevelString() {
		MapReader mr;
		try {
			mr = new MapReader(new File("res/levels/testMap.txt"));
			return mr.toString();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public void removeGhosts() {
		
		control.setGhostRemoved(true);

	}

	@Override
	public boolean moveUp() {
		
		Pacman pacman=control.getPacman();
		UpdatePacmanPosition upp=new UpdatePacmanPosition(pacman, control.getMapElements());
		control.setBlickRichtung(Direction.UP);
		if(upp.canTurnToDirection(Direction.UP)&&!turnUp){
			
				turnUp=true;
				return true;
			
		}else {
			return false;
		}
		
	}

	@Override
	public boolean moveLeft() {
		
		Pacman pacman=control.getPacman();
		UpdatePacmanPosition upp=new UpdatePacmanPosition(pacman, control.getMapElements());
		control.setBlickRichtung(Direction.LEFT);
		if(upp.canTurnToDirection(Direction.LEFT)&&!turnLeft){

				turnLeft=true;
				return true;
			
		}else {
			return false;
		}
	}

	@Override
	public boolean moveDown() {

		Pacman pacman=control.getPacman();
		UpdatePacmanPosition upp=new UpdatePacmanPosition(pacman, control.getMapElements());
		control.setBlickRichtung(Direction.DOWN);
		if(upp.canTurnToDirection(Direction.DOWN)&&!turnDown){
			
				turnDown=true;
				return true;
			
		}else {
			return false;
		}
	}

	@Override
	public boolean moveRight() {

		Pacman pacman=control.getPacman();
		UpdatePacmanPosition upp=new UpdatePacmanPosition(pacman, control.getMapElements());
		control.setBlickRichtung(Direction.RIGHT);
		if(upp.canTurnToDirection(Direction.RIGHT)&&!turnRight){

				turnRight=true;
				return true;
			
		}else {
			return false;
		}
	}

	@Override
	public void movePacman(int x, int y) {
		
		//x represents col of checkPoint
		//y represents row of checkPoint
		MapElement[][] me=control.getMapElements();
		Pacman pacman=this.control.getPacman();
		Vector2f newPosition=me[y][x].getPosition().copy();
		pacman.setCurrentPosition(newPosition);
		pacman.setCheckPointRow(y);
		pacman.setCheckPointCol(x);
		Shape hitBox=pacman.getHitBox();
		hitBox.setLocation(newPosition);
		pacman.setHitBox(hitBox);
		
	}

	@Override
	public Point getPacmanPosition() {
	
		Pacman pacman=control.getPacman();
		Point point=new Point((int)pacman.getCurrentPosition().x/35, (int)pacman.getCurrentPosition().y/35);
		return point;
		
	}

	@Override
	public void moveGhost(int x, int y) {
		
		MapElement[][] me=control.getMapElements();
		
		//move all ghosts to given point
		for(Ghost g:control.getGhosts()){
			
			//x represents col of checkPoint
			//y represents row of checkPoint
			Vector2f newPosition=me[y][x].getPosition().copy();
			g.setCurrentPosition(newPosition);
			g.setCheckPointRow(y);
			g.setCheckPointCol(x);
			Shape hitBox=g.getHitBox();
			hitBox.setLocation(newPosition);
			g.setHitBox(hitBox);
			
		}

	}

	@Override
	public Point getGhostPosition() {
		
		//asume that there is only one ghost on the map
		//return the Point of this(first in list) ghost
		Ghost ghost=control.getGhosts().get(0);
		Point point=new Point((int)ghost.getCurrentPosition().x/35, (int)ghost.getCurrentPosition().y/35);
		return point;
	}

	@Override
	public void update() {
		
		Pacman pacman=control.getPacman();
		UpdatePacmanPosition upp=new UpdatePacmanPosition(pacman, control.getMapElements());
		
		control.PacmanEatItem();
		if(!control.isGhostRemoved()){
			 control.collisionDetect();
			 pacman.setRespawning(false);//if pacman is respawning, ghost will set to its spawn point, kann sich nicht bewegen, muessen wir hier als false setzen, sodass geist sich bewegen kann
		}
		
		//update pacman
		switch (control.getBlickRichtung()) {
		case LEFT:
			if(upp.canTurnToDirection(Direction.LEFT)){
				
				updateFigur(pacman, Direction.LEFT);
				turnLeft=false;
				control.setBlickRichtung(Direction.STOP);//without moveLeft, moveRight..., pacman will in test not automatisch move
			}
			break;
		case RIGHT:
			if(upp.canTurnToDirection(Direction.RIGHT)){
				
				updateFigur(pacman, Direction.RIGHT);
				turnRight=false;
				control.setBlickRichtung(Direction.STOP);
			}
			break;
		case UP:
			if(upp.canTurnToDirection(Direction.UP)){
				
				updateFigur(pacman, Direction.UP);
				turnUp=false;
				control.setBlickRichtung(Direction.STOP);
			}
			break;
		case DOWN:
			if(upp.canTurnToDirection(Direction.DOWN)){
			
				updateFigur(pacman, Direction.DOWN);
				turnDown=false;
				control.setBlickRichtung(Direction.STOP);
			}
			break;
		default:
			break;
		}
		
		control.PacmanEatItem();
		if(!control.isGhostRemoved()){
			 control.collisionDetect();
			 pacman.setRespawning(false);
		}
		
		//update ghost
		for(Ghost g:control.getGhosts()){
			
			GenerateDirection gd=new GenerateDirection(g, control.getMapElements(), new Random(), control.getPacman());
			Direction turn=gd.generateDirection();
			updateFigur(g, turn);		
			
		}
		
		control.PacmanEatItem();//check if pacman has eaten dot 
		if(!control.isGhostRemoved()){
			 control.collisionDetect();
			 pacman.setRespawning(false);
		}
	}
	
	/**
	 * verschieb pacman ein Schritt nach blickrichtung
	 * 
	 * @param figur
	 * @param direction turn to
	 * @return
	 */
	private void updateFigur(Figur figur, Direction direction){
		
		//for checking if a given element walkable
		UpdatePacmanPosition upp=new UpdatePacmanPosition(figur, control.getMapElements());
		UpdateGhostPosition ugp=new UpdateGhostPosition(figur, control.getMapElements());

		int delta=50;//time between 2 frames
		switch (direction) {
		case LEFT:				
				int destinationColL=figur.getCheckPointCol();//ziel von pacman
				if(upp.canTurnToDirection(Direction.LEFT))
					destinationColL--;
				if(destinationColL<0)
					destinationColL=mapReader.getWidth()-1;
				
				while (figur.getCurrentPosition().x>destinationColL*35) {
					
					if(figur instanceof Pacman)
						control.updatePacmanPosition(Direction.LEFT, delta);
					else
						ugp.update(direction, delta);
					
				}
				figur.setCheckPointCol(destinationColL);
				Vector2f positionL=new Vector2f(destinationColL*35, figur.getCurrentPosition().y);
				figur.setCurrentPosition(positionL);			
				Shape hitboxL=figur.getHitBox();
				hitboxL.setLocation(positionL);
				figur.setHitBox(hitboxL);
				
				break;
		case RIGHT:				
				int destinationColR=figur.getCheckPointCol();//ziel von pacman
				if(upp.canTurnToDirection(Direction.RIGHT))
					destinationColR++;
				if(destinationColR>=mapReader.getWidth())
					destinationColR=0;
				
				while (figur.getCurrentPosition().x<destinationColR*35) {
					
					if(figur instanceof Pacman)
						control.updatePacmanPosition(Direction.RIGHT, delta);
					else
						ugp.update(direction, delta);
					
				}
				figur.setCheckPointCol(destinationColR);
				Vector2f positionR=new Vector2f(destinationColR*35, figur.getCurrentPosition().y);
				figur.setCurrentPosition(positionR);			
				Shape hitboxR=figur.getHitBox();
				hitboxR.setLocation(positionR);
				figur.setHitBox(hitboxR);
				
				break;
		case UP:
				int destinationRowU=figur.getCheckPointRow();//ziel von pacman
				if(upp.canTurnToDirection(Direction.UP))
					destinationRowU--;
				if(destinationRowU<0)
					destinationRowU=mapReader.height-1;
				
				while (figur.getCurrentPosition().y>destinationRowU*35) {
					
					if(figur instanceof Pacman)
						control.updatePacmanPosition(Direction.UP, delta);
					else
						ugp.update(direction, delta);
					
				}
				figur.setCheckPointRow(destinationRowU);
				Vector2f positionU=new Vector2f(figur.getCurrentPosition().x, destinationRowU*35);
				figur.setCurrentPosition(positionU);			
				Shape hitboxU=figur.getHitBox();
				hitboxU.setLocation(positionU);
				figur.setHitBox(hitboxU);
				
				break;
		case DOWN:
				int destinationRowD=figur.getCheckPointRow();//ziel von pacman
				if(upp.canTurnToDirection(Direction.DOWN))
					destinationRowD++;
				if(destinationRowD>=mapReader.getHeight())
					destinationRowD=0;
				
				while (figur.getCurrentPosition().y<destinationRowD*35) {
					
					if(figur instanceof Pacman)
						control.updatePacmanPosition(Direction.DOWN, delta);
					else
						ugp.update(direction, delta);
					
				}
				figur.setCheckPointRow(destinationRowD);
				Vector2f positionD=new Vector2f(figur.getCurrentPosition().x, destinationRowD*35);
				figur.setCurrentPosition(positionD);			
				Shape hitboxD=figur.getHitBox();
				hitboxD.setLocation(positionD);
				figur.setHitBox(hitboxD);
				
				break;
		default:
			System.out.println("position didn't updated");
			break;
		}
		
	}

	@Override
	public boolean isWon() {
		Pacman pacman=control.getPacman();
		return pacman.getNumOfDots()==0;
	}

	@Override
	public boolean isLost() {
		Pacman pacman=control.getPacman();
		return pacman.getLives()==0;
	}

	@Override
	public int getPoints() {
		Pacman pacman=control.getPacman();
		return pacman.getScore();
	}

	@Override
	public int getKills() {
		Pacman pacman=control.getPacman();
		return pacman.getKillGhost();
	}

	@Override
	public boolean hasPowerUp() {
		Pacman pacman=control.getPacman();
		return pacman.isPowerUp();
	}

	@Override
	public void setPowerUp(boolean enable) {
		
		Pacman pacman = control.getPacman();
		pacman.setPowerUp(enable);

	}
	
	public Pacman getPacman(){
		
		return control.getPacman();
		
	}
}