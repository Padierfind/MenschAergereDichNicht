package de.hdm.se2.madn.spielbrett;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm.se2.madn.Feld;
import de.hdm.se2.madn.Feld.feldTyp;
import de.hdm.se2.madn.exceptions.SpielfeldMitEigenemSpielerBesetztException;
import de.hdm.se2.madn.exceptions.UngueltigeFeldWertException;
import de.hdm.se2.madn.exceptions.UngueltigerIndexWertException;
import de.hdm.se2.madn.exceptions.UngueltigerLaufWegException;
import de.hdm.se2.madn.factories.FeldFactory;
import de.hdm.se2.madn.factories.SpielerFactory;
import de.hdm.se2.madn.spieler.SpielerInterface;
import de.hdm.se2.madn.spielfigur.SpielfigurInterface;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolle;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleInterface;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleMADN;

/**
 * @author Lena Haide
 * @email lh075@hdm-stuttgart.de
 * @version 1.0
 */

public class SpielbrettMADN extends Spielbrett{
	
	private final static Logger logger = LogManager.getLogger(SpielbrettMADN.class);
	public static final int ANZAHLSPIELFIGURENPROSPIELER = 4;
	public static final int NORMALEFELDERPROSPIELER = 10;
	
	/**
	 * Der Konstruktor bekommt die Anzahl der Spieler, um eine entsprechende Anzahl an Spielfeldern zu konstruieren.
	 * Die Spielfiguren ID mit der entsprechenden Feld ID auf denen sie stehen müssen.
	 * Wirft eine Exception, wenn nicht die erwarteten Werte beim aufrufen der Klasse mitgegeben werden.
	 * 	
	 * @param spieler
	 * Gibt die Spieler an, die mitspielen
	 * @param spielfigurAufFeld
	 * Enthaelt welche Spielfigur sich auf welchem Feld befindet.
	 * @param spielKontrolle
	 * ermöglicht Zugriff auf Funktionen der spielkontrolle.
	 * @param standartFarbe
	 * ermöglicht das Benuetzen der Klasse Color, von javafx
	 */
	
	public SpielbrettMADN(HashMap<SpielfigurInterface, Integer> spielfigurAufFeld, List<SpielerInterface> spieler, SpielKontrolleInterface spielKontrolle, Color standartFarbe) {
		super(spielKontrolle, standartFarbe);
	
		logger.trace("Konstruktor von SpielbrettMADN wird aufgerufen");
		int spieleranzahl = spieler.size();
		//Spielbrett wird erzeugt
		erstelleFelderMap(spieleranzahl, spieler, (spielfigurAufFeld == null));
		
		if (spielfigurAufFeld != null) {
			//schon vorhandener Spielstand wird ausgelesen
			logger.trace("Schon vorhandener Spielzustand wird ausgelesen.");
			for (Map.Entry<SpielfigurInterface, Integer> e : spielfigurAufFeld.entrySet()) {
				Feld aktuellesFeld = getFeldMitId(e.getValue());
				aktuellesFeld.setSpielfigur(e.getKey());
			}
		}
		
		zeichneIstZustand();

	}
	
