package de.hdm.se2.madn.spieler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm.se2.madn.spielkontrolle.SpielKontrolleInterface;
import javafx.scene.paint.Color;

/**
 * @author Janina Muenzenmayer
 * @email jm111@hdm-stuttgart.de
 * @version 1.0
 * 
 */

public class MenschlicherSpielerMADN extends Spieler
{

	private final static Logger logger = LogManager.getLogger(MenschlicherSpielerMADN.class);

	/**
	 * Konstruktor laedt die farbe und spielKontrolle aus der direkten
	 * Superklasse, der abstrakten Klasse Spieler.
	 * 
	 * @param farbe
	 *            Parameter enthaelt die Farbe des Spielers.
	 * @param spielKontrolle
	 *            Der menschliche Benutzer spielt.
	 */
	public MenschlicherSpielerMADN(Color farbe, SpielKontrolleInterface spielKontrolle)
	{
		super(farbe, spielKontrolle);
		logger.trace("Der Konstruktor der Klasse MenschlicherSpielerMADN wird aufgerufen.");
	}

	/**
	 * Methode zeigt den aktuellen Spieler an. Andere Spielzuege werden ueber
	 * die Eingabe des Benutzers ausgefuehrt.
	 */
	@Override
	public void spiele()
	{
		logger.trace("Die Methode spiele() des menschlichen Spielers wird aufgerufen.");

		spielKontrolle.zeigeAktuellenSpielerAn();

		logger.trace("Mensch: Methodenaufruf zeigeAktuellenSpielerAn wurde ausgefuehrt.");
	}

	/**
	 * Methode gibt einen String mit dem Typ des Spielers zurueck: Hier spielt
	 * ein Mensch den Spieltyp Mensch Aergere Dich Nicht.
	 */
	@Override
	public String toString()
	{
		return "Spielertyp: Menschlicher Spieler MADN.";
	}

}
