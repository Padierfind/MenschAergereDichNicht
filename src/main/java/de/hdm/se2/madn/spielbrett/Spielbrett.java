package de.hdm.se2.madn.spielbrett;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm.se2.madn.Feld;
import de.hdm.se2.madn.exceptions.SpielfeldMitEigenemSpielerBesetztException;
import de.hdm.se2.madn.exceptions.UngueltigerLaufWegException;
import de.hdm.se2.madn.factories.SpielerFactory;
import de.hdm.se2.madn.spieler.SpielerInterface;
import de.hdm.se2.madn.spielfigur.SpielfigurInterface;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleInterface;

/**
 * @author Lena Haide
 * @email lh075@hdm-stuttgart.de
 * @version 1.0
 *
 */

public abstract class Spielbrett implements SpielbrettInterface {
	
	private final static Logger logger = LogManager.getLogger(Spielbrett.class);
	
	protected SpielKontrolleInterface spielKontrolle;
	protected Color standartFarbe;
	protected List<Feld> felderList;
	
	protected Spielbrett(SpielKontrolleInterface spielKontrolle, Color standartFarbe) {
		this.spielKontrolle = spielKontrolle;
		this.standartFarbe = standartFarbe;
		this.felderList = new ArrayList<Feld>();
	}
	
	/**
	 * Die Funktion schlaegt die entsprechende Figur. 
	 * 
	 * @param figur
	 * Enthaelt die geschlagene Figur um sie zu identifizieren
	 */
	public abstract void schlageSpielfigur(SpielfigurInterface figur);
	
	/**
	 * Die Methode ruft die Funktion Zeichne der Spielkontrolle auf, mit einer zweiwertigen HashMap deren Inhalt als Schluessel die aktuelle
	 * Feld ID und eine hellere Farbe als die aktuelle Spielerfarbe enthaelt und die Feld ID auf welchem Feld die Spielfigur landen wuerde.
	 * 
	 * @param feldId
	 * Enthaelt die Integer Id des ausgewaehlten Feldes, auf dem sich die Figur befindet.
	 * @param gewuerfelterWert
	 * Enthaelt den Wert der gewuerfelt wurde.
	 * @throws SpielfeldMitEigenemSpielerBesetztException
	 * Wird geworfen, wenn Figur auf Endfeld von eigenem Spieler ist
	 */
	public abstract void spielzugMoeglichkeitAnzeigen(Integer feldId, Integer gewuerfelterWert) throws SpielfeldMitEigenemSpielerBesetztException;
	
	/**
	 * Die Methode fuehrt den Spielzug aus, in dem die Feld Id um den gewuerfelten Wert erhoeht wird. Das ursprüngliche Feld bekommt wieder eine 
	 * normale Farbe und das Neue Feld wird mit der Spieler Farbe gekennzeichnet.
	 * 
	 * @param feldId
	 * Enthaelt die Integer Id des ausgewaehlten Feldes, auf dem sich die Figur befindet.
	 * @param gewuerfelterWert
	 * Enthaelt den Wert der gewuerfelt wurde
	 * @throws SpielfeldMitEigenemSpielerBesetztException 
	 * Wird geworfen, wenn Figur auf Endfeld von eigenem Spieler ist
	 */
	public abstract void spielzugAusfuehren(Integer feldId, Integer gewuerfelterWert) throws SpielfeldMitEigenemSpielerBesetztException;
	
	/**
	 * Die Funktion geht von dem Entsprechenden Feld zur feldId aus und gibt auf grund des gewuerfelterWert, mit dem sie die verkettete Liste naechsteFelder entlang geht,
	 * Das Feld an, auf dem die Spielfigur landen wuerde.
	 * 
	 * @param feldId
	 * Enthaelt die Integer Id des ausgewaehlten Feldes, auf dem sich die Figur befindet.
	 * @param gewuerfelterWert
	 * Enthaelt den Wert, der gewuerfelt wurde.
	 * @return Feld
	 * gibt das Feld zurueck, auf dem die Spielfigur mit dem entsprechenden gewuerfelten Wert landen wuerde.
	 * @throws UngueltigerLaufWegException
	 * Wird geworfen, wenn Spieler mit seinem Zug eine eigene Spielfigur schlagen wuerde
	 */
	public abstract Feld spielzugLaufen(Integer feldId, Integer gewuerfelterwert) throws UngueltigerLaufWegException;
	
	/**
	 * Die Funktion durchsucht alle Felder und gibt eine Liste an Feldern zurueck, auf welche die Spielfiguren des Spielers stehen.
	 * 
	 * @param spieler
	 * Enthaelt den Spieler um die Sielfiguren zu identifizieren.
	 * @return
	 * gibt die Liste der Spielfelder aus. 
	 */
	public List<Feld> getSpielfelderListe(SpielerInterface spieler) {
		
		logger.trace("Die Methode getSpielfelderListe() wird aufgerufen");
		List<Feld> spielfigurAufFeld = new ArrayList<Feld>();
		Iterator<Feld> itr = felderList.iterator();
		
		while(itr.hasNext()){
			
			
			Feld aktuellesFeld = itr.next();
			logger.debug("aktuelles Feld: " + aktuellesFeld.getId());
	
			if(aktuellesFeld.getSpielfigur()!= null){
				logger.debug("aktuelle Feld Farbe: " + aktuellesFeld.getFarbe());
				if(aktuellesFeld.getSpielfigur().getGehoertzuSpieler().equals(spieler)){
					logger.info("ein zugehöriges Feld wurde gefunden");
					spielfigurAufFeld.add(aktuellesFeld);
				}
			}
		}
		
		return spielfigurAufFeld;
	}
	
	/**
	 * Gibt das Feld mit der entsprechenden Id zurueck.
	 * 
	 * @param id
	 * Enthaelt die Integer Id.
	 * 
	 * @return
	 * Gibt das Feld mit der oben entsprechenden Id zurueck.
	 */
	public Feld getFeldMitId(Integer id) {
		
		logger.trace("Die Methode getFeldMitId() wird aufgerufen");
		return felderList.parallelStream().filter(x -> x.getId() == id).findFirst().orElse(null);
		
	}
	
	/**
	 * Die Funktion geht die ganze felderList durch, und schreibt die feldId mit der zugehoerigen feldFarbe in eine 
	 * zweiwertige Hashmap. Die wird dann an die methode zeichne() der Spielkontrolle uebergeben.
	 */
	protected abstract void zeichneIstZustand();
	
	/**
	 * Die Methode wird von der GUI gebraucht, um einen Spielzug zu ueberspringen.	
	 */
	public abstract void spielzugUeberspringen();
	

}