	/**
	 * Die Methode erstellt mit hilfe von spieleranzahl, und Spieler neue Felder, denen der typ, eine Id und Farbe zugeordnet werden.
	 * Gleichzeitig werden Die Felder für die naechsteFelder Liste verkettet und wenn es ein neues Spiel ist, werden die Spielfiguren gleich,
	 * auf die entsprechenden Startpositionen gesetzt.
	 * 
	 * @param spieleranzahl
	 * Enthaelt Die anzahl an Spielern, die teilnehmen
	 * @param spieler
	 * Enthaelt eine Liste von allen teilnehmenden spieler.
	 * @param neuesSpiel
	 * bekommt einen boolean, der angibt ob ein neues Spiel angelegt werden muss.
	 */
	private void erstelleFelderMap(Integer spieleranzahl, List<SpielerInterface> spieler, boolean neuesSpiel){
		
		logger.trace("Die Methode erstelleFelderMap() wird aufgerufen");
		
		Feld tempFeld = null;
		Feld zuVerknuepfendesFeld = null;
		Feld erstesEndfeld = null;
		Feld letztesNormalesFeld = null;
		Feld erstesNormalesFeld = null;
		
		// ID Bedeutung
		// Startpositionen Wert 1-4, Endpositionen Wert 5-8
		// SpielerWert 0 10 20 ... Beispiel Spieler 2 Startpositionen 11, 12, .., 14
		
		for (int i = 0; i < spieleranzahl; i++) {
			
			//erzeuge Startpositionen 
			int startID = 10 + i * 10;
			for (int j = 0; j < ANZAHLSPIELFIGURENPROSPIELER; j++) {
				startID++;
				try {
					tempFeld = FeldFactory.getFeld(startID, this);
					tempFeld.setFeldTyp(feldTyp.STARTPOSITION);
					tempFeld.setFarbe(spieler.get(i).getFarbe());
					tempFeld.setSpieler(spieler.get(i));
					if(neuesSpiel){
						tempFeld.setSpielfigur(spieler.get(i).getSpielfigur(j));
					}
					felderList.add(tempFeld);
				} catch (UngueltigeFeldWertException e) {
					logger.error("FeldFactory bekommt nicht die richtigen Werte:", e.getMessage());
					e.printStackTrace();
					System.exit(0);
					logger.fatal("nicht möglich Felder an zu legen, aufgrund von keinen oder ungültigen Werten");
				} catch (UngueltigerIndexWertException e) {
					logger.error("keine passende Spielfigur zu Index gefunden" + j);
					e.printStackTrace();
				}
			} 
			
			//erzeuge Endpositionen
			int endID = 10 + i * 10 + ANZAHLSPIELFIGURENPROSPIELER;
			zuVerknuepfendesFeld = null;
			for (int j = 0; j < ANZAHLSPIELFIGURENPROSPIELER; j++) {
				endID++;
				try {
					tempFeld = FeldFactory.getFeld(endID, this);
					tempFeld.setFeldTyp(feldTyp.ENDPOSITION);
					tempFeld.setFarbe(Color.LIGHTGRAY);
					tempFeld.setSpieler(spieler.get(i));
					if(zuVerknuepfendesFeld != null){
						zuVerknuepfendesFeld.setNaechstesFeld(tempFeld);
					}else{
						erstesEndfeld = tempFeld;
					}
					
					zuVerknuepfendesFeld = tempFeld;
					felderList.add(tempFeld);

				} catch (UngueltigeFeldWertException e) {
					logger.error("FeldFactory bekommt nicht die richtigen Werte:", e.getMessage());
					e.printStackTrace();
					System.exit(0);
				}
			}
			
			//erzeuge Feldpositionen
			int feldID = i * 100 + 100;
			zuVerknuepfendesFeld = null;
			for( int j = 0; j < NORMALEFELDERPROSPIELER; j++){
				feldID++;
				try {
					tempFeld = FeldFactory.getFeld(feldID, this);
					if(j == 1){
						tempFeld.setFeldTyp(feldTyp.STARTFELD);
						tempFeld.setFarbe(spieler.get(i).getFarbe().brighter());
						tempFeld.setSpieler(spieler.get(i));
					}
					if(zuVerknuepfendesFeld != null){
						zuVerknuepfendesFeld.setNaechstesFeld(tempFeld);
					}else{
						tempFeld.setNaechstesFeld(erstesEndfeld);
						if(letztesNormalesFeld != null){
							letztesNormalesFeld.setNaechstesFeld(tempFeld);
						}
						if(i == 0){
							erstesNormalesFeld = tempFeld;
						}
					}
					letztesNormalesFeld = tempFeld;
					zuVerknuepfendesFeld = tempFeld;
					felderList.add(tempFeld);

				} catch (UngueltigeFeldWertException e) {
					logger.error("FeldFactory bekommt nicht die richtigen Werte:", e.getMessage());
					e.printStackTrace();
					System.exit(0);
				}
			}
		}
		letztesNormalesFeld.setNaechstesFeld(erstesNormalesFeld);
	}
	
