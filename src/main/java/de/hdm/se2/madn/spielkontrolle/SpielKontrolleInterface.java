package de.hdm.se2.madn.spielkontrolle;

import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.List;

import de.hdm.se2.madn.Feld;
import de.hdm.se2.madn.exceptions.SpielfeldMitEigenemSpielerBesetztException;
import de.hdm.se2.madn.exceptions.UngueltigerLaufWegException;
import de.hdm.se2.madn.spieler.SpielerInterface;

/**
 * @author Philip Betzler
 * @email pb060@hdm-stuttgart.de
 * @version 1.0
 **/

public interface SpielKontrolleInterface {
	
	/**
	 * Funktion speichert den aktuellen Spielstand auf der Festplatte. 
	 * Es werden alle noetigen Daten gesammelt und an die Spielsicherung weitergegeben.
	 * 
	 * @param pfad
	 * Enthaelt den Pfad, an welcher Stelle die Sicherungsdatei nachher liegen soll.
	 */
	public void spielSpeichern(String pfad, boolean erzwingeUeberschreiben);
	
	/**
	 * Zeigt den aktuellen Spieler an, in dem sie eine Funktion der Oberflaechen Kontrolle auf ruft und ihr den aktuellen Spieler uebergibt.
	 */
	public void aktiviereSpielzugDesAktivenSpielers();
	
	public void ladeSpielAusDatei(String pfad);

	/**
	 * gibt die Anzahl an Spielern zurück, die insgesammt dabei waren.
	 * 
	 * @return
	 * Int-Wert der Anzahl an Spielern.
	 */
	public int getAnzahlSpieler();
	
	/**
	 * Startet ein neues Spiel und initialisiert alles mit den Start Parametern.
	 */
	public void starteNeuesSpiel();
	
	/**
	 * Startet ein neues Spiel mit den gespeicherten Werten.
	 * 
	 * @param anzahlSpieler
	 * Enthaelt die urspruengliche Anzahl an Spielern, auch die, die schon fertig sind.
	 * 
	 * @param gewinner
	 * Enthaelt eine Liste an Spieler Ids, die fertig sind, in der Reihenfolge, in der sie fertig wurden.
	 * 
	 * @param figuren
	 * Enthaelt eine Liste, deren Eintraege eine Liste mit den Positionen (FeldIds) der Spielfiguren des Spielers sind.
	 * 
	 * @param aktiverSpieler
	 * Enthaelt den Index des aktuell spielenden Spielers.
	 */
	public void ladeSpielMitParameter (int anzahlSpieler, int anzahlKISpieler, List<Color> spielerFiguren,
			List<Integer> gewinner, List<List<Integer>> figuren, 
			int aktiverSpieler);
	
	/**
	 * Uebermittelt der OberflaechenKontrolle die Felder, die man neu zeichnen muss, mit den entsprechenden Farben.
	 * @param felderFarben
	 * Enthaelt die Felder Farben zuordnung.
	 */
	public void zeichne(HashMap<Integer, Color> felderFarben);
	
	/**
	 * Fuehrt einen Zufallsgenerator aus, um zu einem Wuerfelergebnis zu kommen und gibt den gewuerfelten Wert direkt zurueck.
	 * Zusaetzlich wird die Zustandsvariable spielzustaende auf "gewuerfelt" gesetzt und der Wert gespeichert. 
	 * Falls der Spielzustand gerade kein Wuerfeln zulaesst, wird der bisherige gesetzte Wert zurueck gegeben.
	 * 
	 * @return
	 * Enthaelt den gewuerfelten Wert.
	 */
	public int wuerfle();
	
	/**
	 * Gibt den Wert zurueck, der idealerweise vorhin von der Funktion wuerfle() ermittelt wurde. Falls er nicht initialisiert wurde, betraegt der Wert 0.
	 * @return
	 */
	public int getGewuerfelterWert();
	
	/**
	 * Setzt den gewuerfelten Wert auf 0 und die Zustandsvariable auf "wuerfeln".
	 */
	public void resetGewuerfelterWert();
	
	/**
	 * Methode gibt an die Instanz der Spielbrett Interface weiter, welches Feld gerade ausgewaehlt wurde.
	 * @param feldId
	 * Enthaelt die Integer Id des ausgewaehlten Feldes.
	 */
	public void feldAusgewaehlt(int feldId);
	
	/**
	 * Methode gibt an die Instanz der Spielbrett Interface weiter, welches Feld gerade ausgewaehlt wurde und loest den entsprechenden Zug wirklich aus.
	 * @param feldId
	 * Enthaelt die Integer Id des ausgewaehlten Feldes.
	 */
	public void spielzugAusfuehren(int feldId)  throws SpielfeldMitEigenemSpielerBesetztException;
	
	/**
	 * Gibt eine Liste an Felder zurueck, auf welchen die Spielfiguren des Spielers stehen.
	 * 
	 * @param spieler
	 * Enthaelt den Spieler um die Spielfiguren zu identifizieren.
	 * 
	 * @return
	 * Enthaelt die Liste an Spielfelder.
	 */
	public List<Feld> getSpielfelderListe(SpielerInterface spieler);
	
	/**
	 * Gibt das Feld mit der entsprechenden Id zurueck
	 * 
	 * @param id
	 * Enthaelt die Integer Id
	 * 
	 * @return
	 * Enthaelt das Feld mit der oben entsprechenden Id.
	 */
	public Feld getFeldMitId(int id);
	
	/**
	 * Gibt den momentan Aktiven Spieler zurück
	 * @return SpielerInterface aktiverSpieler
	 */
	public SpielerInterface getAktiverSpieler();
	
	/**
	 * startet ein neues Spiel.
	 * 
	 * @param anzahlSpieler
	 * Gesamt Anzahl Spieler
	 * 
	 * @param anzahlKISpieler
	 * Anzahl KI Spieler
	 * 
	 * @param farben
	 * List<Color> mit den gewünschten Farben. List.size muss >= anzahlSpieler sein!
	 */
	public void starteNeuesSpiel(int anzahlSpieler, int anzahlKISpieler, List<Color> farben);
	
	/**
	 * Zeigt aktuellen Spieler im GUI an
	 */
	public void zeigeAktuellenSpielerAn();
	
	/**
	 * gibt das neue Feld der Spielfigur zurück, die vorher auf dem übergebenen Feld stand. Keine permanente Änderung!
	 * 
	 * @param feldID
	 * Int Wert der Feld ID auf welchem die zu bewegende Spielfigur steht.
	 * 
	 * @param gewuerfelterWert
	 * Int Wert wie weit gelaufen werden soll.
	 * 
	 * @return
	 * Neue Feld Position
	 * 
	 * @throws UngueltigerLaufWegException
	 * Falls Spielfigur aus dem Feld Laufen würde!
	 */
	public Feld spielzugLaufen(int feldID, int gewuerfelterWert) throws UngueltigerLaufWegException;
	
	/**
	 * Überspring den Spielzug des aktuell aktiven Spielers
	 */
	public void spielzugUeberspringen();
	
	/**
	 * Funktion Spieler fuegt den übergebenen Spieler zur Gewinner Liste hinzu
	 * @param spieler
	 * SpielerInterface der uebergebene Spieler 
	 */
	public void spielerIstFertig(SpielerInterface spieler);

}
