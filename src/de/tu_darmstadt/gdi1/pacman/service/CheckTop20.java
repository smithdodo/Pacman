package de.tu_darmstadt.gdi1.pacman.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class CheckTop20 {

	public CheckTop20() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * check if the given score is in top 20
	 * @param newScore
	 * @return
	 * @throws IOException
	 */
	public boolean check(int newScore) throws IOException{
		
		FileReader fr=new FileReader(new File("res/levels/records.txt"));
		BufferedReader br=new BufferedReader(fr);
		//read all records into list
		List<String> records=new LinkedList<>();
		String line=br.readLine();
		while(line!=null){
			records.add(line);
			line=br.readLine();
		}
		br.close();
		
		if(records.size()<20){
			
			return true;
			
		}else if(records.size()==20){
			//check for the worst score in record
			String[] worstRecord = records.get(19).split(" ");
			int worstScore = Integer.parseInt(worstRecord[1]);
			
			return newScore>worstScore;
		}else {
			return false;
		}
		
	}
}
