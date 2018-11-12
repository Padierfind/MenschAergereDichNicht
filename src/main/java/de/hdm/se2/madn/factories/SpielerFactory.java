package de.hdm.se2.madn.factories;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm.se2.madn.exceptions.InvalidDataException;
import de.hdm.se2.madn.exceptions.UnbekannteSpielerArtException;
import de.hdm.se2.madn.exceptions.UnbekannterSpieler;
import de.hdm.se2.madn.exceptions.UnbekannterSpielerTypException;
import de.hdm.se2.madn.spieler.KISpielerMADN;
import de.hdm.se2.madn.spieler.MenschlicherSpielerMADN;
import de.hdm.se2.madn.spieler.SpielerInterface;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleInterface;
import javafx.scene.paint.Color;

/**
 * @author Janina Muenzenmayer
 * @email jm111@hdm-stuttgart.de
 * @version 1.0
 * 
 */

public class SpielerFactory
{
	
	private final static Logger logger = LogManager.getLogger(SpielerFactory.class);

	private SpielerFactory()
	{
	}

	/**
	 * Diese Methode erstellt neue Spieler
	 * 
	 * @param spielartSpieler
	 *            Spielart des Spiels: Mensch aergere dich nicht.
	 * @param spieler
	 *            Unterscheidet zwischen menschlichem oder KI-Spieler.
	 * @param farbe
	 *            Enthaelt die Farbe des zu erstellenden Spielers.
	 * @param spielKontrolle
	 *            Zugriff auf die SpielKontrolle
	 * @return SpielerInterface 
	 * 			  Der neu erstellte Spieler wird zurueckgegeben.
	 * @throws UnbekannterSpieler
	 * @throws InvalidDataException
	 */
	public static SpielerInterface getSpieler(String spielartSpieler, String spieler, Color farbe, SpielKontrolleInterface spielKontrolle) throws UnbekannterSpieler, InvalidDataException
	{
		logger.trace("Die Methode getSpieler() der SpielerFactory wird aufgerufen.");

		if (farbe != null && spielKontrolle != null)
		{
			logger.debug("Erstelle Spieler mit folgenden Eigentschaften: Spielart: " + spielartSpieler + ", spielerTyp: " + spieler + ", Farbe: " + farbe.toString());

			SpielerInterface tempSpieler = null;

			switch (spielartSpieler)
			{
			case "MADN":
				switch (spieler)
				{
				case "KI":
					tempSpieler = new KISpielerMADN(farbe, spielKontrolle);
					break;
				case "Mensch":
					tempSpieler = new MenschlicherSpielerMADN(farbe, spielKontrolle);
					break;
				default:
					throw new UnbekannterSpielerTypException( "Ungueltiger Uebergabewert des Spielertyps. Uebergebener Wert: " + spieler + ", akzeptierter Wert: KI, Mensch.");
				}
				break;

			default:
				throw new UnbekannteSpielerArtException("Ungueltiger Uebergabewert der Spielart. Uebergebener Wert: " + spielartSpieler + ", akzeptierter Wert: MADN.");
			}

			return tempSpieler;
		}
		else
		{
			throw new InvalidDataException();
		}
	}

}
