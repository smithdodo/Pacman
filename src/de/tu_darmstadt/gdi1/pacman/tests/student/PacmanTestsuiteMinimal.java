package de.tu_darmstadt.gdi1.pacman.tests.student;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.pacman.tests.PacmanTestAdapterMinimal;
import de.tu_darmstadt.gdi1.pacman.tests.PacmanTestInterfaceMinimal;

public class PacmanTestsuiteMinimal {
	PacmanTestInterfaceMinimal uut;

	@Before
	public void init() {
		uut = new PacmanTestAdapterMinimal();
	}

	@Test
	public void testLevelParser() {
		assertEquals(
				"Falsche Anzahl Pacman-Spawnpunkte!",
				4,
				uut.levelGetPacmanSpawnCount("XXXXXXXXXXXXXXXXXXXX\nXU   X        X   UX\nX XX X XXXXXX X XX X\nX XP            PX X\nX X XX XX  XX XX X X\nX      XGGGGX      X\nX X XX XXXXXX XX X X\nX XP            PX X\nX XX X XXXXXX X XX X\nXU   X        X   UX\nXXXXXXXXXXXXXXXXXXXX"));
		assertEquals(
				"Falsche Anzahl Geist-Spawnpunkte!",
				4,
				uut.levelGetGhostSpawnCount("XXXXXXXXXXXXXXXXXXXX\nXU   X        X   UX\nX XX X XXXXXX X XX X\nX XP            PX X\nX X XX XX  XX XX X X\nX      XGGGGX      X\nX X XX XXXXXX XX X X\nX XP            PX X\nX XX X XXXXXX X XX X\nXU   X        X   UX\nXXXXXXXXXXXXXXXXXXXX"));
		uut.startGame("XXXXX\nXP GX\nXXXXX");
		assertEquals("Falscher Charakter im Level", 'P',
				uut.getLevelCharAt(1, 1));
		assertEquals("Falscher Charakter im Level", 'X',
				uut.getLevelCharAt(4, 2));
		assertEquals("Falscher Charakter im Level", 'G',
				uut.getLevelCharAt(3, 1));
		assertEquals("Falscher Charakter im Level", ' ',
				uut.getLevelCharAt(2, 1));
	}

	@Test
	public void testLevelValidator() {
		assertTrue(
				"Level ist gültig.",
				uut.levelIsValid("XXXXXXXXXXXXXXXXXXXX\nXU   X        X   UX\nX XX X XXXXXX X XX X\nX XP            PX X\nX X XX XX  XX XX X X\nX      XGGGGX      X\nX X XX XXXXXX XX X X\nX XP            PX X\nX XX X XXXXXX X XX X\nXU   X        X   UX\nXXXXXXXXXXXXXXXXXXXX"));
		assertFalse(
				"Level besitzt unerreichbare Bereiche.",
				uut.levelIsValid("XXXXXXXXXXXXXXXXXXXX\nXU   X      X X   UX\nX XX X XXXXXXXX XX X\nX XP            PX X\nX X XX XX  XX XX X X\nX      XGGGGX      X\nX X XX XXXXXX XX X X\nX XP            PX X\nX XX X XXXXXX X XX X\nXU   X        X   UX\nXXXXXXXXXXXXXXXXXXXX"));
		assertTrue(
				"Level ist gültig.",
				uut.levelIsValid("XXXXXXXXX\nX       X\nX   X   X\nX GGX  XX\nXXXXXPXXX\nXXXXXXXXX"));
		assertFalse(
				"Level besitzt keinen Pacman-Spawner.",
				uut.levelIsValid("XXXXXXXXX\nX       X\nX   X   X\nX GGX  XX\nXXXXX XXX\nXXXXXXXXX"));
		assertFalse(
				"Level besitzt keinen Geister-Spawner.",
				uut.levelIsValid("XXXXXXXXX\nX       X\nX   X   X\nX X  PPXX\nXXXXX XXX\nXXXXXXXXX"));
		assertFalse(
				"Level besitzt unerreichbare Bereiche.",
				uut.levelIsValid("XXXXXXXXX\nX   X   X\nX   XXXXX\nX XG  PXX\nXXXXX XXX\nXXXXXXXXX"));
		assertTrue(
				"Level ist gültig!",
				uut.levelIsValid("XXXXXXXXX\nX       X\nX       X\nX  G  P X\nX       X\nXXXXXXXXX"));
		assertFalse("Level besitzt keine freie Fläche.",
				uut.levelIsValid("XXXXXXXXX\nXXXXXXXXX\nXXXXGPXXX\nXXXXXXXXX"));
		assertFalse("Level ist nicht rechteckig.",
				uut.levelIsValid("XXXXXXXXX\nXXXXXXXX\nXXG  PXXX\nXXXXXXXXX"));
		assertTrue("Level ist gültig!", uut.levelIsValid("XXXXX\nXG PX\nXXXXX"));
		assertFalse("Level ist komplett leer.", uut.levelIsValid(""));
		assertFalse(
				"Level besitzt unerreichbare Bereiche.",
				uut.levelIsValid("XXXXXXXXXXX\nXXG XX  X X\nX   XG    X\nXX      X X\nXPXXX  XX X\nXXXXXXXXXXX"));
	}

