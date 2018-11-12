package de.hdm.se2.madn.spielfigur;

import de.hdm.se2.madn.spieler.SpielerInterface;
import javafx.scene.paint.Color;

/**
 * @author Lena Haide, Janina Muenzenmayer
 * @email lh075@hdm-stuttgart.de, jm111@hdm-stuttgart.de
 * @version 1.0
 * 
 */

public abstract class Spielfigur implements SpielfigurInterface
{
	
	protected String typ;

	protected Color farbe;

	protected SpielerInterface gehoertZuSpieler;

	protected Spielfigur(SpielerInterface gehoertZuSpieler, Color farbe)
	{
		this.gehoertZuSpieler = gehoertZuSpieler;
		this.farbe = farbe;
	}

	public Color getFarbe()
	{
		return farbe;
	}

	public abstract String getTyp();

	public SpielerInterface getGehoertzuSpieler()
	{
		return gehoertZuSpieler;
	}
	
}
