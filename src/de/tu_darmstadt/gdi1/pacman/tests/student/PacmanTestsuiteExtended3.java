package de.tu_darmstadt.gdi1.pacman.tests.student;

import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.pacman.tests.PacmanTestAdapterExtended3;
import de.tu_darmstadt.gdi1.pacman.tests.PacmanTestInterfaceExtended3;

public class PacmanTestsuiteExtended3 {
	PacmanTestInterfaceExtended3 uut;

	@Before
	public void init() {
		uut = new PacmanTestAdapterExtended3();
	}

	@Test
	public void testLevelValidator() {
		assertTrue(
				"Level ist gültig.",
				uut.levelIsValid("XXXXXXXXXXXXXXXXX\nXP     TXT     PX\nX XX XXXXXXX XX X\nX X     X     X X\nX   XXX X XXX   X\nXXX XUX X XUX XXX\nX       X       X\nX XXXXXXXXXXXXX X\nX     GGXGG     X\nX XXX XXXXX XXX X\nXU             UX\nXXXXXXXXXXXXXXXXX"));
	}

	@Test
	public void testLevelGenerator() {
		for (int i = 0; i < 20; i++) {
			String level = uut.generateLevel();
			assertTrue(uut.levelIsValid(level));
		}
	}

	@Test
	public void testTeleporter() {
		uut.startGame("XXXXXXX\nX   T X\nX P   X\nX    GX\nXXXXXXX");
		uut.removeGhosts();
		Point teleporterPosition = new Point(4, 1);
		uut.moveUp();
		uut.update();
		uut.moveRight();
		uut.update();
		assertTrue(
				"Pacman steht nicht links neben dem Teleporter!",
				uut.getPacmanPosition().x == teleporterPosition.x - 1
						&& uut.getPacmanPosition().y == teleporterPosition.y);
		uut.moveRight();
		uut.update();
		assertTrue("Pacman ist nicht teleportiert", !uut.getPacmanPosition().equals(teleporterPosition));
	}
}
