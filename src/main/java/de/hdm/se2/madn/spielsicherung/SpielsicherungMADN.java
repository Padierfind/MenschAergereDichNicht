package de.hdm.se2.madn.spielsicherung;

import javafx.scene.paint.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.*;

import de.hdm.se2.madn.exceptions.FailedToReconstructDataException;
import de.hdm.se2.madn.exceptions.InvalidDataException;
import de.hdm.se2.madn.exceptions.SicherungsException;
import de.hdm.se2.madn.exceptions.WrongFileFormatException;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleInterface;
import de.inandout.Terminal;

/**
 * 
 * @author Philip Betzler
 * @email pb060@hdm-stuttgart.de
 * @version 1.0
 *
 * Eine Implementierung der abstrakten Klasse Spielsicherung im Bezug auf das Spiel Mensch aergere dich nicht.
 * 
 * Aktiviert bei Aufruf der Methode sichereSpielstand() ein neuen Thread.
 *
 */
public class SpielsicherungMADN extends Spielsicherung implements Runnable{
	
	private final static Logger logger = LogManager.getLogger(SpielsicherungMADN.class);
	
	private static final String identifer = "madnSave";
	private static final String saveVersion = "V1.0";
	private static List<String> saveGameData = null;
	private static String sicherungsPfad = "";

	/**
	 * Konstruktor SpielsicherungMADN
	 * Klasse benötigt eine Referenz auf ein Spielkontroll Interface um Informationen auszutauschen. 
	 * @param spielKontrolle
	 * 		der Link auf Instanz
	 */
	public SpielsicherungMADN(SpielKontrolleInterface spielKontrolle) {
		super(spielKontrolle);
		logger.trace("Funktion zurueck in SpielsicherungMADN.");
		
		Lock startLock = new ReentrantLock();
		startLock.lock();
		if (saveGameData == null) {
			saveGameData = Collections.synchronizedList(new LinkedList<String>());
		}
		startLock.unlock();
	}

	@Override
	public void sichereSpielstand(String sicherungsPfad, int anzahlSpieler, int anzahlKISpieler, List<Integer> gewinner,
			List<List<Integer>> figuren, int aktiverSpieler, List<Color> spielerFarben, boolean erzwingeUeberschreiben) 
					throws SicherungsException, FileAlreadyExistsException, IOException {
		
		if(!this.checkIfDataMatchesSaveGameRestrictions(anzahlSpieler, anzahlKISpieler, gewinner, figuren, aktiverSpieler, spielerFarben)) {
			throw new InvalidDataException("SpielsicherungMADN.sichereSpielstand : invalide Daten zum speichern erhalten. Siehe Log File."); 
		}
		
		
		
		try {
			
			if (Terminal.checkIfFileExists(sicherungsPfad)) {
				if(!erzwingeUeberschreiben) {
					logger.warn("SpielsicherungMADN.sichereSpielstand: Datei existiert bereits und soll nicht ueberschrieben werden.");
					throw new FileAlreadyExistsException("File at " +sicherungsPfad + " does already exist and fore overwrite is disabled!");
				} else {
					Terminal.deleteFile(sicherungsPfad);
				}
				
			}
			
			
			
			SpielsicherungMADN.saveGameData.clear();
			SpielsicherungMADN.saveGameData.addAll(this.createSaveGameData(anzahlSpieler, anzahlKISpieler, gewinner, figuren, aktiverSpieler, spielerFarben));
			
			SpielsicherungMADN.setSicherungsPfad(sicherungsPfad);
			
			Thread t = new Thread(this, "Saveing Thread");
			t.start();
			
		} catch (IOException e) {
			logger.error("SpielsicherungMADN.sichereSpielstand IO Exception : " +e.toString() +". Can not save file!");
			throw e;
		}
		
		
		return;
		
	}

