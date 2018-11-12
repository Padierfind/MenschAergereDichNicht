package de.hdm.se2.madn.spielkontrolle;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm.se2.madn.Feld;
import de.hdm.se2.madn.exceptions.BuildingDataException;
import de.hdm.se2.madn.exceptions.InvalidDataException;
import de.hdm.se2.madn.exceptions.SicherungsException;
import de.hdm.se2.madn.exceptions.SpielfeldMitEigenemSpielerBesetztException;
import de.hdm.se2.madn.exceptions.UnbekannteSpielfigur;
import de.hdm.se2.madn.exceptions.UnbekannterSpieler;
import de.hdm.se2.madn.exceptions.UnbekannterSpielerTypException;
import de.hdm.se2.madn.exceptions.UngueltigerIndexWertException;
import de.hdm.se2.madn.factories.SpielerFactory;
import de.hdm.se2.madn.factories.SpielfigurFactory;
import de.hdm.se2.madn.spielbrett.SpielbrettMADN;
import de.hdm.se2.madn.spieler.KISpielerMADN;
import de.hdm.se2.madn.spieler.MenschlicherSpielerMADN;
import de.hdm.se2.madn.spieler.SpielerInterface;
import de.hdm.se2.madn.spielfigur.SpielfigurInterface;
import javafx.scene.paint.Color;

/**
 * 
 * @author Philip Betzler
 * @email pb060@hdm-stuttgart.de
 * @version 1.0
 *
 */
public class SpielKontrolleMADN extends SpielKontrolle{
	
	
	private final static Logger logger = LogManager.getLogger(SpielKontrolleMADN.class);
	private int errorCounter= 0;
	private static final int MAXANZAHLSPEICHERVERSUCHE = 10;
	private static final int STANDARTANZAHLSPIELER = 4;
	private static final int STANDARTANZAHLKISPIELER = 3;
	private static final int ANZAHLSPIELFIGURENPROSPIELER = 4;
	private static Color STANDARTCOLOR = Color.BLUE;

	public SpielKontrolleMADN() {
		super();
		
		logger.debug("SpielKontrolle wurde angelegt.");
	}

	@Override
	public void spielSpeichern(String pfad, boolean erzwingeUeberschreiben) {
		List<Color> spielerFarben = new ArrayList<Color>();
		
		for (SpielerInterface sp : spielerListe) {
			spielerFarben.add(sp.getFarbe());
		}
		
		List<List<Integer>> figuren= new ArrayList<>();
		
		for (int i = 0; i < spielerListe.size(); i++) {
			figuren.add(new ArrayList<>());
			List<Feld> tempFeld = spielbrett.getSpielfelderListe(spielerListe.get(i));
			for (Feld f : tempFeld) {
				figuren.get(i).add(f.getId());
			}
		}
		
		try {
			
			spielsicherung.sichereSpielstand(pfad, anzahlSpieler, anzahlKISpieler, gewinnerListe, figuren, aktiverSpielerId, spielerFarben, erzwingeUeberschreiben);
			
		} catch (FileAlreadyExistsException e) {
			logger.info("Sicherungsfile existiert bereits, aber Nutzer will es nicht ueberschreiben. " +e.getMessage());
		} catch (SicherungsException e) {
			logger.error("Error, Interner Fehler beim Spielspeicherung erstellen. Spiel nicht gespeichert.", e);
		} catch (IOException e) {
			errorCounter++;
			logger.error("Error, IOException beim Spielspeichern. Fehler Nummer: " + Integer.toString(errorCounter) + ".");
			if (errorCounter < MAXANZAHLSPEICHERVERSUCHE) {
				try {
					logger.error("Versuche Speicherzugriff erneut.");
					this.spielSpeichern(pfad, erzwingeUeberschreiben);
				} catch (Exception e2) {
					logger.error("Error, errorCounter bei: " + Integer.toString(errorCounter) + " angelangt. Spiel nicht gespeichert.");
				}
			}
		}
		errorCounter = 0;
		
	}

	@Override
	public void aktiviereSpielzugDesAktivenSpielers() {
		spielzustand = Spielzustaende.WUERFLE;
		spielerListe.get(aktiverSpielerId).spiele();
		
	}

	@Override
	public int getAnzahlSpieler() {
		return anzahlSpieler;
	}

	@Override
	public void starteNeuesSpiel() {
		logger.info("Spiel ist mit Standarteinstellungen gestartet.");
		
		this.starteNeuesSpiel(STANDARTANZAHLSPIELER, STANDARTANZAHLKISPIELER, Arrays.asList(Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN));
		
	}

