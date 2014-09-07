package de.tu_darmstadt.gdi1.pacman.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import de.tu_darmstadt.gdi1.pacman.service.CheckTop20;
import de.tu_darmstadt.gdi1.pacman.service.RefreshRecord;

public class PacmanTestAdapterExtended2 extends PacmanTestAdapterExtended1 implements PacmanTestInterfaceExtended2{

	public PacmanTestAdapterExtended2() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void prepareHighscore() {
		//clear old record data
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
		
		CheckTop20 cTop20=new CheckTop20();
		try {
			//check if the given score is in top 20
			if (cTop20.check(points)) {
				RefreshRecord rr=new RefreshRecord();
				rr.refresh(points, name);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	@Override
	public String[] getHighscoreNames() {
		//a array, whos first element is the highst score, and from 2. element to end are playername, downgrade
		String[] nameList=new String[20];
		
		try {
			File file=new File("res/levels/records.txt");
			FileReader fr=new FileReader(file);
			@SuppressWarnings("resource")
			BufferedReader br=new BufferedReader(fr);
			
			String line=br.readLine();
			int i = 0;//index of players name
			while(line!=null){
				
				//in our record data, first line is the highst score
				//bsp:  1 60550 name
				//from left to right are ranking, score, player name
				String[] record=line.split(" ");
				nameList[i++]=record[2];//best player
				
				line=br.readLine();
			}
			
			return nameList;
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	


}