	@Override
	public void ladeSpielstand(String sicherungsPfad) throws SicherungsException, IOException{
		
		List<String> saveList = Terminal.readFromFile(sicherungsPfad);
		List<List<String>> splittedSaveList = new ArrayList<List<String>>();
		
		int splittedSaveListIndex = 0;
		boolean lastHasBeenNULL = true;
		for (int i = 0; i < saveList.size(); i++) {
			if (saveList.get(i).equals("null")) {
				if (!lastHasBeenNULL) {
					lastHasBeenNULL = true;
					splittedSaveListIndex++;
				}
			} else {
				lastHasBeenNULL = false;
				
				if (splittedSaveList.size() == splittedSaveListIndex) {
					splittedSaveList.add(new ArrayList<String>());
					splittedSaveList.get(splittedSaveListIndex).add(saveList.get(i));
				} else if (splittedSaveList.size() <= splittedSaveListIndex) {
					logger.fatal("Error in SpielsicherungMADN.ladeSpielstand! Logischer Fehler in Funktion zur Entpackung des Savegames!");
					throw new FailedToReconstructDataException("Error! : unsolveable coding error in SpielsicherungMADN.ladeSpielstand");
				} else if (splittedSaveList.size() > splittedSaveListIndex) {
					splittedSaveList.get(splittedSaveListIndex).add(saveList.get(i));
				}
			}
		}
		logger.trace(splittedSaveList.toString());
		if (splittedSaveList.size() != 5) {
			logger.error("Exception in SpielsicherungMADN.ladeSpielstand. Falsche Anzahl an logischen Gruppen in der Speicherdatei!");
			throw new FailedToReconstructDataException();
		} else if (!splittedSaveList.get(0).get(0).equals(identifer)) {
			logger.error("Exception in SpielsicherungMADN.ladeSpielstand. Falsche Speicherdatei!");
			throw new FailedToReconstructDataException();
		} else if (!splittedSaveList.get(0).get(1).equals(saveVersion)) {
			logger.warn("Exception in SpielsicherungMADN.ladeSpielstand. Falsche Version der Speicherdatei!");
		}
		
		
		
		int anzahlSpieler = 0;
		int anzahlKISpieler = 0;
		int aktiverSpieler = 0;
		
		List<Integer> gewinner = new ArrayList<>();
		List<List<Integer>> figuren = new ArrayList<>();
		List<Color> spielerFarben = new ArrayList<>();
		
		try {
			anzahlSpieler = Integer.parseInt(splittedSaveList.get(1).get(0));
			anzahlKISpieler = Integer.parseInt(splittedSaveList.get(1).get(1));
			
			for (int i = 0; i < splittedSaveList.get(2).size(); i++) {				
				spielerFarben.add(Color.valueOf(splittedSaveList.get(2).get(i)));
			}
			
			if (splittedSaveList.get(3).size() == 1 && splittedSaveList.get(3).get(0).equals("none")) {
				gewinner = null;
			} else {
				for (int i = 0; i < splittedSaveList.get(3).size(); i++) {				
					gewinner.add(Integer.getInteger(splittedSaveList.get(3).get(i)));
				}
			}
			
			
			int counter = 0;
			for (int i = 0; i < splittedSaveList.get(4).size()/4; i++) {				
				figuren.add(new ArrayList<>());
				for (int j = 0; j < 4; j++) {
					figuren.get(i).add(Integer.parseInt(splittedSaveList.get(4).get(counter)));
					counter++;
				}
			} 
		} catch (NullPointerException | NumberFormatException e) {
			throw new FailedToReconstructDataException();
		}
		
		
		
		spielKontrolle.ladeSpielMitParameter(anzahlSpieler, anzahlKISpieler, spielerFarben, gewinner, figuren, aktiverSpieler);
		
	}
	
	
	/**
	 * ruft die neueste Version der Spielstands Daten Sammelfunktion auf.
	 * 
	 * @param anzahlSpieler
	 * Int Gesamtanzahl Spieler
	 * 
	 * @param anzahlKISpieler
	 * Anzahl KI Spieler
	 * 
	 * @param gewinner
	 * List<Integer> bisherige Gewinner
	 * 
	 * @param figuren
	 * List<List<Integer>> Int Positionen der Spielfiguren der einzelnen Spieler
	 * 
	 * @param aktiverSpieler
	 * Int aktiver Spieler
	 * 
	 * @param spielerFarben
	 * List<Color> Liste mit Spielerfarben
	 * 
	 * @return
	 * List<String> der Spielstandsdaten
	 */
	private List<String> createSaveGameData(int anzahlSpieler, int anzahlKISpieler, List<Integer> gewinner,
			List<List<Integer>> figuren, int aktiverSpieler, List<Color> spielerFarben) {
		
		return Collections.synchronizedList(createSaveGameDataVersion1_0(anzahlSpieler, anzahlKISpieler, gewinner, figuren, aktiverSpieler, spielerFarben));
	}
	