	@Override
	public void starteNeuesSpiel(int anzahlSpieler, int anzahlKISpieler, List<Color> list) {
		List<Color> spielerFarben = list;
		this.init(anzahlSpieler, new ArrayList(), null, 0, anzahlKISpieler, spielerFarben);
		this.spieleAlleSpieler();
	}

	@Override
	public void ladeSpielMitParameter(int anzahlSpieler, int anzahlKISpieler, List<Color> spielerFarben,
			List<Integer> gewinner, List<List<Integer>> figuren, int aktiverSpieler) {
		logger.info("Lade und starte neues Spiel");
		this.init(anzahlSpieler, gewinner, figuren, aktiverSpieler, anzahlKISpieler, spielerFarben);
		logger.info("starte Spielzug des nächsten Spielers!");
		this.spieleAlleSpieler();
		
	}

	@Override
	public void zeichne(HashMap<Integer, Color> felderFarben) {
		logger.debug("Zeichne Spieler Zug auf.");
		oberflaechenKontrolle.zeichnen(felderFarben);
		
	}

	@Override
	public int wuerfle() {
		
		logger.debug("Wuerfle Funktion aufgerufen.");
		
		if(this.spielzustand == Spielzustaende.WUERFLE) {
			
			SecureRandom random = new SecureRandom();
			
		    int tempInt = random.nextInt(6);
			//int tempInt = random.nextInt(2);
		    //TODO Test 6
		    this.gewuerfelterWert = tempInt +1;
			//this.gewuerfelterWert = tempInt +5;
		    //this.gewuerfelterWert = 6;
			
			spielzustand = Spielzustaende.WAEHLE_FELD_AUS;
			logger.debug("Korrekter Wuerfle Aufruf, gewuerfelter Wert war: " +Integer.toString(gewuerfelterWert) +", Zustand gewechselt. ");
			return gewuerfelterWert;
		}
		
		logger.warn("Falscher Wuerfle Aufruf! Wuerfle in Zustand: " +spielzustand.toString() +" aufgerufen!");
		return gewuerfelterWert;
	}

	@Override
	public int getGewuerfelterWert() {
		logger.trace("Gewuerfelter Wert abgefragt. Wert war: " +Integer.toString(gewuerfelterWert));
		return gewuerfelterWert;
	}

	@Override
	public void resetGewuerfelterWert() {
		logger.debug("resetGewuerfelterWert aufgerufen.");
		if (spielzustand == Spielzustaende.BESTAETIGE_FELD) {
			gewuerfelterWert = 0;
			spielzustand = Spielzustaende.ZUG_BEENDET;
			logger.trace("Korrekter Aufruf von resetGewuerfelterWert, Wert resetet.");
			return;
		}
		
		logger.warn("Falscher Aufruf von resetGewuerfelterWert!");
		
	}

	@Override
	public void feldAusgewaehlt(int feldId) {
		logger.debug("feldAusgewaehlt aufgerufen.");
		if (spielzustand == Spielzustaende.WAEHLE_FELD_AUS) {
			
			try {
				spielbrett.spielzugMoeglichkeitAnzeigen(feldId, gewuerfelterWert);
				
			} catch (SpielfeldMitEigenemSpielerBesetztException e) {
				logger.info("Spielfigur zum laufen ausgewaehlt, die eigene Figur schlagen würde. Es wird nichts angezeigt.", feldId, gewuerfelterWert);
			}
			
			logger.trace("Korrekter Aufruf von feldAusgewaehlt.");
			return;
		}
		
		logger.warn("Falscher Aufruf von feldAusgewaehlt!");
		
	}

	@Override
	public void spielzugAusfuehren(int feldId) throws SpielfeldMitEigenemSpielerBesetztException {
		logger.debug("spielzugAusfuehren aufgerufen.");
		if (spielzustand == Spielzustaende.WAEHLE_FELD_AUS) {
			logger.trace("Korrekter Aufruf von spielzugAusfuehren.");
			spielzustand = Spielzustaende.BESTAETIGE_FELD;
			try {
				spielbrett.spielzugAusfuehren(feldId, gewuerfelterWert);
				
			} catch (SpielfeldMitEigenemSpielerBesetztException e) {
				logger.error("Spielfigur zum laufen ausgewaehlt, die eigene Figur schlagen würde. Es wird nichts angezeigt.", feldId, gewuerfelterWert);
				//throw e; // Auskommentiert von Patrick, weil: Wenn es nicht auskommentiert ist, kann kein Zugübersprungen werden, wenn bereits ein Feld ausgewählt ist.
			}
			
			
			resetGewuerfelterWert();
			logger.trace("spieleAlleSpieler: rufe calcNaechsteSpielerId() auf!");
			this.calcNaechsteSpielerId();
			if (spielerListe.get(aktiverSpielerId) instanceof MenschlicherSpielerMADN) {
				this.spieleAlleSpieler();
			}
			
			return;
			
		}
		logger.warn("Falscher Aufruf von spielzugAusfuehren!");
	}

