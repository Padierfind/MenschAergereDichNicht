package de.hdm.se2.madn.oberflaeche;

import javafx.scene.paint.Color;
import java.util.HashMap;

import de.hdm.se2.madn.spieler.SpielerInterface;

/**
 * @author Patrick Tobler
 * @email pt017@hdm-stuttgart.de
 * @version 1.0
 *
 */
public interface OberflaechenKontrolleInterface 
{
	void start(int anzahlSpieler, int anzahlKISpieler, Color firstPlayer, Color secondPlayer, Color thirdPlayer, Color fourthPlayer);
	void zeichnen(HashMap felderFarben);
	int wuerfeln();
	void feldAusgewaehlt(int feldId);
	void spielzugAusfuehren();
	void speichern(String pfad, boolean erzwingeUeberschreiben);
	void laden(String pfad);
	void zeigeAktuellenSpielerAn(SpielerInterface spieler);
	void spielzugUeberspringen();
	void displayVictoryScreen(SpielerInterface winner);
}