	/**
	 * Version 1.0 Der Spielstandsdaten Verarbeitung
	 * 
	 * 
	* @param anzahlSpieler
	 * Int Gesamtanzahl Spieler
	 * 
	 * @param anzahlKISpieler
	 * Anzahl KI Spieler
	 * 
	 * @param gewinner
	 * List<Integer> bisherige Gewinner
	 * 
	 * @param figuren
	 * List<List<Integer>> Int Positionen der Spielfiguren der einzelnen Spieler
	 * 
	 * @param aktiverSpieler
	 * Int aktiver Spieler
	 * 
	 * @param spielerFarben
	 * List<Color> Liste mit Spielerfarben
	 * 
	 * @return
	 * List<String> der Spielstandsdaten
	 */
	private List<String> createSaveGameDataVersion1_0(int anzahlSpieler, int anzahlKISpieler, List<Integer> gewinner,
			List<List<Integer>> figuren, int aktiverSpieler, List<Color> spielerFarben) {
		
		logger.trace("SpielsicherungMADN.createSaveGameDateVersion1_0 : erstelle Datenformat für die Datei.");
		
		/*
		 * Fehlerbehandlung
		 */
		
		
		
		if (figuren == null) {
			logger.error("createSaveGameDataVersion1_0: figuren == null");
			return null;
		}
		
		if (spielerFarben == null) {
			logger.error("createSaveGameDataVersion1_0: spielerfarben == null");
			return null;
		}
		
		
		List<String> datasToSave = new ArrayList<String>();
		
		datasToSave.add(SpielsicherungMADN.identifer);
		datasToSave.add(SpielsicherungMADN.saveVersion);
		datasToSave.add(null);
		datasToSave.add(Integer.toString(anzahlSpieler));
		datasToSave.add(Integer.toString(anzahlKISpieler));
		datasToSave.add(Integer.toString(aktiverSpieler));
		datasToSave.add(null);

		for (int i = 0; i < spielerFarben.size(); i++) {
			datasToSave.add(spielerFarben.get(i).toString());
		}
		datasToSave.add(null);
		
		
		if (gewinner != null && !gewinner.isEmpty()) {
			for (int i = 0; i < gewinner.size(); i++) {
				datasToSave.add(Integer.toString(gewinner.get(i)));
			}
		} else {
			datasToSave.add("none");
		}
		
		datasToSave.add(null);
		
		for (int i = 0; i < figuren.size(); i++) {
			for (int j = 0; j < figuren.get(i).size(); j++) {
				datasToSave.add(Integer.toString(figuren.get(i).get(j)));
			}
		}
		
		logger.trace("SpielsicherungMADN.createSaveGameDateVersion1_0 : List<String> fuer Sicherung erstellt.");
		return datasToSave;
		
	}
	
