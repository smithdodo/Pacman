package de.tu_darmstadt.gdi1.pacman.tests.student;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.pacman.tests.PacmanTestAdapterExtended2;
import de.tu_darmstadt.gdi1.pacman.tests.PacmanTestInterfaceExtended2;

public class PacmanTestsuiteExtended2
{
	PacmanTestInterfaceExtended2 uut;

	@Before
	public void init()
	{
		uut = new PacmanTestAdapterExtended2();
	}

	@Test
	public void testLevelValidator()
	{
		assertTrue("Level ist gültig.", uut.levelIsValid("XXXXXXXXXX\n   X P    \nXXXX  XXXX\nXG       X\nXXXXXXXXXX"));
		assertTrue("Level ist gültig.", uut.levelIsValid("XX XXXXXXX\nX  X P   X\nXXXX  XXXX\nXG       X\nXX XXXXXXX"));
		assertFalse("Level besitzt unerreichbare Bereiche.", uut.levelIsValid("XXXXXXXXXX\nX  X P    \nXXXX  XXXX\nXG       X\nXX XXXXXXX"));
		assertTrue("Level ist gültig.", uut.levelIsValid("XXXXXXXXXXXXXXXXXXX\nX        X        X\nXUXX XXX X XXX XXUX\nX        S        X\nX XX X XXXXX X XX X\nX   PX   X   XP   X\nXXXX XXX X XXX XXXX\nBBBX X       X XBBB\nXXXX X XX XX X XXXX\n       XGGGX       \nXXXX X XXXXX X XXXX\nBBBX X   S   X XBBB\nXXXX X XXXXX X XXXX\nX   P    X    P   X\nX XX XXX X XXX XX X\nX  X           X  X\nXX X X XXXXX X X XX\nX    X   X   X    X\nX XXXXXX X XXXXXX X\nXU               UX\nXXXXXXXXXXXXXXXXXXX"));
	}

	@Test
	public void testPacmanMovement()
	{
		uut.startGame("XX  XXX\nX      \n P     \nX    GX\nXXX XXX");
		uut.removeGhosts();
		assertTrue("Freies Feld", uut.moveUp());
		uut.update();
		assertTrue("Freies Feld", uut.moveRight());
		uut.update();
		assertTrue("Offener Spielfeldrand", uut.moveUp());
		uut.update();
		assertFalse("Wand auf der gegenüberliegenden Seite.", uut.moveUp());
		uut.update();
		assertTrue("Freies Feld", uut.moveRight());
		uut.update();
		assertTrue("Offener Spielfeldrand", uut.moveUp());
		uut.update();
		assertTrue("Falsche Position der Spielfigur.", uut.getPacmanPosition().equals(new Point(3, 4)));
		assertTrue("Offener Spielfeldrand", uut.moveDown());
		uut.update();
		assertTrue("Falsche Position der Spielfigur.", uut.getPacmanPosition().equals(new Point(3, 0)));

		uut.movePacman(0, 2);
		assertTrue("Offener Spielfeldrand", uut.moveLeft());
		uut.update();
		assertTrue("Falsche Position der Spielfigur.", uut.getPacmanPosition().equals(new Point(6, 2)));
		assertTrue("Freies Feld", uut.moveUp());
		uut.update();
		assertFalse("Wand auf der gegenüberliegenden Seite.", uut.moveRight());
		uut.update();
		assertTrue("Freies Feld", uut.moveDown());
		uut.update();
		assertTrue("Offener Spielfeldrand", uut.moveRight());
		uut.update();
		assertTrue("Falsche Position der Spielfigur.", uut.getPacmanPosition().equals(new Point(0, 2)));
	}

	@Test
	public void testGameplay()
	{
		uut.startGame("               \nP      G     U \n               ");
		uut.update();
		assertTrue("Geist bewegt sich in die falsche Richtung.", uut.getGhostPosition().equals(new Point(6, 1)));
		uut.update();
		assertTrue("Geist bewegt sich in die falsche Richtung.", uut.getGhostPosition().equals(new Point(5, 1)));
		assertTrue("Offener Spielfeldrand", uut.moveLeft());
		uut.update();
		assertTrue("Geist bewegt sich in die falsche Richtung.", uut.getGhostPosition().equals(new Point(6, 1)));
		uut.update();
		assertTrue("Geist bewegt sich in die falsche Richtung.", uut.getGhostPosition().equals(new Point(7, 1)));
		uut.update();
		assertTrue("Geist bewegt sich in die falsche Richtung.", uut.getGhostPosition().equals(new Point(8, 1)));
		assertTrue("Freies Feld", uut.moveLeft());
		uut.update();
		assertTrue("PowerUp eingesammelt.", uut.hasPowerUp());
		assertFalse("Geist bewegt sich in die falsche Richtung.", uut.getGhostPosition().equals(new Point(9, 1)));
	}

	@Test
	public void testHighscore()
	{
		uut.prepareHighscore();
		uut.addToHighscore("Benjamin", 123);
		uut.addToHighscore("Jonathan", 4);
		uut.addToHighscore("Kathryn", 604);
		uut.addToHighscore("Jean-Luc", 780);
		uut.addToHighscore("James", 284);
		String[] names = uut.getHighscoreNames();
		assertEquals("Falsche Reihenfolge in der Highscore Liste.", "Jean-Luc", names[0]);
		assertEquals("Falsche Reihenfolge in der Highscore Liste.", "Kathryn", names[1]);
		assertEquals("Falsche Reihenfolge in der Highscore Liste.", "James", names[2]);
		assertEquals("Falsche Reihenfolge in der Highscore Liste.", "Benjamin", names[3]);
		assertEquals("Falsche Reihenfolge in der Highscore Liste.", "Jonathan", names[4]);
	}
}
