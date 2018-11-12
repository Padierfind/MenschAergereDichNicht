package de.hdm.se2.madn.spielsicherung;

import javafx.scene.paint.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

import de.hdm.se2.madn.exceptions.SicherungsException;
import de.hdm.se2.madn.spieler.SpielerInterface;

/**
 * @author Philip Betzler
 * @email pb060@hdm-stuttgart.de
 * @version 1.0
 * 
 **/

public interface SpielsicherungInterface {
	
	/**
	 * Methode zur Sicherung eines Spielstandes des aktuellen Spiels.
	 * 
	 * 
	 * @param sicherungsPfad
	 * Beschreibt den Pfad an dem die Sicherungsdatei gespeichert werden soll.
	 * 
	 * @param anzahlSpieler
	 * Enthaelt die Anzahl an Spielern die bei Spielbeginn mitgespielt haben.
	 * 
	 * @param gewinner
	 * Enthaelt eine Liste der Spieler, die schon fertig sind, in der entsprechenden Reihenfolge. 
	 * Index 0 -> 1., letzter Index -> letzter Spieler, der fertig wurde.
	 * 
	 * @param figuren
	 * Enthaelt eine Liste, wessen Eintrag eine Liste der Figuren des jeweiligen Spielers sind, 
	 * welche wiederum die Positionen der Figuren auf dem Spielfeld enthalten.
	 * Das heißt, die erste Spielfigur des Spielers Eins, hätte, 
	 * wenn man das List Model auf ein zweidimensionales Array Model ummüntzt, 
	 * den Index [0][0] und den Inhalt der FeldId des Feldes, auf dem die Spielfigur gerade steht.
	 * 
	 * @param aktiverSpieler
	 * Enthaelt den Index des Spielers aus der Liste der immer noch aktiven Spielern, der aktuell am Zug ist.
	 */
	public void sichereSpielstand (String sicherungsPfad, 
			int anzahlSpieler, int anzahlKISpieler, List<Integer> gewinner, 
			List<List<Integer>> figuren, 
			int aktiverSpieler, List<Color> spielerFarben, boolean erzwingeUeberschreiben) 
					throws SicherungsException, FileAlreadyExistsException, IOException;
	
	
	/**
	 * Laedt einen bisherigen Spielstand von der Festplatte und startet das entsprechende Spiel.
	 * Ruft die Methode ladeSpielMitParameter des SpielKontrolleInterface auf.
	 * @see SpielerInterface
	 * 
	 * @param sicherungsPfad
	 * Enthaelt die genaue Pfadangabe zum Spielstand
	 */
	public void ladeSpielstand(String sicherungsPfad) throws SicherungsException, FileNotFoundException, IOException;

}