	/**
	 * Ueberprueft ob die uebergebenen Daten nach einem bisherigen Standart valide sind.
	 * 
	 * @param anzahlSpieler
	 * Int Gesamtanzahl Spieler
	 * 
	 * @param anzahlKISpieler
	 * Anzahl KI Spieler
	 * 
	 * @param gewinner
	 * List<Integer> bisherige Gewinner
	 * 
	 * @param figuren
	 * List<List<Integer>> Int Positionen der Spielfiguren der einzelnen Spieler
	 * 
	 * @param aktiverSpieler
	 * Int aktiver Spieler
	 * 
	 * @param spielerFarben
	 * List<Color> Liste mit Spielerfarben
	 * 
	 * @return
	 * Boolean ob irgendeine Version passt.
	 */
	private boolean checkIfDataMatchesSaveGameRestrictions(int anzahlSpieler, int anzahlKISpieler, List<Integer> gewinner,
			List<List<Integer>> figuren, int aktiverSpieler, List<Color> spielerFarben) {
		
		return this.checkIfDataMatchesSaveGameRestrictionsV1_0(anzahlSpieler, anzahlKISpieler, gewinner, figuren, aktiverSpieler, spielerFarben);
	}
	/**
	 * Ueberprueft ob die uebergebenen Daten zur Version 1.0 gehoeren koennen.
	 * 
	 * @param anzahlSpieler
	 * Int Gesamtanzahl Spieler
	 * 
	 * @param anzahlKISpieler
	 * Anzahl KI Spieler
	 * 
	 * @param gewinner
	 * List<Integer> bisherige Gewinner
	 * 
	 * @param figuren
	 * List<List<Integer>> Int Positionen der Spielfiguren der einzelnen Spieler
	 * 
	 * @param aktiverSpieler
	 * Int aktiver Spieler
	 * 
	 * @param spielerFarben
	 * List<Color> Liste mit Spielerfarben
	 * 
	 * @return
	 * Boolean ob irgendeine Version passt.
	 */
	private boolean checkIfDataMatchesSaveGameRestrictionsV1_0 (int anzahlSpieler, int anzahlKISpieler, List<Integer> gewinner,
			List<List<Integer>> figuren, int aktiverSpieler, List<Color> spielerFarben) {
		
		logger.trace("SpielsicherungMADN.checkIfDataMatchesSaveGameRestrictionsV1_0 wurde gestartet." );
		
		if (anzahlSpieler < 2) {
			logger.error("SpielsicherungMADN.checkIfDataMatchesSaveGameRestrictionsV1_0 : weniger als 2 Spieler gehen nicht!");
			return false;
		} else if (anzahlKISpieler < 0 || anzahlKISpieler > anzahlSpieler) {
			logger.error("SpielsicherungMADN.checkIfDataMatchesSaveGameRestrictionsV1_0 : negative, oder zu grosse Anzahl an KI Spielern!");
			return false;
		} else if (figuren.size() < anzahlSpieler) {
			logger.error("SpielsicherungMADN.checkIfDataMatchesSaveGameRestrictionsV1_0 : nicht alle Spieler uebergeben!");
			return false;
		} else if (aktiverSpieler < 0 || aktiverSpieler >= anzahlSpieler) {
			logger.error("SpielsicherungMADN.checkIfDataMatchesSaveGameRestrictionsV1_0 : aktiverSpieler kann nicht zugeordnet werden!");
			return false;
		} else if (spielerFarben.size() < anzahlSpieler) {			
			logger.error("SpielsicherungMADN.checkIfDataMatchesSaveGameRestrictionsV1_0 : zu wenig Farben fuer die Spieler!");
			return false;
		} else if (spielerFarben.size() > anzahlSpieler) {			
			logger.error("SpielsicherungMADN.checkIfDataMatchesSaveGameRestrictionsV1_0 : zu viele Farben fuer die Spieler!");
			return false;
		} else {			
			for (int i = 0; i < figuren.size(); i++) {
				List tempList = figuren.get(i);
				if (tempList.size()  != 4) {
					logger.error("SpielsicherungMADN.checkIfDataMatchesSaveGameRestrictionsV1_0 : "
							+ "Spieler : " + i + " hat keine 4 Spielfiguren!");
					return false;
				}
				for (int j = 0; j < tempList.size(); j++) {
					if (tempList.get(j) == null) {
						logger.error("SpielsicherungMADN.checkIfDataMatchesSaveGameRestrictionsV1_0 :"
								+ " Figur :" + j +" von Spieler : " +i +" steht auf dem Feld NULL!");
						return false;
					}
				}
			}
		}
		
		logger.trace("SpielsicherungMADN.checkIfDataMatchesSaveGameRestrictionsV1_0 wird erfolgreich beendet.");
		return true;
	}

	@Override
	public void run() {
		try {
			Terminal.addToFile(SpielsicherungMADN.getSicherungsPfad(), SpielsicherungMADN.saveGameData);
		} catch (IOException e) {
			logger.error("Error, cant save to File!");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Synchronisierte Methode um den Sicherungspfad für die Speicherdatei festzulegen.
	 * @param sicherungsPfad
	 * String des Sicherungspfads
	 */
	private synchronized static void setSicherungsPfad (String sicherungsPfad) {
		SpielsicherungMADN.sicherungsPfad = sicherungsPfad;
	}
	
	/**
	 * Methode um den Sicherungspfad für die Speicherdatei festzulegen.
	 * Nicht synchonized da das reine lesen thread save ist und ein lock in einer statischen Methode oder Funktion die Klasse lahm legt.
	 * return
	 * String des Sicherungspfads
	 */
	private static String getSicherungsPfad () {
		return SpielsicherungMADN.sicherungsPfad;
	}
	
	
}