	@Override
	public List<Feld> getSpielfelderListe(SpielerInterface spieler) {
		logger.debug("getSpielfelderListe aufgerufen.");
		return spielbrett.getSpielfelderListe(spieler);
	}

	@Override
	public Feld getFeldMitId(int id) {
		logger.debug("getFeldMitId aufgerufen.");
		return spielbrett.getFeldMitId(id);
	}

	@Override
	protected void init(int anzahlSpieler, List<Integer> gewinner, List<List<Integer>> figuren, int aktiverSpieler,
			int anzahlKISpieler, List<Color> spielerFarben) {
		logger.debug("init aufgerufen.");
		
		this.spielbrett = null;
		this.spielerListe.clear();
		
		if (gewinner != null) {
			this.gewinnerListe = gewinner;
		} else {
			this.gewinnerListe = new ArrayList<Integer>();
		}
		
		if (!ueberpruefeAllgemeineInitDaten(anzahlSpieler, this.gewinnerListe, figuren, aktiverSpieler, anzahlKISpieler, spielerFarben, 4)) {
			logger.error("Error, init mit invaliden Daten!", anzahlSpieler, gewinner, figuren, aktiverSpieler, anzahlKISpieler, spielerFarben, 4);
			throw new BuildingDataException();
		}
		
		logger.debug("Init Variablen korrekt.");
		
		this.anzahlSpieler = anzahlSpieler;
		this.anzahlKISpieler = anzahlKISpieler;
		this.aktiverSpielerId = aktiverSpieler - 1; // -1 Da die Person, die dran war, als gespeichert wurde, nicht übersprungen werden soll!
		
		logger.trace("Int zugewiesen");
		
		this.calcNaechsteSpielerId();
		logger.trace("aktiver Spieler validiert.");
		
		int kiSpielerUebrig = anzahlKISpieler;
		int menschlicheSpielerUebrig = anzahlSpieler - anzahlKISpieler;
		for (int i = 0; i < anzahlSpieler; i++) {
		
			if ((i % 2 == 1 && kiSpielerUebrig > 0) || menschlicheSpielerUebrig == 0) {
				try {
					try {
						spielerListe.add(SpielerFactory.getSpieler("MADN", "KI", spielerFarben.get(i), this));
					} catch (InvalidDataException e) {
						logger.fatal("Error, Color or spielkontrolle reference where null!", spielerFarben.get(i), this);
						e.printStackTrace();
						System.exit(1);
					}
					kiSpielerUebrig--;
					logger.trace("KI Spieler kreirt");
				} catch (UnbekannterSpieler e) {
					logger.fatal("Fataler Fehler! Kann KI Spieler nicht anlegen!");
					System.exit(1);
				}
				
			} else {
				try {
					try {
						spielerListe.add(SpielerFactory.getSpieler("MADN", "Mensch", spielerFarben.get(i), this));
					} catch (InvalidDataException e) {
						logger.fatal("Error, Color or spielkontrolle reference where null!", spielerFarben.get(i), this);
						e.printStackTrace();
						System.exit(1);
					}
					menschlicheSpielerUebrig--;
					logger.trace("Menschlicher Spieler kreirt");
				} catch (UnbekannterSpieler e) {
					logger.fatal("Fataler Fehler! Kann Menschlichen Spieler nicht anlegen!");
					System.exit(1);
				}
				
			}
			
			for(int j = 0; j < ANZAHLSPIELFIGURENPROSPIELER; j++) {
				try {
					try {
						spielerListe.get(i).addSpielfigur(SpielfigurFactory.getSpielfigur("MADN", spielerListe.get(i), spielerListe.get(i).getFarbe()));
					} catch (InvalidDataException e) {
						logger.fatal("Fataler Error, kann keine Spielfigur anlegen!", spielerListe.get(i), spielerListe.get(i).getFarbe());
						e.printStackTrace();
					}
				} catch (UnbekannteSpielfigur e) {
					logger.fatal("Fatal Error, kann keine Spielfiguren anlegen!");
					e.printStackTrace();
					System.exit(1);
				}
			}
			
		}
		
		HashMap<SpielfigurInterface, Integer> spielfigurAufFeld;
		
		if (figuren == null) {
			spielfigurAufFeld = null;
		} else {
			spielfigurAufFeld = new HashMap<>();
			for (int i = 0; i < figuren.size(); i++) {
				for (int j = 0; j < figuren.get(i).size(); j++) {
					try {
						spielfigurAufFeld.put(spielerListe.get(i).getSpielfigur(j), figuren.get(i).get(j));
					} catch (UngueltigerIndexWertException e) {
						logger.error("Fatal Error, kann keine Start Positionen festlegen.");
						e.printStackTrace();
						System.exit(1);
					}
				}
			}
		}
		
		
		this.spielbrett = new SpielbrettMADN(spielfigurAufFeld, spielerListe, this, STANDARTCOLOR);
		
		logger.trace("Spieler kreirt");
		
		logger.debug("init ende.");
		spielzustand = Spielzustaende.WUERFLE;
	}
	
	
	@Override
	public void spielerIstFertig(SpielerInterface spieler) {
		logger.debug("Funktion spielerIstFertig aufgerufen.", spieler);
		if (spielzustand == Spielzustaende.BESTAETIGE_FELD) {
			gewinnerListe.add(spielerListe.indexOf(spieler));
			logger.trace("Funktion Korrekt aufgerufen.");
			return;
		}
		logger.warn("Warnung, Funktion spielerIstFertig außerhalb des korrekten Zustands aufgerufen!", spielzustand, spieler);
				
	}

