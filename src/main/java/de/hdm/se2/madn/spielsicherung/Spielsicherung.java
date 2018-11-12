package de.hdm.se2.madn.spielsicherung;

import javafx.scene.paint.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

import org.apache.logging.log4j.*;

import de.hdm.se2.madn.exceptions.SicherungsException;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleInterface;

public abstract class Spielsicherung implements SpielsicherungInterface{
	
	private final static Logger logger = LogManager.getLogger(Spielsicherung.class);

	protected SpielKontrolleInterface spielKontrolle;
	
	protected Spielsicherung(SpielKontrolleInterface spielKontrolle) {
		logger.trace("Konstruktor mit Parameter spielKontrolleInterface: " + spielKontrolle.toString() + " aufgerufen. ");
		this.spielKontrolle = spielKontrolle;
		logger.trace("Instanz SpielkontrolleInterface in Spielsicherung Konstruktor hinzugefuegt.");
	}
	
	public abstract void sichereSpielstand(String sicherungsPfad, int anzahlSpieler, int anzahlKISpieler, List<Integer> gewinner, 
			List<List<Integer>> figuren, int aktiverSpieler, List<Color> spielerFarben, boolean erzwingeUeberschreiben) 
					throws SicherungsException, FileAlreadyExistsException, IOException;
	
	public abstract void ladeSpielstand(String sicherungsPfad) throws SicherungsException, FileNotFoundException, IOException;
}
