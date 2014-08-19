package de.tu_darmstadt.gdi1.pacman.tests;


public interface PacmanTestInterfaceExtended2 extends
    PacmanTestInterfaceExtended1 {

  /**
   * Bereitet eine leere Highscore-Liste für die folgenden Aufrufe vor.
   */
  public void prepareHighscore();

  /**
   * Fügt einen neuen Namen mit Punktzahl in die Highscore-Liste ein.
   * 
   * @param name
   *          Spielername
   * @param points
   *          Zugehörige Punktzahl
   */
  public void addToHighscore(String name, int points);

  /**
   * Gibt die absteigend nach Punktzahl sortierte Liste der Spielernamen aus.
   * Die Punktzahlen werden hier nicht ausgegeben, sie bestimmen nur die
   * Sortierung. Am geringsten Index im Array soll also die höchste Punktzahl
   * liegen.
   * 
   * @return Sortiertes Array der Spielernamen
   */
  public String[] getHighscoreNames();
}