	/**
	 * Die Methode sucht durch einen Iterator, die felderList ab und überprueft, ob ein Feld eine Startposition ist, frei ist und die selbe farbe,
	 * wie der Spieler hat. Wenn ein entsprechendes Feld gefunden wurde, wird es zurueck gegeben.
	 * 
	 * @param spieler
	 * bekommt den entsprechenden Spieler rein.
	 * @return Feld
	 * gibt ein Feld in den Startpositionen des Spielers zurueck.
	 */
	protected Feld getFreieStartposition(SpielerInterface spieler){
		
		logger.trace("Die Methode getFreieStartposition() wird aufgerufen"); 
		Iterator<Feld> itr = felderList.iterator();
		Feld aktuellesFeld = null;
		
		while(itr.hasNext()){
			
			aktuellesFeld = itr.next();
			
			if(aktuellesFeld.getSpieler() == spieler && aktuellesFeld.istFrei() && aktuellesFeld.getFeldTyp() == feldTyp.STARTPOSITION){
				logger.info("Freie Startposition ist:" + aktuellesFeld.getId());
				break;
			}
		}
		
		return aktuellesFeld;
		
	}
	
	/**
	 * Die Funktion schlaegt die entsprechende Figur. 
	 * 
	 * @param figur
	 * Enthaelt die geschlagene Figur um sie zu identifizieren
	 */
	@Override
	public void schlageSpielfigur(SpielfigurInterface figur) {	//nicht eher Klassenname: setSpielfigurAufAnfang
		logger.trace("Die Methode schlageSpielfigur() wird aufgerufen");
		getFreieStartposition(figur.getGehoertzuSpieler()).setSpielfigur(figur);
		logger.info("die zu schlagende Figur ist:" + figur);
		
	}

	/**
	 * Die Methode ueberprueft ob mindestens eine Startposition besetzt ist und gibt darauf hin einen Boolean zurueck.
	 * 
	 * @param spieler
	 * Gibt die Spielfigur an, die gerade am Zug ist.
	 * @return Boolean
	 * true, wenn es eine besetzte Startposition gibt.
	 */
	public boolean mindestensEineStartpositionBesetzt(SpielerInterface spieler){
		logger.trace("Die Methode mindestensEineStartpositionBesetzt() wird aufgerufen");
		return felderList.parallelStream().anyMatch(x -> x.getSpielfigur() != null && x.getSpielfigur().getGehoertzuSpieler() == spieler && x.getFeldTyp() == feldTyp.STARTPOSITION);
	}
	
