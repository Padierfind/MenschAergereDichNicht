package de.hdm.se2.madn.spieler;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm.se2.madn.Feld;
import de.hdm.se2.madn.Feld.feldTyp;
import de.hdm.se2.madn.exceptions.SpielfeldMitEigenemSpielerBesetztException;
import de.hdm.se2.madn.exceptions.UngueltigerLaufWegException;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleInterface;
import javafx.scene.paint.Color;

/**
 * @author Janina Muenzenmayer
 * @email jm111@hdm-stuttgart.de
 * @version 1.0
 * 
 */

public class KISpielerMADN extends Spieler
{

	private final static Logger logger = LogManager.getLogger(KISpielerMADN.class);

	/**
	 * Konstruktor laedt die farbe und spielKontrolle aus der direkten
	 * Superklasse, der abstrakten Klasse Spieler.
	 * 
	 * @param farbe
	 *            Parameter enthaelt die Farbe des Spielers.
	 * @param spielKontrolle
	 *            Spielt als KI.
	 */
	public KISpielerMADN(Color farbe, SpielKontrolleInterface spielKontrolle)
	{
		super(farbe, spielKontrolle);
		logger.trace("Der Konstruktor der Klasse KISpielerMADN wird aufgerufen.");
	}

	
	/**
	 * Methode zeigt den aktuellen Spieler an. Die Methode wuerfle() wird von
	 * SpielKontrolleInterface aufgerufen - der KISpieler wuerfelt. Die Liste
	 * eigeneSpielfelder enthaelt die Positionen der eigenen Spielfiguren.
	 * 
	 * Wuerfelt der KISpieler eine 6 und eine Spielfigur steht noch auf einer
	 * Startposition, wird diese Figur bevorzugt bewegt. Ist das nicht der Fall,
	 * prueft eine for-Schleife, ob sich eine Figur im Spielbereich/auf
	 * Startfeld befindet. Wenn ja, wird der Wert des aktuellen Zielfelds durch
	 * den Aufruf von spielzugLaufen erfasst.
	 * Ist das Zielfeld des Zuges von einer fremden Spielfigur besetzt, wird
	 * diese geschlagen.
	 * Ist das Zielfeld des Zuges von einer eigenen Spielfigur besetzt, wird
	 * dieser Zug nicht ausgeführt.
	 * Ist kein Zielfeld von einer anderen Spielfigur besetzt, zieht die erste
	 * moegliche Spielfigur vor(istNotfallAusweg).
	 * Ist kein Zug moeglich wird der Spielzug uebersprungen.
	 * 
	 */
	@Override
	public void spiele()
	{
		logger.trace("Die Methode spiele() des KI-Spielers wird aufgerufen.");

		spielKontrolle.zeigeAktuellenSpielerAn();

		int gewuerfelterWert = spielKontrolle.wuerfle();
		List<Feld> eigeneSpielfelder = spielKontrolle.getSpielfelderListe(this);

		if (gewuerfelterWert != 0)
		{
			if (gewuerfelterWert == 6)
			{
				logger.trace("KI: Der gewuerfelte Wert ist 6.");
				boolean eigeneFigurBesetztStartfeld = false;
				Feld zielFeldFuerEigeneFigur = null;				
				
				for (Feld aktuellesFeld : eigeneSpielfelder) 
				{
					if (aktuellesFeld.getFeldTyp().equals(Feld.feldTyp.STARTFELD) && aktuellesFeld.getSpieler().equals(this))
					{
						eigeneFigurBesetztStartfeld = true;
						zielFeldFuerEigeneFigur = aktuellesFeld;
					}
				}
				
				for (Feld aktuellesFeld : eigeneSpielfelder)
				{				
					if (aktuellesFeld.getFeldTyp().equals(Feld.feldTyp.STARTPOSITION))	
					{
						if ( !eigeneFigurBesetztStartfeld ) {
							logger.trace("KI: Die aktuelle Spielfigur befindet sich auf einer Startposition.");
							
							try
							{
								spielKontrolle.spielzugAusfuehren(aktuellesFeld.getId());
								logger.trace("KI: Spielzug ausfuehren wurde augewaehlt.");
								return;
							} catch (SpielfeldMitEigenemSpielerBesetztException e)
							{
								logger.fatal("KI: Fataler Fehler. Catch-Block darf logisch nicht ausgefuehrt werden.", aktuellesFeld);
								e.printStackTrace();
							}
						} else 
						{
							try
							{
								logger.trace("KI: Startfeld ist von eigenem Spieler besetzt.");
								spielKontrolle.spielzugAusfuehren(zielFeldFuerEigeneFigur.getId());
								return;
							}catch (SpielfeldMitEigenemSpielerBesetztException es)
							{
								logger.trace("KI: Spieler wuerde eigene Spielfigur schlagen. Spielzug wird uebersprungen.");
								spielKontrolle.spielzugUeberspringen();
								return;
							}
						}
					}
				}
			}
		
			logger.trace("KI: Der gewuerfelte Wert ist keine 6 oder es steht keine Spielfigur auf den Startpositionen.");

			Feld auszufuehrendesFeld = null;
			boolean istNotfallAusweg = true;

			for (Feld aktuellesFeld : eigeneSpielfelder)	// durchläuft nacheinander die Position jeder Spielfigur
			{
				Feld zielFeld = null;

				if (  aktuellesFeld.getFeldTyp().equals(Feld.feldTyp.NORMAL)  ||  aktuellesFeld.getFeldTyp().equals(Feld.feldTyp.STARTFELD)  )
				{
					logger.trace( "KI: Die aktuelle Spielfigur befindet sich im normalen Spielbereich/auf der Startposition." );
				
					try
					{
						zielFeld = spielKontrolle.spielzugLaufen(aktuellesFeld.getId(), gewuerfelterWert);

						if ( zielFeld.istFrei() == false )
						{
							logger.trace("KI: Aktuellen Spielfigur koennte schlagen.");

							try
							{
								spielKontrolle.spielzugAusfuehren(aktuellesFeld.getId());
								return;
							} catch (SpielfeldMitEigenemSpielerBesetztException es)
							{
								logger.trace("KI: Spieler wuerde eigene Spielfigur schlagen.");
							}

						} else	// zielFeld.istFrei() == true
						{ 
							if (istNotfallAusweg == true)
							{
								auszufuehrendesFeld = aktuellesFeld;
								istNotfallAusweg = false;
							}
						}

					} catch (UngueltigerLaufWegException e)
					{
						logger.error("Error, UngueltigerLaufwegException in Klasse KISpielerMADN ausgeloest. Unbekanntes Problem.", aktuellesFeld, zielFeld );
						e.printStackTrace();
					}
				}
			}
				
			if ( auszufuehrendesFeld != null )
			{
				try
				{
					spielKontrolle.spielzugAusfuehren(auszufuehrendesFeld.getId());
				} catch ( SpielfeldMitEigenemSpielerBesetztException e )
				{
					logger.fatal( "KI: Fataler Fehler. Catch-Block darf logisch nicht ausgefuehrt werden.", auszufuehrendesFeld );
					e.printStackTrace();
				}
				return;
			}
		}
		logger.info("KI: Keinen moeglichen Spielzug gefunden. Spielzug wird uebersprungen.");
		spielKontrolle.spielzugUeberspringen();
	}

	
	/**
	 * Methode gibt einen String mit dem Typ des Spielers zurueck: 
	 * Hier spielt eine KI den Spieltyp Mensch Aergere Dich Nicht.
	 */
	@Override
	public String toString()
	{
		return "Spielertyp: KI Spieler MADN.";
	}
}
