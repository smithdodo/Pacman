package de.tu_darmstadt.gdi1.pacman.view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.StateBasedEntityManager;

/**
 * Grundgerüst eines FSMs
 */
public class Pacman extends StateBasedGame{

public final static int HOMEMENUE=0;
public final static int GAME=1;
public final static int GAMEMENUE=1;


	public Pacman() throws SlickException
	{
		super("GDI1 Praktikum: Pacman");
	}

	/*
	 * von StateBasedGame geerbt und einmal beim Start ausgeführt
	 */
	public void initStatesList(GameContainer gc) throws SlickException
	{
		addState(new HomeMenu());
		addState(new Game());
	}
}