	@Override
	public void ladeSpielAusDatei(String pfad) {
		try {
			logger.debug("Lade Spielstand aus Datei: " + pfad, pfad);
			spielsicherung.ladeSpielstand(pfad);
		} catch (IOException e) {
			logger.error("Achtung, IOException beim Spiel laden!", e);
			errorCounter++;
			if (errorCounter < MAXANZAHLSPEICHERVERSUCHE) {
				try {
					spielsicherung.ladeSpielstand(pfad);
				} catch (Exception e2) {
					logger.fatal("Beim Spielladen ist was ziemlich schiefgegangen! Starte Neues Spiel");
					this.starteNeuesSpiel();
				}
				
			}
		} catch (SicherungsException e) {
			logger.error("SicherungsException! Kann Spielstand nicht lesen. Starte neues Spiel");
			this.starteNeuesSpiel();
		}
		errorCounter = 0;
		
	}
	
	@Override
	public SpielerInterface getAktiverSpieler() {
		return this.spielerListe.get(aktiverSpielerId);
	}
	
	/**
	 * zeigt übergebenen Spieler im GUI an, falls er gerade wirklich an der Reihe ist!
	 * Ansonsten wird nicht gemacht.
	 * 
	 * @param spieler
	 * SpielerInterface der Spieler der Angezeigt werden soll
	 */
	public void zeigeAktuellenSpielerAn(SpielerInterface spieler) {
		if (this.spielerListe.get(aktiverSpielerId).equals(spieler)) {
			this.oberflaechenKontrolle.zeigeAktuellenSpielerAn(spieler);
			return;
		}
		
		logger.warn("Warnung! Aktuell nicht spielender Spieler versucht sich anzuzeigen.");
		
	}

	@Override
	public void zeigeAktuellenSpielerAn() {
		this.zeigeAktuellenSpielerAn(spielerListe.get(aktiverSpielerId));
		
	}
	
	@Override
	public void spielzugUeberspringen() {
		spielbrett.spielzugUeberspringen();
		this.spielzustand = Spielzustaende.WUERFLE;
		logger.trace("spieleAlleSpieler: rufe calcNaechsteSpielerId() auf!");
		this.calcNaechsteSpielerId();
		if (spielerListe.get(aktiverSpielerId) instanceof MenschlicherSpielerMADN) {
			this.spieleAlleSpieler();
		}
	}
	
	
	/**
	 * loest die Spielzuege aller nicht Menschlichen Spieler nacheinander aus.
	 * Bricht ab, wenn ein Menschlicher Spieler an der reihe ist, 
	 * damit dieser selber seinen Zug bestimmen kann und nicht übersprungen wird.
	 */
	public void spieleAlleSpieler() {
		logger.trace("spieleAlleSpieler aufgerufen!");
		while(!spielZuEnde) {
			logger.trace("spieleAlleSpieler in Schleife!");
			this.aktiviereSpielzugDesAktivenSpielers();
			if(spielZuEnde) {
				logger.info("Spiel zu Ende gespielt!");
				return;
			}
			
			if (spielerListe.get(aktiverSpielerId) instanceof MenschlicherSpielerMADN) {
				logger.trace("spieleAlleSpieler: Aktueller Spieler ist Menschlich!");
				return;
			}
			logger.trace("spieleAlleSpieler: Aktueller Spieler ist KI!"); 
			
		}
		
	}
	
	

}
