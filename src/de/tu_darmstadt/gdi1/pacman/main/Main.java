package de.tu_darmstadt.gdi1.pacman.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import de.tu_darmstadt.gdi1.pacman.view.Pacman;

public class Main
{
	public static void main(String[] args) throws SlickException
	{
		// standardpfade initi		
		setPaths();

		// engine starten
		Pacman game = new Pacman();
		AppGameContainer app = new AppGameContainer(game);

		// konfiguration festlegen
		app.setDisplayMode(700, 435, false);
		app.setShowFPS(false);
		app.setTargetFrameRate(60);
		app.setIcon("res/icon.png");
		app.start();
	}

	private static void setPaths()
	{
		if (System.getProperty("os.name").toLowerCase().contains("windows"))
			System.setProperty("org.lwjgl.librarypath",
			System.getProperty("user.dir") + "/lwjgl-2.9.0/native/windows");
		else if (System.getProperty("os.name").toLowerCase().contains("mac"))
			System.setProperty("org.lwjgl.librarypath",
			System.getProperty("user.dir") + "/lwjgl-2.9.0/native/macosx");
		else
			System.setProperty("org.lwjgl.librarypath",
			System.getProperty("user.dir") + "/lwjgl-2.9.0/native/" + System.getProperty("os.name").toLowerCase());
	}
}