	/**
	 * Die Methode ueberprueft, ob das Startfeld mit einer eigenen Figur besetzt ist.
	 * 
	 * @param spieler
	 * Gibt die Spielfigur an, die gerade am Zug ist.
	 * @return Boolean
	 * true, wenn das eigene Startfeld mit einer eigenen Figur besetzt ist.
	 */
	public boolean besetztesStartfeldMitEigenerFigur(SpielerInterface spieler){
		logger.trace("Die Methode besetztesStartfeldMitEigenerFigur() wird aufgerufen");
		return felderList.parallelStream().anyMatch(x -> x.getSpielfigur() != null && x.getSpielfigur().getGehoertzuSpieler() == spieler && x.getFeldTyp() == feldTyp.STARTFELD && x.getSpieler() == spieler);

	}
	
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
	@Override
	public void spielzugMoeglichkeitAnzeigen(Integer feldId, Integer gewuerfelterWert) throws SpielfeldMitEigenemSpielerBesetztException { //sollte doch eher nur berechnen --> Anzeigen Aufgabe GUI
		
		logger.trace("Die Methode spielzugMoeglichkeitAnzeigen() wird aufgerufen");
		HashMap<Integer, Color> felderFarben = new HashMap<Integer, Color>();
		Feld startFeld = getFeldMitId(feldId);
		Feld naechstesFeld = null;
		
		if(startFeld.getSpielfigur() == null 
				|| startFeld.getSpielfigur().getGehoertzuSpieler() != spielKontrolle.getAktiverSpieler()){
			//nichts zu tun in diesem Fall
		} else{

			try {
				naechstesFeld = spielzugLaufen(feldId, gewuerfelterWert);
			} catch (UngueltigerLaufWegException e) {
				logger.error("Figur kann nicht Laufen:", e.getMessage());
				e.printStackTrace();
				this.zeichneIstZustand();
				return;
			}

			this.zeichneIstZustand();
			// wenn eine 6 gewuerfelt wurde, die ausgewaehlte Figur aber auf keiner Startposition steht, obwohl auf den Startpositionen noch Figuren vorhanden sind und das eigene Startfeld nicht mit einer eigenen Figur besetzt ist
			if (gewuerfelterWert == 6 && startFeld.getFeldTyp() != feldTyp.STARTPOSITION && !mindestensEineStartpositionBesetzt(startFeld.getSpielfigur().getGehoertzuSpieler()) && !besetztesStartfeldMitEigenerFigur(startFeld.getSpielfigur().getGehoertzuSpieler())) {
				Iterator<Feld> itr = felderList.iterator();
				Feld aktuellesFeld = null;

				while (itr.hasNext()) {

					aktuellesFeld = itr.next();
					// wird eine besetzte Startposition gesucht, die zum Spieler gehört, und wird als neues feld zum starten gesetzt.
					if (aktuellesFeld.getSpieler() != null && aktuellesFeld.getSpieler().equals(startFeld.getSpielfigur().getGehoertzuSpieler())
							&& aktuellesFeld.istFrei() == false
							&& aktuellesFeld.getFeldTyp() == Feld.feldTyp.STARTPOSITION) {
						startFeld = aktuellesFeld;
						break;
					}
				}

				itr = felderList.iterator();

				while (itr.hasNext()) {

					aktuellesFeld = itr.next();
					//hier wird das zugehoerige Startfeld gesucht, um es als naechstes Feld fest zu legen.
					if (aktuellesFeld.getSpieler() == startFeld.getSpieler()
							&& aktuellesFeld.getFeldTyp() == feldTyp.STARTFELD) {
						naechstesFeld = aktuellesFeld;
						break;
					}
				}
			}

			//wenn auf dem naechsten Feld eine Figur steht, wird geschaut ob diese geschlagen werden kann oder zum eigenen Spieler gehoert.
			if (naechstesFeld.getSpielfigur() != null) {
				if (naechstesFeld.getSpielfigur().getGehoertzuSpieler() != startFeld.getSpielfigur()
						.getGehoertzuSpieler()) {
					logger.info("Eine Figur kann geschlagen werden");

				} else {
					throw new SpielfeldMitEigenemSpielerBesetztException("Spielfigur auf Naechstem Feld gehoert zu Spieler!");
				}
			}

			//Das Feld wo die Spielfigur steht und wo sie landen wuerde, wird mit einer helleren Farbe an zeichne() der Spielkontrolle weitergegeben.
			felderFarben.put(startFeld.getId(), startFeld.getSpielfigur().getFarbe().brighter().brighter());
			felderFarben.put(naechstesFeld.getId(), startFeld.getSpielfigur().getFarbe().brighter().brighter());

			spielKontrolle.zeichne(felderFarben);
		}
	}

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
	@Override
	public void spielzugAusfuehren(Integer feldId, Integer gewuerfelterWert) throws SpielfeldMitEigenemSpielerBesetztException { // frägt jemand ab, ob Figur da überhaupt laufen darf ?
		logger.trace("die Methode spielzugAusfuehren() wurde aufgerufen");
		Feld startFeld = getFeldMitId(feldId);
		Feld naechstesFeld = null;

		if (startFeld.getSpielfigur() == null
				|| startFeld.getSpielfigur().getGehoertzuSpieler() != spielKontrolle.getAktiverSpieler()) {
			//nichts zu tun in diesem Fall
		} else {

			try {
				naechstesFeld = spielzugLaufen(feldId, gewuerfelterWert);
				logger.debug("methode spielzugLaufen wurde ausgeführt");
			} catch (UngueltigerLaufWegException e) {
				logger.error("Figur kann nicht Laufen:", e.getMessage());
				e.printStackTrace();
				this.spielzugUeberspringen();
				return; 
			}
			
			// wenn eine 6 gewuerfelt wurde, die ausgewaehlte Figur aber auf keiner Startposition steht, obwohl auf den Startpositionen noch Figuren vorhanden sind und das eigene Startfeld nicht mit einer eigenen Figur besetzt ist
			if (gewuerfelterWert == 6 && startFeld.getFeldTyp() != feldTyp.STARTPOSITION && mindestensEineStartpositionBesetzt(startFeld.getSpielfigur().getGehoertzuSpieler()) && !besetztesStartfeldMitEigenerFigur(startFeld.getSpielfigur().getGehoertzuSpieler())) {
				Iterator<Feld> itr = felderList.iterator();
				Feld aktuellesFeld = null;

				while (itr.hasNext()) {

					aktuellesFeld = itr.next();
					// wird eine besetzte Startposition gesucht, die zum Spieler gehört, und wird als neues feld zum starten gesetzt.
					if (aktuellesFeld.getSpieler() != null && aktuellesFeld.getSpieler().equals(startFeld.getSpielfigur().getGehoertzuSpieler())
							&& aktuellesFeld.istFrei() == false
							&& aktuellesFeld.getFeldTyp() == Feld.feldTyp.STARTPOSITION) {
						startFeld = aktuellesFeld;
						break;
					}
				}

				itr = felderList.iterator();

				while (itr.hasNext()) {

					aktuellesFeld = itr.next();
					//hier wird das zugehoerige Startfeld gesucht, um es als naechstes Feld fest zu legen.
					if (aktuellesFeld.getSpieler() == startFeld.getSpieler()
							&& aktuellesFeld.getFeldTyp() == feldTyp.STARTFELD) {
						naechstesFeld = aktuellesFeld;
						break;
					}
				}
			}
			//wenn auf dem naechsten Feld eine Figur steht, wird geschaut ob diese geschlagen werden kann oder zum eigenen Spieler gehoert.
			if (naechstesFeld.getSpielfigur() != null) {
				logger.trace("Das zu erreichende feld ist schon besetzt");
				if (naechstesFeld.getSpielfigur().getGehoertzuSpieler() != startFeld.getSpielfigur()
						.getGehoertzuSpieler()) {
					logger.info("SchlageSpielfigur wird von spielzugMöglichkeit aufgerufen");
					schlageSpielfigur(naechstesFeld.getSpielfigur()); 
				} else {
					throw new SpielfeldMitEigenemSpielerBesetztException("Spielfigur auf Endfeld gehoert zu Spieler!");

				}
			}
			
			// Die Spielfigur wird neu gesetzt
			naechstesFeld.setSpielfigur(startFeld.getSpielfigur());
			logger.trace("Figur wurde auf naechstesFeld gesetzt");
			startFeld.setSpielfigur(null);
			logger.trace("Spielfigur wurde von ihrem alten Feld entfernt");
			
			List<Feld> tempFelderList = this.getSpielfelderListe(naechstesFeld.getSpielfigur().getGehoertzuSpieler());
			boolean alleFigurenAufEndFelder = true;
			
			for (Feld aktuellesFeld : tempFelderList) {
				if (aktuellesFeld.getFeldTyp() != Feld.feldTyp.ENDPOSITION) {
					alleFigurenAufEndFelder = false;
					break;
				}
			}
			
			if(alleFigurenAufEndFelder) {
				spielKontrolle.spielerIstFertig(naechstesFeld.getSpieler());
			}

			zeichneIstZustand();
		}
	}
	
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
	@Override
	public Feld spielzugLaufen(Integer feldId, Integer gewuerfelterWert) throws UngueltigerLaufWegException{ // wenn 6 gewürfelt wird, muss vorhandener Spieler raus
		logger.trace("die Methode spielzugLaufen() wurde aufgerufen");
		Feld startFeld = getFeldMitId(feldId);														
		Feld naechstesFeld = startFeld;
		List<Feld> naechsteFelder = null;
		Feld abInsZiel = null;
		
		// wenn der gewuerfelte Wert 6 entspricht und eine Startposition ausgewählt wurde
		if(gewuerfelterWert == 6 && getFeldMitId(feldId).getFeldTyp() == feldTyp.STARTPOSITION ){
			logger.info("Gewuerfelter Wert entspricht 6 und ist eine Startposition");
			
				Iterator<Feld> itr = felderList.iterator();
				Feld aktuellesFeld = null;
				
				while(itr.hasNext()){
					
					aktuellesFeld = itr.next();
					// das zugehörige Startfeld wird gesucht
					if(aktuellesFeld.getSpieler() == startFeld.getSpieler() && aktuellesFeld.getFeldTyp() == feldTyp.STARTFELD){
						logger.debug("Das Passende Feld, hat die ID " + aktuellesFeld.getId());
						naechstesFeld = aktuellesFeld;
					}	
				}
		}else {
			// wen keine 6 gewuerfelt wurde
			logger.info("es wird keine 6 gewuerfelt");
			logger.trace("Das Startfeld" + startFeld.getId());
			// wenn ein startposition ausgewaehlt wurde, wird der Spielzug Uebersprungen
			if (startFeld.getFeldTyp() == feldTyp.STARTPOSITION) {
				spielzugUeberspringen();
			}else{
				for (int i = 0; i < gewuerfelterWert; i++) {
					naechsteFelder = naechstesFeld.getNaechsteFelder();
					// wenn es keine naechsten Felder gibt
					if (naechsteFelder.size() == 0) {
						
						if (gewuerfelterWert > 1) {
							try {
								 return this.spielzugLaufen(feldId, i);
							} catch (UngueltigerLaufWegException e) {
								logger.debug("Funktion spielzugLaufen: UngueltigerLaufWegException in rekursiven Funktionsaufruf. "
										+ "Gewuerfelter Wert = " +Integer.toString(gewuerfelterWert) +" "
												+ "Exception message: "+ e.getMessage());
								throw e;
							}
						}
						
						throw new UngueltigerLaufWegException("Figur würde über Endfeld hinaus gehen!");
					}
					// wenn es nur ein naechstes Feld gibt
					if (naechsteFelder.size() == 1) {
						naechstesFeld = naechsteFelder.get(0);
					// wenn es mehr als 1 naechstes Feld gibt -> muss geschaut werden, ob die Endposition zum Spieler gehört, sonst wird normal weiter gelaufen
					} else {
						for (int k = 0; k < naechsteFelder.size(); k++) {
							if (naechsteFelder.get(k).getSpieler() == startFeld.getSpielfigur().getGehoertzuSpieler() && naechsteFelder.get(k).getFeldTyp() == feldTyp.ENDPOSITION) {
								logger.trace("Spielfigur kann in eigene Endfelder gehen");
								// das Endfeld gehoert zum Spieler, abInsZiel wird auf naechstesFeld gesetzt
								abInsZiel = naechsteFelder.get(k);
							}
						}
						if (abInsZiel == null) {
							// wenn abInsZiel null ist, lauft die Figur normal weiter
							for (int k = 0; k < naechsteFelder.size(); k++) {
								if (naechsteFelder.get(k).getFeldTyp() == feldTyp.NORMAL || naechsteFelder.get(k).getFeldTyp() == feldTyp.STARTFELD) {
									naechstesFeld = naechsteFelder.get(k);
								}
							}
						} else {
							// sonst wird das naechsteFeld abInsZiel
							naechstesFeld = abInsZiel;
						}
					}
				}
			}
		}
		logger.trace("Spielzug laufen wurde vollständig ausgeführt, das naechste Feld hat die ID:" +  naechstesFeld.getId());
		return naechstesFeld;
	}

