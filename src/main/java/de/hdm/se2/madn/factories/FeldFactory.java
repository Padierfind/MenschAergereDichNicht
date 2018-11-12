package de.hdm.se2.madn.factories;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm.se2.madn.Feld;
import de.hdm.se2.madn.exceptions.UngueltigeFeldWertException;
import de.hdm.se2.madn.spielbrett.SpielbrettInterface;

public class FeldFactory {

	private final static Logger logger = LogManager.getLogger(FeldFactory.class);

	private FeldFactory() {
	}

	/**
	 * Die Methode erstellt ein neues Feld.
	 * 
	 * @param id
	 *            die Feld ID.
	 * @param spielbrett
	 *            Der SpielbrettTyp.
	 * @return Feld das neu erstellte Feld das zurück gegeben wird.
	 */

	public static Feld getFeld(Integer id, SpielbrettInterface spielbrett) throws UngueltigeFeldWertException {

		logger.trace("Die Methode getFeld() der FeldFactory wird aufgerufen");
		Feld tempfeld = null;
		if (id > 0 && spielbrett != null) {
			tempfeld = new Feld(id, spielbrett);
		} else if (id <= 0 && spielbrett == null) {
			throw new UngueltigeFeldWertException("id und Spielbrett sind gleich null, oder id ist kleiner als null!");
		} else if (id <= 0) {
			throw new UngueltigeFeldWertException("id ist kleiner oder gleich null!");
		} else if (spielbrett == null) {
			throw new UngueltigeFeldWertException("Wert für Spielbrett ist gleich null!");
		}

		logger.debug("erstellt ein neues Feld mit den Eigenschaften: FeldID: " + id + ", SpielbrettTyp: "
				+ spielbrett.toString());

		return tempfeld;

	}

}
