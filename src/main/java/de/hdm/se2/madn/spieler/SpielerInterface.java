package de.hdm.se2.madn.spieler;

import de.hdm.se2.madn.exceptions.UngueltigerIndexWertException;
import de.hdm.se2.madn.spielfigur.SpielfigurInterface;
import javafx.scene.paint.Color;

/**
 * @author Janina Muenzenmayer
 * @email jm111@hdm-stuttgart.de
 * @version 1.0
 * 
 */

public interface SpielerInterface
{

	/**
	 * Methode gibt die Farbe des entsprechenden Spielers an.
	 * 
	 * @return Parameter 
	 *            enthaelt die Farbe des Spielers.
	 */
	public Color getFarbe();

	/**
	 * Methode sollte nicht aufgerufen werden und gibt daher eine
	 * RuntimeException zurueck.
	 */
	public void istAktiverSpieler();

	/**
	 * Die Methode zeigt den aktuellen Spieler an und ermoeglicht einem
	 * KI-Spieler, seinen Spielzug auszufuehren.
	 */
	public void spiele();

	/**
	 * Methode soll den Verweis zu den Spielfiguren des Spielers angeben bzw. zu
	 * figurenListe hinzufuegen. Wenn der Verweis einer Spielfigur null ist,
	 * wird eine Exception geworfen.
	 * 
	 * @param spielfigur
	 *            Verweis einer zugehörigen Spielfigur
	 */
	public void addSpielfigur(SpielfigurInterface spielfigur);

	/**
	 * Methode soll den Verweis zu den Spielfiguren des Spielers aus
	 * figurenListe loeschen. Wenn der Verweis einer Spielfigur null ist, wird
	 * eine Exception geworfen, vergleichbar zur Methode addSpielfigur.
	 *
	 * @param spielfigur
	 *            Verweis einer zugehoerigen Spielfigur
	 */
	public void delSpielfigur(SpielfigurInterface spielfigur);

	/**
	 * Gibt eine Spielfigur des Spielers, die in der figurenListe enthalten ist,
	 * anhand ihres Index zurueck.
	 * 
	 * @param index
	 *            Der Index einer Spielfigur.
	 * @return figurenListe.get(index) 
	 *            Wird zurueckgegeben, wenn der Wert des Index zutrifft. Gibt eine Spielfigur zurueck.
	 * @throws UngueltigerIndexWertException
	 *             Kommt zum Einsatz, falls der Index einen ungueltigen Wert
	 *             annimmt (sh. if-Schleife).
	 */
	public SpielfigurInterface getSpielfigur(Integer index) throws UngueltigerIndexWertException;

}