	/**
	 * Die Funktion geht die ganze felderList durch, und schreibt die feldId mit der zugehoerigen feldFarbe in eine 
	 * zweiwertige Hashmap. Die wird dann an die methode zeichne() der Spielkontrolle uebergeben.
	 */
	@Override
	protected void zeichneIstZustand() { 	

		logger.trace("Die Methode zeichneIstZustand() wurde aufgerufen"); 
		HashMap<Integer, Color> felderFarben = new HashMap<Integer, Color>();
		Color feldFarbe = null;
		
		for(int i = 0; i < felderList.size(); i++){
			int feldId = felderList.get(i).getId();
			// wenn sich eine Spielfigur auf einem Feld befindet, wird der Liste die entsprechende Farbe der Figur zugeordnet
			if(felderList.get(i).getSpielfigur() != null){
				feldFarbe = felderList.get(i).getSpielfigur().getFarbe();
			} else {
				// Startpositionen, die nicht besetzt sind bekommen die Farbe LIGHTGRAY
				if(felderList.get(i).getFeldTyp() == feldTyp.STARTPOSITION){
					feldFarbe = Color.LIGHTGRAY;
				// alle anderen Felder, die nicht besetzt sind bekommen ihre eigene Feldfarbe zugeordnet
				}else {
					feldFarbe = felderList.get(i).getFarbe();
				}
			}
			felderFarben.put(feldId, feldFarbe);
		}
		
		spielKontrolle.zeichne(felderFarben);
		
	}

	/**
	 * Die Methode wird von der GUI gebraucht, um einen Spielzug zu ueberspringen.	
	 */
	@Override
	public void spielzugUeberspringen() {
		logger.trace("Spielzug wird übersprungen!");
		//spielKontrolle.spielzugUeberspringen(); 
		
	}



}
