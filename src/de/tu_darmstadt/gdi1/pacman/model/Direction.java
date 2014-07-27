package de.tu_darmstadt.gdi1.pacman.model;

public enum Direction {
	// need STOP because at begin and after teleport the pacman should be
	// still(none coordinate/checkpoint wiil be changed)
	LEFT, RIGHT, UP, DOWN, STOP
}
