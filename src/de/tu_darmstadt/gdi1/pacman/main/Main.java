package de.tu_darmstadt.gdi1.pacman.main;

import java.io.File;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import de.tu_darmstadt.gdi1.pacman.model.MapReader;
import de.tu_darmstadt.gdi1.pacman.model.Model;
import de.tu_darmstadt.gdi1.pacman.view.Pacman;

public class Main
{
	public static void main(String[] args) throws SlickException
	{
		// standardpfade initi		
		//setPaths();

		// engine starten
		Pacman game = new Pacman();
		AppGameContainer app = new AppGameContainer(game);

		// konfiguration festlegen
		app.setDisplayMode(700, 435, false);
		app.setShowFPS(true);
		app.setTargetFrameRate(60);
		app.start();
	}

	/*private static void setPaths()
	{
		if (System.getProperty("os.name").toLowerCase().contains("windows"))
			System.setProperty("org.lwjgl.librarypath",
			System.getProperty("user.dir") + "/native/windows");
		else if (System.getProperty("os.name").toLowerCase().contains("mac"))
			System.setProperty("org.lwjgl.librarypath",
			System.getProperty("user.dir") + "/native/macosx");
		else
			System.setProperty("org.lwjgl.librarypath",
			System.getProperty("user.dir") + "/native/" + System.getProperty("os.name").toLowerCase());
	}*/
}