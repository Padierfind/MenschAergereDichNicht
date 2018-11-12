package de.hdm.se2.madn.spieler;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm.se2.madn.exceptions.UngueltigerIndexWertException;
import de.hdm.se2.madn.spielfigur.SpielfigurInterface;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleInterface;
import javafx.scene.paint.Color;

/**
 * @author Janina Muenzenmayer
 * @email jm111@hdm-stuttgart.de
 * @version 1.0
 * 
 */

public abstract class Spieler implements SpielerInterface
{

	private final static Logger logger = LogManager.getLogger(Spieler.class);

	protected Color farbe;

	protected SpielKontrolleInterface spielKontrolle;

	protected List<SpielfigurInterface> figurenListe;

	/**
	 * Konstruktor ordnet die ihm uebergebenen Werte fuer farbe und
	 * spielKontrolle den Variablen der Klasse zu.
	 * 
	 * @param farbe
	 *            Parameter enthaelt die Farbe des Spielers.
	 * @param spielKontrolle
	 *            Der Spieler spielt.
	 */
	protected Spieler(Color farbe, SpielKontrolleInterface spielKontrolle)
	{
		this.farbe = farbe;
		this.spielKontrolle = spielKontrolle;
		figurenListe = new ArrayList<>();
	}

	/**
	 * Methode gibt die Farbe des Spielers zurueck.
	 */
	public Color getFarbe()
	{
		return farbe;
	}

	/**
	 * Methode sollte nicht aufgerufen werden und gibt daher eine
	 * RuntimeException zurueck.
	 */
	public void istAktiverSpieler()
	{
		logger.fatal("Die Methode istAktiverSpieler() sollte nicht aufgerufen werden.");
		throw new RuntimeException("Error, Diese Methode sollte nicht aufgerufen werden.");
	}

	/**
	 * Die Methode zeigt den aktuellen Spieler an und ermoeglicht einem
	 * KI-Spieler, seinen Spielzug auszufuehren.
	 */
	public abstract void spiele();

	/**
	 * Methode soll den Verweis zu den Spielfiguren des Spielers angeben bzw. zu
	 * figurenListe hinzufuegen. Wenn der Verweis einer Spielfigur null ist,
	 * wird eine Exception geworfen. spielfigur sollte im besten Fall nicht den
	 * Wert "null" haben.
	 */
	public void addSpielfigur(SpielfigurInterface spielfigur)
	{
		logger.trace("Die Methode addSpielfigur() wird aufgerufen.");
		
		if (spielfigur == null)
		{
			throw new NullPointerException("spielfigur darf nicht den Wert 'Null' enthalten.");
			// Funktion wird hier abgebrochen, sofern die Spielfigur den Wert "null" hat
		}
		
		figurenListe.add(spielfigur);
		logger.info("Eine Spielfigur wurde der Figureliste des Spielers hinzugefuegt.");
	}

	/**
	 * Methode soll den Verweis zu den Spielfiguren des Spielers aus
	 * figurenListe loeschen. Wenn der Verweis einer Spielfigur null ist, wird
	 * eine Exception geworfen, vergleichbar zu addSpielfigur. spielfigur sollte
	 * im besten Fall nicht den Wert "null" haben.
	 */
	public void delSpielfigur(SpielfigurInterface spielfigur)
	{
		logger.trace("Die Methode delSpielfigur() wird aufgerufen.");
		if (spielfigur == null)
		{
			throw new NullPointerException("spielfigur darf nicht den Wert 'Null' enthalten.");
			// Funktion wird hier abgebrochen, sofern die Spielfigur den Wert "null" hat
		}

		figurenListe.remove(spielfigur);
		logger.info("Eine Spielfigur wurde aus der Figureliste des Spielers entfernt.");
	}

	/**
	 * Gibt eine Spielfigur des Spielers, die in der figurenListe enthalten ist,
	 * anhand ihres Index zurueck.
	 * 
	 * @param index
	 *            Der Index einer Spielfigur.
	 * @return figurenListe.get(index) 
	 * 			  Wird zurueckgegeben, wenn der Wert des Index zutrifft. Gibt eine Spielfigur zurueck.
	 * @throws UngueltigerIndexWertException
	 *             Kommt zum Einsatz, falls der Index einen ungueltigen Wert
	 *             annimmt (sh. if-Schleife).
	 */
	public SpielfigurInterface getSpielfigur(Integer index) throws UngueltigerIndexWertException
	{
		logger.trace("Die Methode getSpielfigur() wird aufgerufen.");
		if (figurenListe.size() > index && index >= 0)
		{
			return figurenListe.get(index);
		} else
		{
			throw new UngueltigerIndexWertException();
		}
	}

	public abstract String toString();

}
