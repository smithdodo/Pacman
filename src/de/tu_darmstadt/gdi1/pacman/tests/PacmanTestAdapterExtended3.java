package de.tu_darmstadt.gdi1.pacman.tests;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import de.tu_darmstadt.gdi1.pacman.control.Control;
import de.tu_darmstadt.gdi1.pacman.exceptions.InvalidLevelCharacterException;
import de.tu_darmstadt.gdi1.pacman.exceptions.InvalidLevelFormatException;
import de.tu_darmstadt.gdi1.pacman.exceptions.NoGhostSpawnPointException;
import de.tu_darmstadt.gdi1.pacman.exceptions.NoItemsException;
import de.tu_darmstadt.gdi1.pacman.exceptions.NoPacmanSpawnPointException;
import de.tu_darmstadt.gdi1.pacman.exceptions.ReachabilityException;
import de.tu_darmstadt.gdi1.pacman.model.MapReader;

public class PacmanTestAdapterExtended3 implements
		PacmanTestInterfaceExtended1, PacmanTestInterfaceExtended2 {

	public PacmanTestAdapterExtended3() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean levelIsValid(String content) {

		// write string into text file, so that mapReader can read
		File file = new File("res/tests/tempMap.txt");
		try {

			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {

			MapReader mr = new MapReader(new File("res/tests/tempMap.txt"));

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
		File file = new File("res/tests/tempMap.txt");
		try {

			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// will throw exceptions if not valid
		MapReader mr = new MapReader(new File("res/tests/tempMap.txt"));

	}

	@Override
	public int levelGetPacmanSpawnCount(String content) {
		
		MapReader mr;

		// write string into text file, so that mapReader can read
		File file = new File("res/tests/tempMap.txt");
		try {

			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			 mr = new MapReader(new File("res/tests/tempMap.txt"));
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
		File file = new File("res/tests/tempMap.txt");
		try {

			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			 mr = new MapReader(new File("res/tests/tempMap.txt"));
		} catch (Exception e) {
			System.out.println(e);
			return -1;
		}
		
		return mr.getGhostSpawnPoints().size();

	}

	@Override
	public void startGame(String level) {
		
		MapReader mr;

		// write string into text file, so that mapReader can read
		File file = new File("res/tests/tempMap.txt");
		try {

			FileWriter fw = new FileWriter(file);
			fw.write(level);
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			 mr = new MapReader(new File("res/tests/tempMap.txt"));
			 Control control = new Control(mr, mr.getGhosts(), mr.getPacman(), new Random());
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}

	@Override
	public char getLevelCharAt(int x, int y) {
		MapReader mr;
		
		try {
			 mr = new MapReader(new File("res/tests/tempMap.txt"));
			 return mr.getMapElementStringArray()[x][y].charAt(0);
			 
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
			 mr = new MapReader(new File("res/tests/tempMap.txt"));
			 String s=String.valueOf(c);
			 String[][] mapElementStringArray=new String[mr.getHeight()][mr.getWidth()];
			 mapElementStringArray=mr.getMapElementStringArray().clone();
			 mapElementStringArray[x][y]=s;
			 mr.setMapElementStringArray(mapElementStringArray);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@Override
	public String getLevelString() {
		MapReader mr;
		try {
			 mr = new MapReader(new File("res/tests/tempMap.txt"));
			return mr.toString();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public void removeGhosts() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean moveUp() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveLeft() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveDown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveRight() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void movePacman(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public Point getPacmanPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void moveGhost(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public Point getGhostPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isWon() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLost() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getKills() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasPowerUp() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPowerUp(boolean enable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void prepareHighscore() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addToHighscore(String name, int points) {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] getHighscoreNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getViewDirection() {
		// TODO Auto-generated method stub
		return 0;
	}

}
