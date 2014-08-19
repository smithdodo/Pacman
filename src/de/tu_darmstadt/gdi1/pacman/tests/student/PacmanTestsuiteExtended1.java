package de.tu_darmstadt.gdi1.pacman.tests.student;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.pacman.tests.PacmanTestAdapterExtended1;
import de.tu_darmstadt.gdi1.pacman.tests.PacmanTestInterfaceExtended1;

public class PacmanTestsuiteExtended1
{
	PacmanTestInterfaceExtended1 uut;

	@Before
	public void init()
	{
		uut = new PacmanTestAdapterExtended1();
	}

	@Test
	public void testExtendedAI1()
	{
		uut.startGame("XXXXXXX\nX  P  X\nX     X\nX     X\nX     X\nX     X\nX     X\nX     X\nX     X\nX     X\nX     X\nX     X\nX  G  X\nXXXXXXX");
		Point ghostPos = uut.getGhostPosition();

		// Geist muss bei richtiger Implementierung 5 Schritte hochlaufen
		for (int i = 0; i < 10; i++)
		{
			assertEquals("Der Geist muss Pacman folgen, da Sichtkontakt herrscht.", ghostPos.y - i, uut.getGhostPosition().y);
			uut.update();
		}

		// Der Geist steht genau vor Pacman
		assertFalse("Pacman muss noch leben.", uut.isLost());
		uut.update();
		assertTrue("Pacman muss tot sein.", uut.isLost());
	}

	@Test
	public void testExtendedAI2()
	{
		uut.startGame("XXXX\nXUXX\nXPXX\nX XX\nX XX\nX XX\nX XX\nX  X\nX XX\nX XX\nX XX\nX XX\nX XX\nXGXX\nXXXX");
		Point ghostPos = uut.getGhostPosition();
		Point pacmanPos = uut.getPacmanPosition();

		// Geist läuft bis zur Abzweigung
		for (int i = 0; i < 6; i++)
		{
			assertEquals("Der Geist muss Pacman folgen, da Sichtkontakt herrscht.", ghostPos.y - i, uut.getGhostPosition().y);
			uut.update();
		}

		// Pacman frisst PowerUp und wird daher unsterblich
		uut.movePacman(pacmanPos.x, pacmanPos.y - 1);
		uut.movePacman(pacmanPos.x, pacmanPos.y);

		uut.update();
		assertFalse("Der Geist ist Pacman nicht korrekt ausgewichen", uut.getGhostPosition().y != 6);
		uut.setPowerUp(false);

		for (int i = 0; i < 3; i++)
			uut.update();

		assertTrue("Der Geist steht Pacman nicht gegenüber.", uut.getGhostPosition().x == 1 && uut.getGhostPosition().y == 3);
		uut.update();
		assertTrue("Pacman ist nicht tot.", uut.isLost());
	}

	@Test
	public void testViewDirection()
	{
		uut.startGame("XXXXXXX\nX     X\nX  P  X\nX     X\nX  G  X\nXXXXXXX");
		uut.removeGhosts();

		// nach rechts gehen
		uut.moveRight();
		uut.update();
		assertTrue("Pacman schaut nicht nach rechts.", uut.getViewDirection() == 0);

		// nach unten gehen
		uut.moveDown();
		uut.update();
		assertTrue("Pacman schaut nicht nach unten.", uut.getViewDirection() == 3);

		// nach links gehen
		uut.moveLeft();
		uut.update();
		assertTrue("Pacman schaut nicht nach unten.", uut.getViewDirection() == 2);

		// nach oben gehen
		uut.moveUp();
		uut.update();
		assertTrue("Pacman schaut nicht nach oben.", uut.getViewDirection() == 1);
	}
}
