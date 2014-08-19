package de.tu_darmstadt.gdi1.pacman.tests;

public interface PacmanTestInterfaceExtended1 extends PacmanTestInterfaceMinimal {
  /**
   * Gibt die aktuelle Blickrichtung der Spielfigur aus. Dabei werden die
   * Richtungen wie folgt kodiert: 0 = Rechts 1 = Oben 2 = Links 3 = Unten
   * 
   * @return Aktuelle Blickrichtung als int kodiert.
   */
  public int getViewDirection();
}
