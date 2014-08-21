package de.tu_darmstadt.gdi1.pacman.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Music;

import de.tu_darmstadt.gdi1.pacman.model.Direction;
import de.tu_darmstadt.gdi1.pacman.model.Figur;
import de.tu_darmstadt.gdi1.pacman.model.Ghost;
import de.tu_darmstadt.gdi1.pacman.model.MapElement;
import de.tu_darmstadt.gdi1.pacman.model.MapReader;
import de.tu_darmstadt.gdi1.pacman.model.Pacman;
import de.tu_darmstadt.gdi1.pacman.model.PowerUp;
import de.tu_darmstadt.gdi1.pacman.model.SpeedUp;
import de.tu_darmstadt.gdi1.pacman.service.ActivateItem;
import de.tu_darmstadt.gdi1.pacman.service.CollusionDetect;
import de.tu_darmstadt.gdi1.pacman.service.GenerateDirection;
import de.tu_darmstadt.gdi1.pacman.service.RefreshRecord;
import de.tu_darmstadt.gdi1.pacman.service.RespawnTimer;
import de.tu_darmstadt.gdi1.pacman.service.Saver;
import de.tu_darmstadt.gdi1.pacman.service.UpdateGhostPosition;
import de.tu_darmstadt.gdi1.pacman.service.UpdatePacmanPosition;
import de.tu_darmstadt.gdi1.pacman.service.UpdatePowerUp;
import de.tu_darmstadt.gdi1.pacman.service.UpdateSpeedUp;

public class Control {
	
	MapReader mapReader;
	MapElement[][] mapElements;
	List<Ghost> ghosts;
	Pacman pacman;
	public Direction blickRichtung;
	
	List<SpeedUp> speedUps;
	List<PowerUp> powerUps;

	Random random;
	
	Boolean scoreSaved=false;
	
	//soundboard
	Music pacman_die_music;
	Music pacman_eat_dot_music;
	Music pacman_eat_special_item_music;
	Music ghost_die_music;

	public Control(MapReader mr, List<Ghost> g, Pacman p, Random r) {
		
		super();
		this.mapReader = mr;
		this.mapElements=mr.getMapData();
		this.ghosts=g;
		this.pacman=p;
		this.random=r;
		this.blickRichtung=Direction.STOP;
		
		this.speedUps=mr.getSpeedUps();
		this.powerUps=mr.getPowerUps();
		//calculate how many dots are there on the map, that pacman must eat
		pacman.setNumOfDots(mr.getDots().size());
		
		try {
			pacman_die_music=new Music("res/soundboard/die.wav");
			pacman_eat_dot_music=new Music("res/soundboard/waka_waka.wav");
			pacman_eat_special_item_music=new Music("res/soundboard/eating_herry.wav");
			ghost_die_music=new Music("res/soundboard/eating_ghost.wav");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void updatePacmanPosition(Direction turnDirection, int delta){
		
		UpdatePacmanPosition updater=new UpdatePacmanPosition(pacman, mapElements);
		if(turnDirection!=null){
			blickRichtung=turnDirection;
		}
		updater.update(blickRichtung, delta);

	}
	
	public void updateGhostPosition(int delta){
	
		//update all ghosts' position
		for(Ghost g:ghosts){

			if(g.isRespawning()){
				RespawnTimer rt=new RespawnTimer();
				rt.update(g, delta);
			}else{
				UpdateGhostPosition updater=new UpdateGhostPosition(g, mapElements);
				GenerateDirection grd=new GenerateDirection(g, mapElements, random, pacman);
				Direction turnDirection=grd.generateDirection();
				updater.update(turnDirection, delta);
			}
		}
		
	}
	
	/**
	 * eat Item, activate special effect, update effect timer
	 */
	public void PacmanEatItem() {
		
		ActivateItem ai=new ActivateItem(pacman, mapElements);
		ai.activateItem(random, ghosts, this, pacman_eat_dot_music, pacman_eat_special_item_music);
		
	}
	
	public void updateSpeedUp(int delta){
		
		UpdateSpeedUp us=new UpdateSpeedUp(speedUps, pacman);
		us.update(delta);
		
	}

	public void updatePowerUp(int delta) {
		
		UpdatePowerUp up=new UpdatePowerUp(powerUps, pacman);
		up.update(delta);
		
	}
	
	/**
	 * check if pacman eats ghost or ghost eat pacman
	 */
	public void collisionDetect(){
		
		CollusionDetect cd=new CollusionDetect(ghosts, pacman);
		cd.update(speedUps, powerUps, this, pacman_die_music, ghost_die_music);
		
	}
	
	/**
	 * update respawn timer
	 */
	public void updateRespawnTimer(Figur f, int delta) {
		RespawnTimer rt=new RespawnTimer();
		rt.update(f, delta);
	}
	
	public void resetTurnDirection(){
		this.blickRichtung=Direction.STOP;
	}
	
	/**
	 * refresh the highscore records
	 * score will be save in format like:
	 * ranking score playerName
	 * example:
	 * 1 50000 pinky
	 * 2 30000 naughty
	 * @param name
	 * @throws IOException
	 */
	public void refreshRecord(String playerName) throws IOException{
		
		RefreshRecord rr=new RefreshRecord();
		
		if(!scoreSaved){
			
			rr.refresh(pacman.getScore(), playerName);
			scoreSaved=true;
			
		}
		
	}
	
	public boolean isTopTen() throws IOException {
		
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
		
		if(records.size()<10){
			
			return true;
			
		}else if(records.size()==10){
			
			String[] worstRecord = records.get(9).split(" ");
			int worstScore = Integer.parseInt(worstRecord[1]);
			
			return pacman.getScore()>worstScore;
		}else {
			System.out.println("false----");
			return false;
		}
		
	}
	
	public void saveGame() throws IOException{
		
		Saver saver = new Saver(this, mapElements, pacman, ghosts);
		saver.saveGame();
		
	}
	
	
	public String getMapPath(){
		
		return mapReader.getFilePath();
		
	}

	public Pacman getPacman() {
		return pacman;
	}

	public MapElement[][] getMapElements() {
		return mapElements;
	}

	public List<Ghost> getGhosts() {
		return ghosts;
	}

	public List<PowerUp> getPowerUps() {
		return powerUps;
	}

	public Direction getBlickRichtung() {
		return blickRichtung;
	}

	public void setBlickRichtung(Direction blickRichtung) {
		this.blickRichtung = blickRichtung;
	}

	

}
