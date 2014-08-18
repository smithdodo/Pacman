package de.tu_darmstadt.gdi1.pacman.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.tu_darmstadt.gdi1.pacman.control.Control;
import de.tu_darmstadt.gdi1.pacman.model.Dot;
import de.tu_darmstadt.gdi1.pacman.model.Ghost;
import de.tu_darmstadt.gdi1.pacman.model.MapElement;
import de.tu_darmstadt.gdi1.pacman.model.SpecialItem;
import de.tu_darmstadt.gdi1.pacman.view.Pacman;

public class Saver {
	
	Control controller;
	
	MapElement[][] mapElements;
	int width, height;
	
	de.tu_darmstadt.gdi1.pacman.model.Pacman pacman;
	List<Ghost> ghosts;

	public Saver(Control c, MapElement[][] me, de.tu_darmstadt.gdi1.pacman.model.Pacman pacman, List<Ghost> ghosts) {
		
		this.controller=c;
		this.mapElements=me;
		height = mapElements.length;
		try {
			width = mapElements[0].length;
		} catch (Exception e) {
			width = 0;
			e.printStackTrace();
		}
		
		this.pacman=pacman;
		this.ghosts=ghosts;
		
		
	}
	
	public void saveGame() throws IOException{
		
		List<String> gameData=new ArrayList<>();
		for(int i=0;i<height;i++){
			for (int j = 0; j < width; j++) {
				if (mapElements[i][j] instanceof Dot||mapElements[i][j] instanceof SpecialItem) {
					gameData.add(mapElements[i][j].toString());
				}
			}
		}
		gameData.add(pacman.toString());
		for(Ghost g:ghosts){
			
			gameData.add(g.toString());
			
		}
		
		File file = new File("res/levels/save.txt");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		for(String s:gameData){
			
			bw.write(s);
			bw.newLine();
			
		}
//		fw.close();
		bw.close();
		
		
		
	}

}
