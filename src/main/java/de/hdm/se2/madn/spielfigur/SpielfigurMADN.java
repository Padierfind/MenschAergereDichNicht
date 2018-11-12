package de.hdm.se2.madn.spielfigur;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm.se2.madn.spieler.Spieler;
import de.hdm.se2.madn.spieler.SpielerInterface;
import javafx.scene.paint.Color;

/**
 * @author Lena Haide, Janina Muenzenmayer
 * @email lh075@hdm-stuttgart.de, jm111@hdm-stuttgart.de
 * @version 1.0
 * 
 */

public class SpielfigurMADN extends Spielfigur
{
	private final static Logger logger = LogManager.getLogger(SpielfigurMADN.class);

	String typ = "MADN";

	public SpielfigurMADN(SpielerInterface gehoertZuSpieler, Color farbe)
	{
		super(gehoertZuSpieler, farbe);
		logger.trace("Konstruktor von SpielfigurMADN wird aufgerufen.");
	}

	public SpielfigurMADN(SpielerInterface gehoertZuSpieler)
	{
		super(gehoertZuSpieler, gehoertZuSpieler.getFarbe());
		logger.trace("Konstruktor von SpielfigurMADN wird aufgerufen.");
	}

	@Override
	public String getTyp()
	{
		return typ;
	}

}
