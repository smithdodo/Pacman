package de.tu_darmstadt.gdi1.pacman.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import de.tu_darmstadt.gdi1.pacman.model.MapReader;

public class GenerateRandomLevel {

	public GenerateRandomLevel() {
		// TODO Auto-generated constructor stub
	}
	
	public void generateRandomMap() throws IOException {
		
		Random a = new Random();
		
		String[] mapElement = new String[8];
		mapElement[0] = " ";
		mapElement[1] = "P";
		mapElement[2] = "X";
		mapElement[3] = "S";
		mapElement[4] = "G";
		mapElement[5] = "B";
		mapElement[6] = "U";
		mapElement[7] = "T";
		
//		int width = a.nextInt(10) + 1;
//		int height = a.nextInt(20) + 1;
		
		//Um keine unspielbaren Level zu bekommen, legen wir die Größe eines Levels auf 20x11 Kästchen fest.
		int width=20;
		int height=11;
		
		String[][] randomMap = new String[height][width];
		
		File file = new File("res/levels/randomMap.txt");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {				
				randomMap[i][j] = mapElement[a.nextInt(7)];				
				bw.write(randomMap[i][j]);
			}
			bw.newLine();
		}
		bw.close();
		
		//Die Level, die wir kreieren, sind wegen der hohen Anzahl der Geister meist schwierig zu spielen.
		try {			
			@SuppressWarnings("unused")
			MapReader mr = new MapReader(new File(
					"res/levels/randomMap.txt"));
			
		} catch (Exception e) {
			this.generateRandomMap();
		}			
	}

}
