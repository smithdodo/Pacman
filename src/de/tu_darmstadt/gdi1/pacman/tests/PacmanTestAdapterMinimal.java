package de.tu_darmstadt.gdi1.pacman.tests;

import java.awt.Checkbox;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.print.attribute.standard.Copies;

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
import de.tu_darmstadt.gdi1.pacman.model.Ghost;
import de.tu_darmstadt.gdi1.pacman.model.MapElement;
import de.tu_darmstadt.gdi1.pacman.model.MapReader;
import de.tu_darmstadt.gdi1.pacman.model.Pacman;
import de.tu_darmstadt.gdi1.pacman.model.PowerUp;
import de.tu_darmstadt.gdi1.pacman.model.Road;
import de.tu_darmstadt.gdi1.pacman.service.GenerateDirection;
import de.tu_darmstadt.gdi1.pacman.service.UpdateFigurPosition;
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
		
		this.removeGhost=true;

	}

	@Override
	public boolean moveUp() {
		
		Pacman pacman=control.getPacman();
		UpdatePacmanPosition upp=new UpdatePacmanPosition(pacman, control.getMapElements());
		
		if(upp.canTurnToDirection(Direction.UP)&&!turnUp){
			
				control.setBlickRichtung(Direction.UP);
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
		
		if(upp.canTurnToDirection(Direction.LEFT)&&!turnLeft){

				control.setBlickRichtung(Direction.LEFT);
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
		
		if(upp.canTurnToDirection(Direction.DOWN)&&!turnDown){
			
				control.setBlickRichtung(Direction.DOWN);
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
		
		if(upp.canTurnToDirection(Direction.RIGHT)){

				control.setBlickRichtung(Direction.RIGHT);
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
		MapElement[][] me=control.getMapElements();
		/*Vector2f newPosition=me[pacman.getCheckPointRow()][pacman.getCheckPointCol()].getPosition().copy();
		Shape hitBox=pacman.getHitBox();
		hitBox.setLocation(newPosition);
		pacman.setHitBox(hitBox);
		pacman.setCurrentPosition(newPosition);
		pacman.setCurrentDirection(control.getBlickRichtung());*/
		
		UpdatePacmanPosition upp=new UpdatePacmanPosition(pacman, control.getMapElements());
		
		control.PacmanEatItem();
		if(!removeGhost)
			 control.collisionDetect();
		
		switch (pacman.getCurrentDirection()) {
		case LEFT:
			if(upp.canTurnToDirection(Direction.LEFT)||upp.canTurnToDirection(control.getBlickRichtung())){
				int t=pacman.getCheckPointCol();
				float destination=me[pacman.getCheckPointRow()][(pacman.getCheckPointCol()-1)%(mapReader.getWidth()-1)].getPosition().x;
				while (pacman.getCurrentPosition().x!=destination) {
					control.updatePacmanPosition(control.getBlickRichtung(), 50);
				}
				pacman.setCheckPointCol(t-1);
				turnLeft=false;
			}
			break;
		case RIGHT:
			if(upp.canTurnToDirection(Direction.RIGHT)||upp.canTurnToDirection(control.getBlickRichtung())){
				int t=pacman.getCheckPointCol();
				float destination=me[pacman.getCheckPointRow()][(pacman.getCheckPointCol()+1)%(mapReader.getWidth()-1)].getPosition().x;
				while (pacman.getCurrentPosition().x!=destination) {
					control.updatePacmanPosition(control.getBlickRichtung(), 50);
				}
				pacman.setCheckPointCol(t+1);
				turnRight=false;
			}
			break;
		case UP:
			if(upp.canTurnToDirection(Direction.UP)||upp.canTurnToDirection(control.getBlickRichtung())){
				int t=pacman.getCheckPointRow();
				float destination=me[(pacman.getCheckPointRow()-1)%(mapReader.getHeight()-1)][pacman.getCheckPointCol()].getPosition().y;
				while (pacman.getCurrentPosition().y!=destination) {
					control.updatePacmanPosition(control.getBlickRichtung(), 50);
				}
				pacman.setCheckPointRow(t-1);
				turnUp=false;
			}
			break;
		case DOWN:
			if(upp.canTurnToDirection(Direction.DOWN)||upp.canTurnToDirection(control.getBlickRichtung())){
				int t=pacman.getCheckPointRow();
				float destination=me[(pacman.getCheckPointRow()+1)%(mapReader.getHeight()-1)][pacman.getCheckPointCol()].getPosition().y;
				while (pacman.getCurrentPosition().y!=destination) {
					control.updatePacmanPosition(control.getBlickRichtung(), 50);
				}
				pacman.setCheckPointRow(t+1);
				turnDown=false;
			}
			break;
		case STOP:
			if(upp.canTurnToDirection(Direction.LEFT)||upp.canTurnToDirection(Direction.RIGHT)||upp.canTurnToDirection(Direction.UP)||upp.canTurnToDirection(Direction.DOWN)){
				control.updatePacmanPosition(control.getBlickRichtung(), 1);
				if(control.getBlickRichtung().equals(Direction.RIGHT)){
					
					pacman.setCheckPointCol((int)pacman.getCurrentPosition().x/35+1);
					
				}else if(control.getBlickRichtung().equals(Direction.DOWN)){
					
					pacman.setCheckPointRow((int)pacman.getCurrentPosition().y/35+1);
					
				}else if(control.getBlickRichtung().equals(Direction.LEFT)){
					
					pacman.setCheckPointCol((int)pacman.getCurrentPosition().x/35);
					
				}else if(control.getBlickRichtung().equals(Direction.UP)){
					
					pacman.setCheckPointRow((int)pacman.getCurrentPosition().y/35);
					
				}
				while (pacman.getCurrentPosition().x%35!=0||pacman.getCurrentPosition().y%35!=0) {
					control.updatePacmanPosition(control.getBlickRichtung(), 50);
				}
			
				turnLeft=false;
				turnRight=false;
				turnUp=false;
				turnDown=false;
			}
		}
		
		
		
		for(Ghost g:control.getGhosts()){
//			control.updatePowerUp(1);
//			control.updateSpeedUp(1);
			UpdateGhostPosition ugp=new UpdateGhostPosition(g, control.getMapElements());
			
			GenerateDirection gd=new GenerateDirection(g, control.getMapElements(), new Random(), control.getPacman());
			Direction turn=gd.generateDirection();
			switch (turn) {
			
			case LEFT:
				if(ugp.canTurnToDirection(Direction.LEFT)){
				Vector2f newPosL=me[g.getCheckPointRow()][g.getCheckPointCol()-1].getPosition().copy();
				Shape hitBoxL=g.getHitBox();
				hitBoxL.setLocation(newPosL);
				g.setHitBox(hitBoxL);
				g.setCurrentPosition(newPosL);
				g.setCurrentDirection(Direction.LEFT);
				g.setCheckPointCol(g.getCheckPointCol()-1);}
				break;
			case RIGHT:
				if(ugp.canTurnToDirection(Direction.RIGHT)){
				Vector2f newPosR=me[g.getCheckPointRow()][g.getCheckPointCol()+1].getPosition().copy();
				Shape hitBoxR=g.getHitBox();
				hitBoxR.setLocation(newPosR);
				g.setHitBox(hitBoxR);
				g.setCurrentPosition(newPosR);
				g.setCurrentDirection(Direction.RIGHT);
				g.setCheckPointCol(g.getCheckPointCol()+1);
				}
				break;
			case UP:
				if(ugp.canTurnToDirection(Direction.UP)){
				Vector2f newPosU=me[g.getCheckPointRow()-1][g.getCheckPointCol()].getPosition().copy();
				Shape hitBoxU=g.getHitBox();
				hitBoxU.setLocation(newPosU);
				g.setHitBox(hitBoxU);
				g.setCurrentPosition(newPosU);
				g.setCurrentDirection(Direction.UP);
				g.setCheckPointRow(g.getCheckPointRow()-1);
				}
				break;
			case DOWN:
				if(ugp.canTurnToDirection(Direction.DOWN)){
				Vector2f newPosD=me[g.getCheckPointRow()+1][g.getCheckPointCol()].getPosition().copy();
				Shape hitBoxD=g.getHitBox();
				hitBoxD.setLocation(newPosD);
				g.setHitBox(hitBoxD);
				g.setCurrentPosition(newPosD);
				g.setCurrentDirection(Direction.DOWN);
				g.setCheckPointRow(g.getCheckPointRow()+1);
				}
				break;

			default:
				break;
			}
			if(!(me[g.getCheckPointRow()][g.getCheckPointCol()] instanceof Road))
				System.out.println(me[g.getCheckPointRow()][g.getCheckPointCol()].toString());
			
		}
		
		control.PacmanEatItem();//check if pacman has eaten dot 
		if(!removeGhost)
			 control.collisionDetect();
		
		
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