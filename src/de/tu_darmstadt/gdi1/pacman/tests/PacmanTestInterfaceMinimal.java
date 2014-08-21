package de.tu_darmstadt.gdi1.pacman.tests;

import java.awt.Point;

import de.tu_darmstadt.gdi1.pacman.exceptions.InvalidLevelCharacterException;
import de.tu_darmstadt.gdi1.pacman.exceptions.InvalidLevelFormatException;
import de.tu_darmstadt.gdi1.pacman.exceptions.NoGhostSpawnPointException;
import de.tu_darmstadt.gdi1.pacman.exceptions.NoItemsException;
import de.tu_darmstadt.gdi1.pacman.exceptions.NoPacmanSpawnPointException;
import de.tu_darmstadt.gdi1.pacman.exceptions.ReachabilityException;
import de.tu_darmstadt.gdi1.pacman.model.Pacman;

public interface PacmanTestInterfaceMinimal {
  /**
   * Gibt true aus, wenn aus dem String content ein gültiger Level geladen
   * werden kann, ansonsten false. Auftretende Exceptions werden abgefangen und
   * haben immer die Rückgabe false zur Folge.
   * 
   * @param content
   * @return true if the level is legal
   */
  public boolean levelIsValid(String content);

  /**
   * Parst einen Level aus dem String content und leitet dabei die
   * entsprechenden Parserexceptions weiter.
   * 
   * @param content
   *          Textdarstellung des Levels
   * @throws InvalidLevelCharacterException
   * @throws InvalidLevelFormatException
   * @throws NoPacmanSpawnPointException
   * @throws ReachabilityException
   * @throws NoGhostSpawnPointException
   * @throws NoItemsException
   */
  public void levelIsValidWithException(String content)
      throws InvalidLevelCharacterException, InvalidLevelFormatException,
      NoPacmanSpawnPointException, ReachabilityException,
      NoGhostSpawnPointException, NoItemsException;

  /**
   * Gibt die Anzahl der Felder zurück, die zum Spawnen der Spielfigur dienen.
   * 
   * @param content
   *          Textdarstellung des Levels
   * @return Mögliche Pacman Spawnpunkte
   */
  public int levelGetPacmanSpawnCount(String content);
  
  /**
   * Gibt die Anzahl der Felder zurück, auf denen Geister spawnen können.
   * 
   * @param content
   *          Textdarstellung des Levels
   * @return Mögliche Geist Spawnpunkte
   */
  public int levelGetGhostSpawnCount(String content);

  /**
   * Startet ein neues Spiel und platziert alle Items und Figuren. Das
   * gestartete Spiel soll für die folgenden Aufrufe der Spielsteuerung
   * verwendet werden.
   * 
   * @param level
   *          Textdarstellung des Levels
   */
  public void startGame(String level);

  /**
   * Gibt den char zurück, der das Feld im Level des laufenden Spiels an der
   * Position x,y repräsentiert.
   * 
   * @param x
   * @param y
   * @return char an Position x,y
   */
  public char getLevelCharAt(int x, int y);

  /**
   * Ändert das aktuell laufende Level an der Position x,y.
   * 
   * @param x
   * @param y
   * @param c
   *          Neuer Char des Levels
   */
  public void setLevelChar(int x, int y, char c);
  
  /**
   * Gibt die Textdarstellung des aktuell laufenden Levels aus
   * (.toString-Methode).
   * 
   * @return Textdarstellung des laufenden Levels
   */
  public String getLevelString();

  /**
   * Entfernt alle Geister aus dem Spiel und verhindert deren Respawn
   */
  public void removeGhosts();

  /**
   * Versucht die Spielfigur ein Feld nach Oben zu bewegen und gibt aus, ob die
   * Bewegung erfolgreich war. Wenn die Bewegung nicht möglich ist, oder in
   * diesem Tick bereits eine Bewegung durchgeführt wurde, soll der Befehl
   * ignoriert werden.
   * 
   * @return True, wenn die Bewegung möglich war, sonst false.
   */
  public boolean moveUp();

  /**
   * Versucht die Spielfigur ein Feld nach Links zu bewegen und gibt aus, ob die
   * Bewegung erfolgreich war. Wenn die Bewegung nicht möglich ist, oder in
   * diesem Tick bereits eine Bewegung durchgeführt wurde, soll der Befehl
   * ignoriert werden.
   * 
   * @return True, wenn die Bewegung möglich war, sonst false.
   */
  public boolean moveLeft();

  /**
   * Versucht die Spielfigur ein Feld nach Unten zu bewegen und gibt aus, ob die
   * Bewegung erfolgreich war. Wenn die Bewegung nicht möglich ist, oder in
   * diesem Tick bereits eine Bewegung durchgeführt wurde, soll der Befehl
   * ignoriert werden.
   * 
   * @return True, wenn die Bewegung möglich war, sonst false.
   */
  public boolean moveDown();

  /**
   * Versucht die Spielfigur ein Feld nach Rechts zu bewegen und gibt aus, ob
   * die Bewegung erfolgreich war. Wenn die Bewegung nicht möglich ist, oder in
   * diesem Tick bereits eine Bewegung durchgeführt wurde, soll der Befehl
   * ignoriert werden.
   * 
   * @return True, wenn die Bewegung möglich war, sonst false.
   */
  public boolean moveRight();

  /**
   * Platziert die Spielfigur auf einer beliebigen Position, ohne Prüfungen
   * durchzuführen.
   * 
   * @param x
   * @param y
   */
  public void movePacman(int x, int y);

  /**
   * Gibt die aktuelle Position der Spielfigur aus.
   * 
   * @return Aktuelle Position der Spielfigur
   */
  public Point getPacmanPosition();

  /**
   * Bewegt den Geist zu einer beliebigen Position, ohne Prüfungen
   * durchzuführen.
   * 
   * @param x
   * @param y
   */
  public void moveGhost(int x, int y);

  /**
   * Gibt die aktuelle Position des Geists aus.
   * 
   * @return Aktuelle Position des Geists
   */
  public Point getGhostPosition();
    
  /**
   * Führt einen Schritt in der Spiellogik, wie das Bewegen von Figuren und
   * prüfen auf Kollisionen, aus.
   */
  public void update();

  /**
   * Gibt aus, ob das Spiel gewonnen ist.
   * 
   * @return True, wenn das Spiel gewonnen ist, ansonsten false.
   */
  public boolean isWon();

  /**
   * Gibt aus, ob das Spiel verloren ist.
   * 
   * @return True, wenn das Spiel verloren ist, ansonsten false,
   */
  public boolean isLost();

  /**
   * Gibt die aktuelle Punktzahl des Spielers aus.
   * 
   * @return Aktuelle Punktzahl
   */
  public int getPoints();

  /**
   * Gibt aus, wie oft die die Spielfigur unter Einfluss eines PowerUps einen
   * Geist gefressen hat.
   * 
   * @return Anzahl Geisttötungen
   */
  public int getKills();

  /**
   * Gibt aus, ob Pacman aktuell unter Einfluss eines PowerUps steht.
   * 
   * @return True, wenn ein PowerUp aktiv ist, ansonsten false
   */
  public boolean hasPowerUp();

  /**
   * Legt fest, ob Pacman unter dem Einfluss eines PowerUps steht.
   * 
   * @param enable
   */
  public void setPowerUp(boolean enable);
}
