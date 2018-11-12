package de.hdm.se2.madn;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm.se2.madn.factories.SpielerFactory;
import de.hdm.se2.madn.spielbrett.SpielbrettInterface;
import de.hdm.se2.madn.spieler.SpielerInterface;
import de.hdm.se2.madn.spielfigur.SpielfigurInterface;
import javafx.scene.paint.Color;

/**
 * 
 * @author Lena Haide
 * @email lh075@hdm-stuttgart.de
 * @version 1.0
 *
 */

public class Feld {

	private final static Logger logger = LogManager.getLogger(Feld.class);

	private int id;
	private SpielfigurInterface figur;
	private SpielbrettInterface spielbrett;

	public enum feldTyp {
		STARTPOSITION, STARTFELD, ENDPOSITION, NORMAL
	}

	private feldTyp typ;
	protected Color farbe;
	protected List<Feld> naechstes;
	private SpielerInterface spieler;

	/**
	 * Konstruktor gibt die Werte vor, die angegeben werden müssen.
	 * 
	 * @param id
	 *            Gibt die Feld Id an.
	 * @param spielbrett
	 *            gibt das Spielbrett an.
	 */

	public Feld(Integer id, SpielbrettInterface spielbrett) {

		logger.trace("Der Konstruktor der Feld Klasse wird aufgerufen");
		this.id = id;
		this.spielbrett = spielbrett;
		this.typ = Feld.feldTyp.NORMAL; // Default
		this.farbe = Color.rgb(237, 237, 237); // Default
		this.naechstes = new ArrayList<Feld>();
	}

	public void setSpieler(SpielerInterface spieler) {
		this.spieler = spieler;
	}

	public SpielerInterface getSpieler() {
		return spieler;
	}

	/**
	 * Die Funktion setzt den Typ des entsprechenden Feldes.
	 * 
	 * @param typ
	 *            typ enthaelt den Feld typ.
	 */
	public void setFeldTyp(Feld.feldTyp typ) {
		this.typ = typ;
	}

	/**
	 * Die Methode gibt den Typ eines bestimmten Feldes zurueck.
	 * 
	 * @return feldTyp der Feld Typ wird zurueckgegeben.
	 */
	public Feld.feldTyp getFeldTyp() {
		return typ;
	}

	/**
	 * Die Funktion setzt die Farbe des entsprechenden Feldes.
	 * 
	 * @param farbe
	 *            Die Farbe, die das Feld haben soll.
	 */
	public void setFarbe(Color farbe) {
		this.farbe = farbe;
	}

	/**
	 * Die Methode gibt die Farbe des entsprechenden Feldes zurueck.
	 * 
	 * @return Color Die Farbe des Feldes.
	 */
	public Color getFarbe() {
		return farbe;
	}

	/**
	 * Die Methode testet, ob ein Feld frei ist.
	 * 
	 * @return boolean true wenn feld frei ist.
	 */
	public boolean istFrei() {
		return (figur == null);
	}

	/**
	 * Die Funktion fügt der verketteten Liste ein Feld hinzu.
	 * 
	 * @param naechstesFeld
	 *            Feld das in verkettung aufgenommen werden soll.
	 */
	public void setNaechstesFeld(Feld naechstesFeld) { // Feld kann mehrere
														// naechste Felder haben
		naechstes.add(naechstesFeld);
	}

	/**
	 * Die Methode gibt die verkettete Liste naechstes zurück.
	 * 
	 * @return List<Feld> Die Liste naechstes der Felder.
	 */
	public List<Feld> getNaechsteFelder() {
		return naechstes;
	}

	/**
	 * Die Funktion gibt die Feld Id zurueck.
	 * 
	 * @return Feld ID Die Id des entsprechenden Feldes.
	 */

	public int getId() {
		return id;
	}

	/**
	 * Die Funktion gibt die Spielfigur zurueck.
	 * 
	 * @return SpielfigurInterface die Figur, die auf dem Feld steht.
	 */

	public SpielfigurInterface getSpielfigur() {
		return figur;
	}

	/**
	 * Durch die Methode kann man die Spielfigur setzen.
	 * 
	 * @param figur
	 *            Die Figur die gesetzt werden soll.
	 */

	public void setSpielfigur(SpielfigurInterface figur) {

		if (figur == null) {
			this.figur = null;
		} else {
			this.figur = figur;
		}

	}

}
