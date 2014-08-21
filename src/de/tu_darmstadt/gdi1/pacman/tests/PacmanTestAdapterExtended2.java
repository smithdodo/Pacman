package de.tu_darmstadt.gdi1.pacman.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import de.tu_darmstadt.gdi1.pacman.model.Pacman;

public class PacmanTestAdapterExtended2 extends PacmanTestAdapterExtended1 implements PacmanTestInterfaceExtended2{

	public PacmanTestAdapterExtended2() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void prepareHighscore() {
		
		try {
			File file=new File("res/levels/records.txt");
			FileWriter fw=new FileWriter(file);
			fw.write("");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void addToHighscore(String name, int points) {
		
		Pacman pacman=control.getPacman();
		pacman.setScore(points);
		try {
			control.refreshRecord(name);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String[] getHighscoreNames() {
		
		String[] recordSummary=new String[10];
		
		try {
			File file=new File("res/levels/records.txt");
			FileReader fr=new FileReader(file);
			BufferedReader br=new BufferedReader(fr);
			String line=br.readLine();
			if(line!=null){
				//in our record data, first line is the highst score
				//bsp:  1 60550 name
				//from left to right are ranking, score, player name
				String[] record=line.split(" ");
				recordSummary[0]=record[1];
				recordSummary[1]=record[2];
			}else {
				br.close();
				return null;
			}
		
			int i=3;//index of next name
			while (line!=null) {
				
				String[] record=line.split(" ");
				recordSummary[i++]=record[2];
				line=br.readLine();
				
			}
			br.close();
			return recordSummary;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}


}