	@Test
	public void testToString() {
		uut.startGame("XXXXXXX\nXP    X\nX     X\nX    GX\nXXXXXXX");
		uut.setLevelChar(3, 3, 'X');
		uut.setLevelChar(3, 2, 'X');
		uut.setLevelChar(2, 2, 'X');
		uut.setLevelChar(2, 3, 'G');
		uut.setLevelChar(6, 4, 'B');
		uut.setLevelChar(5, 1, 'P');
		uut.setLevelChar(5, 3, 'X');
		assertEquals("Level.toString ist fehlerhaft.", uut.getLevelString(),
				"XXXXXXX\nXP   PX\nX XX  X\nX GX XX\nXXXXXXB");
	}

	@Test
	public void testPacmanMovement1() {
		uut.startGame("XXXXXXXXX\nXPX    GX\nX X  X XX\nX   U   X\nXXXXXXXXX");
		uut.removeGhosts();
		assertFalse("Wand", uut.moveLeft());
		uut.update();
		assertTrue("Freies Feld", uut.moveDown());
		uut.update();
		assertTrue("Freies Feld", uut.moveUp());
		uut.update();
		assertTrue("Freies Feld", uut.moveDown());
		uut.update();
		assertFalse("Wand", uut.moveRight());
		uut.update();
		assertTrue("Freies Feld", uut.moveDown());
		uut.update();
		assertFalse("Wand", uut.moveLeft());
		uut.update();
		assertTrue("Freies Feld", uut.moveRight());
		uut.update();
		assertFalse("Wand", uut.moveUp());
		uut.update();
		assertFalse("Wand", uut.moveDown());
		uut.update();
		assertTrue("Freies Feld", uut.moveRight());
		uut.update();
		assertFalse("Kein PowerUp eingesammelt.", uut.hasPowerUp());
		assertTrue("Freies Feld", uut.moveUp());
		uut.update();
		assertFalse("Wand", uut.moveLeft());
		uut.update();
		assertTrue("Freies Feld", uut.moveRight());
		uut.update();
		assertTrue("Freies Feld", uut.moveUp());
		uut.update();
		assertTrue("Freies Feld", uut.moveDown());
		uut.update();
		assertFalse("Kein PowerUp eingesammelt.", uut.hasPowerUp());
		assertFalse("Wand", uut.moveRight());
		uut.update();
		assertTrue("Freies Feld", uut.moveDown());
		uut.update();
		assertTrue("PowerUp eingesammelt.", uut.hasPowerUp());
	}

