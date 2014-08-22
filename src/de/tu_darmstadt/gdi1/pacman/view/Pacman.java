package de.tu_darmstadt.gdi1.pacman.view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Grundgerüst eines FSMs
 */
public class Pacman extends StateBasedGame{

public final static int HOMEMENUE=0;
public final static int Level1=1;
public final static int Level2=2;
public final static int Level3=3;
public final static int GAMEMENUE=4;
public final static int RANKING=5;
public final static int NEWGAMEMENU=6;
public final static int RANDOMLEVEL=7;
public final static int ABOUTUS=8;
public final static int LANGUAGEWINDOW=9;


public static int language=0;//(0->english 1->deutsch 2->chinesisch)

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
		addState(new Level1());
		addState(new Level2());
		addState(new Level3());
		addState(new GameMenu());
		addState(new Ranking());
		addState(new NewGameMenu());
		addState(new RandomLevel());
		addState(new AboutUs());
		addState(new LanguageWindow());
	}
}