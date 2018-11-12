package de.hdm.se2.madn.spielkontrolle;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm.se2.madn.Feld;
import de.hdm.se2.madn.exceptions.BuildingDataException;
import de.hdm.se2.madn.exceptions.UngueltigerLaufWegException;
import de.hdm.se2.madn.factories.SpielfigurFactory;
import de.hdm.se2.madn.oberflaeche.OberflaechenKontrolleInterface;
import de.hdm.se2.madn.oberflaeche.gui.GUIKontrolle;
import de.hdm.se2.madn.spielbrett.SpielbrettInterface;
import de.hdm.se2.madn.spieler.SpielerInterface;
import de.hdm.se2.madn.spielsicherung.SpielsicherungInterface;
import de.hdm.se2.madn.spielsicherung.SpielsicherungMADN;
import javafx.scene.paint.Color;

public abstract class SpielKontrolle implements SpielKontrolleInterface{
	
	private final static Logger logger = LogManager.getLogger(SpielKontrolle.class);
 
	protected int anzahlSpieler = 0;
	protected int anzahlKISpieler = 0;
	protected int gewuerfelterWert = 0;
	protected int aktiverSpielerId = 0;
	protected List<Integer> gewinnerListe;
	protected static boolean spielZuEnde = false;
	
	protected List<SpielerInterface> spielerListe = new ArrayList<>(); 
	protected SpielsicherungInterface spielsicherung;
	protected OberflaechenKontrolleInterface oberflaechenKontrolle;
	protected SpielbrettInterface spielbrett;
	
	public enum Spielzustaende {INIT, WUERFLE, WAEHLE_FELD_AUS, BESTAETIGE_FELD, ZUG_BEENDET};
	
	protected Spielzustaende spielzustand = Spielzustaende.INIT;
	
	
	protected SpielKontrolle() {
		
		spielsicherung = new SpielsicherungMADN(this);
		oberflaechenKontrolle = new GUIKontrolle(this);
		
	}
	
	/**
	 * Initialisiert ein entsprechendes Spiel
	 * 
	 * 
	 * @param anzahlSpieler
	 * Gesamt Anzahl Spieler
	 * 
	 * @param gewinner
	 * List<Integer> bisherige gewinner
	 * 
	 * @param figuren
	 * List<List<Integer>> der Figuren Positionen der einzelnen Spieler
	 * 
	 * @param aktiverSpieler
	 * Int ID des Aktiven Spielers
	 * 
	 * @param anzahlKISpieler
	 * Anzahl KI Spieler im Spiel
	 * 
	 * @param spielerFarben
	 * List<Color> Farben der aktiven Spieler
	 */
	protected abstract void init(int anzahlSpieler, List<Integer> gewinner, List<List<Integer>> figuren, int aktiverSpieler, int anzahlKISpieler, List<Color> spielerFarben);
	
	/**
	 * 
	 * Ueberprueft die uebergebenen Daten auf allgemeine und grobe Unstimmigkeiten.
	 * 
	 *@param anzahlSpieler
	 * Gesamt Anzahl Spieler
	 * 
	 * @param gewinner
	 * List<Integer> bisherige gewinner
	 * 
	 * @param figuren
	 * List<List<Integer>> der Figuren Positionen der einzelnen Spieler
	 * 
	 * @param aktiverSpieler
	 * Int ID des Aktiven Spielers
	 * 
	 * @param anzahlKISpieler
	 * Anzahl KI Spieler im Spiel
	 * 
	 * @param spielerFarben
	 * List<Color> Farben der aktiven Spieler
	 * 
	 * @param anzahlSpielfigureProSpieler
	 * Int anzahl Spielfiguren pro Spieler
	 * 
	 * @return
	 * Boolean ob werte stimmig sein koennten. True wenn stimmig, false wenn was faul ist.
	 */
	protected boolean ueberpruefeAllgemeineInitDaten(int anzahlSpieler, List<Integer> gewinner, List<List<Integer>> figuren, int aktiverSpieler, int anzahlKISpieler, List<Color> spielerFarben, int anzahlSpielfigureProSpieler) {
		
		logger.trace("ueberpruefeAllgemeineInitDaten aufgerufen.");
		
		if (anzahlSpieler < anzahlKISpieler || aktiverSpieler >= anzahlSpieler) {
			
			logger.error("Error: anzahlSpieler < anzahlKISpieler || aktiverSpieler >= anzahlSpieler", anzahlSpieler, anzahlKISpieler, aktiverSpieler);
			return false;
			
		} else if (figuren == null) {
			logger.trace("ueberpruefeAllgemeineInitDaten erfolgreich beendet. Neues Spiel sollte gestartet werden.");
			return true;
			
		} else if (spielerFarben.size() != figuren.size() || anzahlSpieler < figuren.size()) {
			logger.error("Error: spielerFarben.size() != figuren.size() || anzahlSpieler != figuren.size()", 
					Integer.toString(spielerFarben.size()), Integer.toString(figuren.size()), Integer.toString(anzahlSpieler));
			return false;
		} else if(gewinner.size() >= spielerFarben.size()) {
			
			logger.error("gewinner.size() >= spielerFarben.size()", 
					gewinner.size(), spielerFarben.size());
			return false;
			
		} else {
			if (anzahlSpielfigureProSpieler > 0) {
				for (int i = 0; i < figuren.size(); i++) {
					
					if (figuren.get(i).size() != anzahlSpielfigureProSpieler) {
						logger.error("Error: figuren.get(" + Integer.toString(i) + ").size() != " +Integer.toString(anzahlSpielfigureProSpieler));
						return false;
					}
				}
				
				try {
					gewinner.stream().forEach(N -> { if (N == null) {throw new BuildingDataException();}});
					figuren.stream().forEach(N -> { if (N == null) {throw new BuildingDataException();}});
					figuren.stream().forEach(N -> N.forEach (M -> {if (M == null) { throw new BuildingDataException();}}));
					spielerFarben.stream().forEach(N -> { if (N == null) {throw new BuildingDataException();}});
				} catch (RuntimeException e) {
					logger.error("Error: NullPointerException!", gewinner, figuren, spielerFarben);
					return false;
				}
				
			}
			
			
		}
		
		logger.trace("ueberpruefeAllgemeineInitDaten erfolgreich beendet.");
		return true;
		
	}
	
	/**
	 * Errechnet den naechsten Spieler und ueberprueft ob er ueberhaupt noch spielen muss.
	 */
	protected void calcNaechsteSpielerId() {
		
		if (gewinnerListe.size() == spielerListe.size() && spielerListe.size() != 0) {
			this.spielZuEnde = true;
			oberflaechenKontrolle.displayVictoryScreen(spielerListe.get(gewinnerListe.get(0)));
			aktiverSpielerId = 0;
			return;
		} 
		
		aktiverSpielerId++;
		aktiverSpielerId %= anzahlSpieler;
		
		
		
		while(gewinnerListe.contains(aktiverSpielerId)) {
			aktiverSpielerId++;
			aktiverSpielerId %= anzahlSpieler;
		}
	}
	
	/**
	 * Ruft spielzugLaufen im Spielbrett auf.
	 */
	public Feld spielzugLaufen(int feldID, int gewuerfelterWert) throws UngueltigerLaufWegException {
		return spielbrett.spielzugLaufen(feldID, gewuerfelterWert);
	}

}