	@Test
	public void testPacmanMovement2() {
		uut.startGame("XXX\nXXX\nXXX\nXXX\nXPX\nX X\nXGX\nXXX");
		uut.removeGhosts();
		assertTrue("Bewegung möglich.", uut.moveDown());
		assertFalse("Bewegung in diesem Tick nicht nochmal.", uut.moveDown());
		uut.update();
		assertTrue("Bewegung möglich.", uut.moveUp());
		assertFalse("Bewegung in diesem Tick nicht nochmal.", uut.moveUp());
		uut.update();
		assertFalse("Bewegung in diese Richtung nicht möglich.", uut.moveUp());
		assertFalse("Bewegung in diese Richtung nicht möglich.", uut.moveLeft());
		assertFalse("Bewegung in diese Richtung nicht möglich.",
				uut.moveRight());
		assertTrue("Freies Feld.", uut.moveDown());
	}

	@Test
	public void testAI() {
		uut.startGame("XXXXXXXXX\nXPX    GX\nX X  X XX\nX   U   X\nXXXXXXXXX");
		Point pos = uut.getGhostPosition();
		for (int i = 0; i < 20; i++) {
			uut.update();
			assertFalse("Der Geist hat sich nicht bewegt.",
					pos.equals(uut.getGhostPosition()));
			assertFalse(
					"Der Geist hat ein solides Feld betreten.",
					'X' == uut.getLevelCharAt(uut.getGhostPosition().x,
							uut.getGhostPosition().y));
			pos = uut.getGhostPosition();
		}
	}

	@Test
	public void testGameplayWon() {
		uut.startGame("XXXXXXX\nX U   X\nXPX X X\nX X  GX\nXXXXXXX");
		int points = 0;

		assertTrue("Freies Feld", uut.moveDown());
		uut.update();
		assertFalse("Kein PowerUp eingesammelt.", uut.hasPowerUp());
		assertTrue("Punktzahl trotz Item nicht erhöht.",
				points < uut.getPoints());
		points = uut.getPoints();

		assertTrue("Freies Feld", uut.moveUp());
		uut.update();
		assertFalse("Kein PowerUp eingesammelt.", uut.hasPowerUp());
		assertTrue("Punktzahl hat sich geändert.", points == uut.getPoints());

		assertTrue("Freies Feld", uut.moveUp());
		uut.update();
		assertFalse("Kein PowerUp eingesammelt.", uut.hasPowerUp());
		assertTrue("Punktzahl trotz Item nicht erhöht.",
				points < uut.getPoints());
		points = uut.getPoints();

		assertTrue("Freies Feld", uut.moveRight());
		uut.update();
		assertTrue("PowerUp eingesammelt.", uut.hasPowerUp());
		assertTrue("Punktzahl trotz Item nicht erhöht.",
				points < uut.getPoints());
		points = uut.getPoints();

		assertTrue("Freies Feld", uut.moveRight());
		uut.update();
		assertTrue("Punktzahl trotz Item nicht erhöht.",
				points < uut.getPoints());
		points = uut.getPoints();

		assertTrue("Freies Feld", uut.moveRight());
		uut.update();
		assertTrue("Punktzahl trotz Item nicht erhöht.",
				points < uut.getPoints());
		points = uut.getPoints();

		assertTrue("Freies Feld", uut.moveRight());
		uut.update();
		assertTrue("Punktzahl trotz Item nicht erhöht.",
				points < uut.getPoints());
		points = uut.getPoints();

		assertTrue("Freies Feld", uut.moveDown());
		uut.update();
		assertTrue("Punktzahl trotz Item nicht erhöht.",
				points < uut.getPoints());
		points = uut.getPoints();

		assertTrue("Freies Feld", uut.moveDown());
		uut.update();
		assertTrue("Punktzahl hat sich geändert.", points == uut.getPoints());

		assertTrue("Freies Feld", uut.moveLeft());
		uut.update();
		assertTrue("Punktzahl trotz Item nicht erhöht.",
				points < uut.getPoints());
		points = uut.getPoints();

		assertTrue("Freies Feld", uut.moveLeft());
		uut.update();
		assertTrue("Punktzahl trotz Item nicht erhöht.",
				points < uut.getPoints());
		points = uut.getPoints();

		assertTrue("Freies Feld", uut.moveUp());
		uut.update();
		assertTrue("Punktzahl trotz Item nicht erhöht.",
				points < uut.getPoints());
		points = uut.getPoints();

		assertFalse("Das Spiel ist nicht verloren.", uut.isLost());
		assertTrue("Das Spiel müsste gewonnen sein.", uut.isWon());
	}

