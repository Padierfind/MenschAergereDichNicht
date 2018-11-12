package de.hdm.se2.madn.factories;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm.se2.madn.exceptions.InvalidDataException;
import de.hdm.se2.madn.exceptions.UnbekannteSpielfigur;
import de.hdm.se2.madn.exceptions.UnbekannteSpielfigurArtException;
import de.hdm.se2.madn.spieler.KISpielerMADN;
import de.hdm.se2.madn.spieler.MenschlicherSpielerMADN;
import de.hdm.se2.madn.spieler.SpielerInterface;
import de.hdm.se2.madn.spielfigur.SpielfigurInterface;
import de.hdm.se2.madn.spielfigur.SpielfigurMADN;
import javafx.scene.paint.Color;

/**
 * @author Janina Muenzenmayer
 * @email jm111@hdm-stuttgart.de
 * @version 1.0
 * 
 */

public class SpielfigurFactory
{

	private final static Logger logger = LogManager.getLogger(SpielfigurFactory.class);

	private SpielfigurFactory()
	{
	}

	/**
	 * Diese Methode erstellt neue Spielfiguren
	 * 
	 * @param typ
	 *            Typ des Spielfigur: Mensch aergere dich nicht.
	 * @param gehoertZuSpieler
	 *            Zugehoeriger Spieler
	 * @param farbe
	 *            Enthaelt die Farbe des zu erstellenden Spielers.
	 * @return SpielfigurInterface
	 * 			  Die neu erstellte Spielfigur wird zurueckgegeben.
	 * @throws UnbekannteSpielfigur
	 * @throws InvalidDataException
	 */
	public static SpielfigurInterface getSpielfigur(String typ, SpielerInterface gehoertZuSpieler, Color farbe) throws UnbekannteSpielfigur, InvalidDataException
	{
		logger.trace("Die Methode getSpielfigur() der SpielfigurFactory wird aufgerufen.");

		if (gehoertZuSpieler != null && farbe != null)
		{
			logger.debug("Erstelle Spielfigur mit folgenden Eigentschaften: Spielart: " + typ);

			SpielfigurInterface tempSpielfigur = null;

			switch (typ)
			{
			case "MADN":
				tempSpielfigur = new SpielfigurMADN(gehoertZuSpieler, farbe);
				break;
			default:
				throw new UnbekannteSpielfigurArtException( "Ungueltiger Uebergabewert des Spielfigurtyps. Uebergebener Wert: " + typ + ", akzeptierter Wert: MADN.");
			}

			return tempSpielfigur;
		}
		else
		{
			throw new InvalidDataException();
		}
	}

}
