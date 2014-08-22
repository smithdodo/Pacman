package de.tu_darmstadt.gdi1.pacman.tests;

import java.io.File;
import java.io.IOException;

import de.tu_darmstadt.gdi1.pacman.exceptions.InvalidLevelCharacterException;
import de.tu_darmstadt.gdi1.pacman.exceptions.NoGhostSpawnPointException;
import de.tu_darmstadt.gdi1.pacman.exceptions.NoItemsException;
import de.tu_darmstadt.gdi1.pacman.exceptions.NoPacmanSpawnPointException;
import de.tu_darmstadt.gdi1.pacman.exceptions.ReachabilityException;
import de.tu_darmstadt.gdi1.pacman.model.MapReader;
import de.tu_darmstadt.gdi1.pacman.service.GenerateRandomLevel;

public class PacmanTestAdapterExtended3 extends PacmanTestAdapterExtended2 implements PacmanTestInterfaceExtended3{

	public PacmanTestAdapterExtended3() {
	}

	@Override
	public String generateLevel() {
		
		GenerateRandomLevel grl=new GenerateRandomLevel();
		try {
			grl.generateRandomMap();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		MapReader mr;
		
		try {
			
			mr = new MapReader(new File("res/levels/randomMap.txt"));
			return mr.toString();
		
		} catch (Exception e){
			
			e.printStackTrace();
			return null;
		}
		
	}

}