	@Test
	public void testGameplayLost() {
		uut.startGame("XXXXXXXX\nX U   GX\nXPXXXXXX\nX XXXXXX\nXXXXXXXX");
		int points = 0;
		// zuerst zum powerup bewegen
		assertTrue("Freies Feld", uut.moveUp());
		uut.update();
		assertFalse("Kein PowerUp eingesammelt.", uut.hasPowerUp());
		assertTrue("Punktzahl trotz Item nicht erhöht.",
				points < uut.getPoints());
		points = uut.getPoints();

		assertTrue("Freies Feld", uut.moveRight());
		uut.update();
		assertTrue("PowerUp eingesammelt.", uut.hasPowerUp());
		assertTrue("Punktzahl trotz Item nicht erhöht.",
				points < uut.getPoints());
		points = uut.getPoints();

		// nach rechts laufen, bis der geist gefressen wurde
		for (int i = 0; i < 3; i++) {
			assertTrue("Freies Feld", uut.moveRight());
			uut.update();
			assertTrue("PowerUp eingesammelt.", uut.hasPowerUp());
			assertTrue("Punktzahl trotz Item nicht erhöht.",
					points < uut.getPoints());
			points = uut.getPoints();

			if (uut.getKills() > 0)
				break;
		}

		// der geist hat spätestens jetzt keine andere wahl, als zur spielfigur
		// zu gehen
		if (uut.getKills() == 0)
			uut.update();

		assertTrue("Der Geist hätte gefressen werden müssen.",
				uut.getKills() > 0);

		uut.moveGhost(6, 1);
		uut.movePacman(5, 1);
		uut.setPowerUp(false);
		uut.update();
		assertFalse("Das Spiel ist nicht gewonnen.", uut.isWon());
		assertTrue("Das Spiel müsste verloren sein.", uut.isLost());
	}

	@Test
	public void testSpawn1() {
		uut.startGame("XXXXXXXXXXXXX\nX           X\nX  P      G X\nXXXXXXXXXXXXX");
		Point p = uut.getPacmanPosition();
		assertTrue("Pacman ist nicht korrekt gespawnt.", p.x == 3 && p.y == 2);
		uut.movePacman(7, 1);
		uut.moveGhost(7, 1); // Geist frisst Pacman
		uut.update();
		assertTrue("Pacman befindet sich auf keinem Spawnpunkt",
				uut.getPacmanPosition().x == 3
						&& uut.getPacmanPosition().y == 2);

	}

	@Test
	public void testSpawn2() {
		
		uut.startGame("XXXXXXXXXXXXX\nX     P   X X\nX         X X\nX         X X\nX     G     X\nXXXXXXXXXXXXX");
		Point p = uut.getPacmanPosition();
		Point g = uut.getGhostPosition();
		assertTrue("Pacman ist nicht korrekt gespawnt.", p.x == 6 && p.y == 1);
		uut.setPowerUp(true);
		uut.movePacman(11, 2);
		uut.moveGhost(11, 1);
		uut.update();
		assertFalse("Pacman hätte nicht neu spawnen dürfen.",
				uut.getPacmanPosition().x == p.x
						&& uut.getPacmanPosition().y == p.y);
		assertTrue("Der Geist hätte neu spawnen müssen.",
				uut.getGhostPosition().x == g.x
						&& uut.getGhostPosition().y == g.y);

	}
}
