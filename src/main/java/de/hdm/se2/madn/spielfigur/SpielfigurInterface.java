package de.hdm.se2.madn.spielfigur;

import de.hdm.se2.madn.spieler.SpielerInterface;
import javafx.scene.paint.Color;

/**
 * @author Lena Haide, Janina Muenzenmayer
 * @email lh075@hdm-stuttgart.de, jm111@hdm-stuttgart.de
 * @version 1.0
 * 
 */

public interface SpielfigurInterface
{

	/**
	 * Methode gibt die Farbe des zugehoerigen Spielers an.
	 * 
	 * @return Gibt die Farbe zurueck.
	 */
	public Color getFarbe();

	/**
	 * Methode gibt den Typ der Spielfigur zurueck.
	 *
	 * @return Typ der Spielfigur
	 */
	public String getTyp();

	/**
	 * Methode gibt an zu welchem Spieler die Spielfigur gehoert.
	 * 
	 * @return Zugehoeriger Spieler
	 */
	public SpielerInterface getGehoertzuSpieler();

}
