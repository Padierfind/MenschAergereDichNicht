package de.hdm.se2.madn.spielbrett;

import java.util.List;

import de.hdm.se2.madn.Feld;
import de.hdm.se2.madn.exceptions.SpielfeldMitEigenemSpielerBesetztException;
import de.hdm.se2.madn.exceptions.UngueltigerLaufWegException;
import de.hdm.se2.madn.spieler.SpielerInterface;
import de.hdm.se2.madn.spielfigur.SpielfigurInterface;

/**
 * @author Lena Haide
 * @email lh075@hdm-stuttgart.de
 * @version 1.0
 *
 */

public interface SpielbrettInterface {
	
	/**
	 * Die Funktion schlaegt die entsprechende Figur. 
	 * 
	 * @param figur
	 * Enthaelt die geschlagene Figur um sie zu identifizieren
	 */
	public void schlageSpielfigur(SpielfigurInterface figur);
	
	/**
	 * Die Methode ruft die Funktion Zeichne der Spielkontrolle auf, mit einer zweiwertigen HashMap deren Inhalt als Schluessel die aktuelle
	 * Feld ID und eine hellere Farbe als die aktuelle Spielerfarbe enthaelt und die Feld ID auf welchem Feld die Spielfigur landen wuerde.
	 * 
	 * @param feldId
	 * Enthaelt die Integer Id des ausgewaehlten Feldes, auf dem sich die Figur befindet.
	 * @param gewuerfelterWert
	 * Enthaelt den Wert der gewuerfelt wurde.
	 * @throws SpielfeldMitEigenemSpielerBesetztException
	 * Wird geworfen, wenn Figur auf Endfeld von eigenem Spieler ist
	 */
	
	public void spielzugMoeglichkeitAnzeigen(Integer feldId, Integer gewuerfelterWert) throws SpielfeldMitEigenemSpielerBesetztException;
	
	/**
	 * Die Methode fuehrt den Spielzug aus, in dem die Feld Id um den gewuerfelten Wert erhoeht wird. Das ursprüngliche Feld bekommt wieder eine 
	 * normale Farbe und das Neue Feld wird mit der Spieler Farbe gekennzeichnet.
	 * 
	 * @param feldId
	 * Enthaelt die Integer Id des ausgewaehlten Feldes, auf dem sich die Figur befindet.
	 * @param gewuerfelterWert
	 * Enthaelt den Wert der gewuerfelt wurde
	 * @throws SpielfeldMitEigenemSpielerBesetztException 
	 * Wird geworfen, wenn Figur auf Endfeld von eigenem Spieler ist
	 */
	public void spielzugAusfuehren(Integer feldId, Integer gewuerfelterWert) throws SpielfeldMitEigenemSpielerBesetztException;
	
	/**
	 * Die Funktion geht von dem Entsprechenden Feld zur feldId aus und gibt auf grund des gewuerfelterWert, mit dem sie die verkettete Liste naechsteFelder entlang geht,
	 * Das Feld an, auf dem die Spielfigur landen wuerde.
	 * 
	 * @param feldId
	 * Enthaelt die Integer Id des ausgewaehlten Feldes, auf dem sich die Figur befindet.
	 * @param gewuerfelterWert
	 * Enthaelt den Wert, der gewuerfelt wurde.
	 * @return Feld
	 * gibt das Feld zurueck, auf dem die Spielfigur mit dem entsprechenden gewuerfelten Wert landen wuerde.
	 * @throws UngueltigerLaufWegException
	 * Wird geworfen, wenn Spieler mit seinem Zug eine eigene Spielfigur schlagen wuerde
	 */
	public Feld spielzugLaufen(Integer feldId, Integer gewuerfelterWert) throws UngueltigerLaufWegException;
	
	/**
	 * Die Funktion durchsucht alle Felder und gibt eine Liste an Feldern zurueck, auf welche die Spielfiguren des Spielers stehen.
	 * 
	 * @param spieler
	 * Enthaelt den Spieler um die Sielfiguren zu identifizieren.
	 * @return
	 * gibt die Liste der Spielfelder aus. 
	 */
	
	public List<Feld> getSpielfelderListe(SpielerInterface spieler);
	
	/**
	 * Gibt das Feld mit der entsprechenden Id zurueck.
	 * 
	 * @param id
	 * Enthaelt die Integer Id.
	 * 
	 * @return
	 * Gibt das Feld mit der oben entsprechenden Id zurueck.
	 */
	public Feld getFeldMitId(Integer id);
	
	/**
	 * Die Methode wird von der GUI gebraucht, um einen Spielzug zu ueberspringen.	
	 */
	public void spielzugUeberspringen();

}
