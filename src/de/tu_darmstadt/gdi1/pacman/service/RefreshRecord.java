package de.tu_darmstadt.gdi1.pacman.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * refresh the game record
 * 
 * save new record and the player's name into record list
 * 
 *
 */
public class RefreshRecord {

	public RefreshRecord() {

	}

	public void refresh(Integer score, String playerName) throws IOException {

		FileReader fr = new FileReader(new File("res/levels/records.txt"));
		BufferedReader br = new BufferedReader(fr);
		// read all records into list
		List<String> records = new LinkedList<>();
		String line = br.readLine();
		while (line != null) {
			records.add(line);
			line = br.readLine();
		}
		br.close();
		
		System.out.println("controller-refresh->"+playerName);

		// read all high scores into list for Sortierung
		int ranking = 1;
		// use cache list to avoid concurrent concurrentmodificationexception
		List<String> cacheRecord = new ArrayList<>();
		cacheRecord.addAll(records);
		records.clear();
		boolean flag = false;

		for (java.util.Iterator<String> it = cacheRecord.iterator(); it
				.hasNext(); ranking++) {
			
			String[] t = it.next().split(" ");
			int aScore = Integer.parseInt(t[1]);
			if (aScore < score && !flag&&ranking<=10) {
				records.add(ranking + " " + score.toString() + " " + playerName);
				ranking++;
				flag = true;
			}
			if(ranking>10)
				break;
			else 
				records.add(ranking + " " + t[1] + " " + t[2]);

		}
		if (cacheRecord.size() == records.size()&&records.size()!=10) {
			records.add(ranking + " " + score.toString() + " " + playerName);
		}
		System.out.println(records.get(0));

		// save new record to file
		FileWriter fw = new FileWriter(new File("res/levels/records.txt"),
				false);
		BufferedWriter bw = new BufferedWriter(fw);
		for (String s : records) {
			fw.write(s + "\n");
			bw.newLine();
		}
		fw.close();
		fr.close();
		System.out.println("refreshed");
	}

}
