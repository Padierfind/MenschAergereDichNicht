package de.hdm.se2.madn.oberflaeche.gui;


import javafx.scene.paint.Color;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm.se2.madn.exceptions.SpielfeldMitEigenemSpielerBesetztException;
import de.hdm.se2.madn.oberflaeche.Oberflaechenkontrolle;
import de.hdm.se2.madn.spieler.SpielerInterface;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleInterface;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleMADN;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * @author Patrick Tobler
 * @email pt017@hdm-stuttgart.de
 * @version 1.0
 *
 */

public class GUIKontrolle extends Oberflaechenkontrolle
{
	public int wuerfeln = 0;
	
	private final static Logger logger = LogManager.getLogger(GUIKontrolle.class);

	public int wuerfeln()
	{
		logger.trace("onButtonFireSpielzugÜberspringen() aufgerufen.");

		int gewuerfelterWert = spielKontrolle.wuerfle();
		return gewuerfelterWert;
	};
	
	public static GUI gui;
	public static SpielKontrolleMADN spielKontrolle;
	
	/**
	 * Setzt die guiInstance in die Variable gui.
	 * 
	 * @param guiInstance Eine Instanz der GUI Klasse.
	 */
	public void setGUIInstance(GUI guiInstance)
	{
		logger.trace("setGUIInstance() mit GUIInstance: " + guiInstance + " aufgerufen.");

		gui = guiInstance;
		spielKontrolle = new SpielKontrolleMADN();
	}
	
	public int ausgewaehltesFeld;
	
	public GUIKontrolle(SpielKontrolleInterface spielKontrolle) 
	{
		super(spielKontrolle);
	}
	
	/**
	 * Ruft die Methode starteNeuesSpiel() in der spielKontrolle auf. Übergibt ihr die erhaltenen Parameter.
	 * Ruft die Methode resetGameInterface() in der GUI Klasse auf.
	 */
	
	@Override
	public void start(int anzahlSpieler, int anzahlKISpieler, Color firstplayer, Color secondPlayer, Color thirdPlayer, Color fourthPlayer) 
	{		
		logger.trace("start() mit " + anzahlSpieler + " Spielern und" + anzahlKISpieler + " KI Spielern aufgerufen.");

		spielKontrolle.starteNeuesSpiel(anzahlSpieler, anzahlKISpieler, Arrays.asList(firstplayer, secondPlayer,thirdPlayer,fourthPlayer));
		gui.resetGameInterface();
	}


	/**
	 * Ruft die Methode maleGameGUI() in der GUI Klasse auf. übergibt ihr die erhaltene HashMap felderFarben.
	 */
	@Override
	public void zeichnen(HashMap felderFarben)
	{
		logger.trace("zeichnen() aufgerufen.");

		gui.maleGameGUI(felderFarben);
	}

	/**
	 * Setzt die Variable feldAusgewaehlt auf den Integer feldId.
	 * Ruft die Methode feldAusgewaehlt in der spielKontrolle auf.
	 */
	@Override
	public void feldAusgewaehlt(int feldId)
	{
		logger.trace("Feld mit ID: " + feldId + " in feldAusgewaehlt() aufgerufen.");

		ausgewaehltesFeld = feldId;
		spielKontrolle.feldAusgewaehlt(feldId);
	}

	/**
	 * Ruft die Methode spielzugAusfuehren() in der spielKontrolle auf. Übergibt ihr die Variable feldAusgewaehlt.
	 * Ruft die Methode spieleAlleSpieler() in der spielKontrolle auf.
	 */
	@Override
	public void spielzugAusfuehren()
	{
		logger.trace("spielzugAusfuehren() aufgerufen.");

		try
		{
			spielKontrolle.spielzugAusfuehren(ausgewaehltesFeld);
		} catch (SpielfeldMitEigenemSpielerBesetztException e) 
		{
			logger.error("Fehler beim Versuch die Methode spielzugAusfuehren() in der spielKontrolle-Klasse aufzurufen.");
			e.printStackTrace();
		}
		
		spielKontrolle.spieleAlleSpieler();
	}

	/**
	 * Ruft die Methode spielzugUeberspringen() in der spielKontrolle auf.
	 * Ruft die Methode spieleAlleSpieler() in der spielKontrolle auf.
	 * Setzt den Integer wuerfeln auf einen ungültigen Wert. In diesem Fall 0.
	 */
	@Override
	public void spielzugUeberspringen()
	{
		logger.trace("spielzugÜberspringen() aufgerufen.");

		spielKontrolle.spielzugUeberspringen();
		spielKontrolle.spieleAlleSpieler();
		
		wuerfeln = 0;
	}

	/**
	 * Ruft die Methode spielSpeichern() in der spielKontrolle auf. Übergibt ihr den Pfad und den boolean erzwingeUeberschreiben.
	 */
	@Override
	public void speichern(String pfad, boolean erzwingeUeberschreiben)
	{
		logger.trace("speichern() mit Pfad " + pfad + " aufgerufen. Außerdem ist boolean erzwingeUeberschreiben: " + erzwingeUeberschreiben);

		spielKontrolle.spielSpeichern(pfad, erzwingeUeberschreiben);
	}
	
	/**
	 * Ruft die Methode ladeSpielAusDatei() in der spielKontrolle auf. Übergibt ihr den Pfad.
	 * Ruft die Methode resetGameInterface() in der GUI Klasse auf.
	 */
	@Override
	public void laden(String pfad)
	{
		logger.trace("laden() mit Pfad " + pfad + " aufgerufen.");

		spielKontrolle.ladeSpielAusDatei(pfad);
		gui.resetGameInterface();
	}

	/**
	 * Ruft die Methode zeigeAktuellerSpieler() in der GUI Klasse auf. Übergibt ihr das SpielerInterface des aktuellen Spielers.
	 */
	@Override
	public void zeigeAktuellenSpielerAn(SpielerInterface spieler)
	{
		logger.trace("zeigeAktuellenSpielerAn() aufgerufen.");

		gui.zeigeAktuellerSpieler(spieler);
	}
	
	/**
	 * Ruft die Methode displayVictoryScreen() in der GUI Klasse auf. Übergibt ihr das SpielerInterface des Siegers.
	 */
	@Override
	public void displayVictoryScreen(SpielerInterface winner)
	{
		gui.displayVictoryScreen(winner);
	}
}