package de.hdm.se2.madn.spielbrett;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.hdm.se2.madn.Feld;
import de.hdm.se2.madn.exceptions.SpielfeldMitEigenemSpielerBesetztException;
import de.hdm.se2.madn.exceptions.UngueltigerLaufWegException;
import de.hdm.se2.madn.spieler.KISpielerMADN;
import de.hdm.se2.madn.spieler.SpielerInterface;
import de.hdm.se2.madn.spielfigur.SpielfigurInterface;
import de.hdm.se2.madn.spielfigur.SpielfigurMADN;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleInterface;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleMADN;
import javafx.scene.paint.Color;

import org.junit.Assert;
import org.junit.Assert.*;
public class SpielbrettMADNTest {

	// bevor die Tests richtig durchlaufen koennen, muss in der Klasse SpielbrettMADN.java im package de.hdm.se2.madn.spielbrett
	// in der letzten Methode zeichneIstZustand() die Zeile:
	// 551	->	spielKontrolle.zeichne(felderFarben);
	// auskommentiert werden, da sonst auf auf GUI Methoden zugegriffen wird, die aber für die Tests nicht initialisiert wurde.
	
	// Fuer diese Tests, muss zusaetzlich in Zeile:
	// 281	->	|| startFeld.getSpielfigur().getGehoertzuSpieler() != spielKontrolle.getAktiverSpieler()
	// auskommentiert werden und in Zeile:
	// 363	->  || startFeld.getSpielfigur().getGehoertzuSpieler() != spielKontrolle.getAktiverSpieler()
	// auskommentiert werden, da hier der aktiver Spieler abgefragt wird, der aber im test nicht eigenstaendig fest gelegt werden kann.
	// Diese Aenderungen betreffen die Methode spielzugAusfuehren() und spielzugMoeglichkeitAnzeigen()
	
	/**
	 * Die Methode testet schlageSpielfigur() und die damit verbundene Methode getFreieStartposition, durch die eine Figur nach dem Schlagen
	 * wieder auf die Startposition zurueck gesetzt wird.
	 * 
	 * @throws UngueltigerLaufWegException
	 * @throws SpielfeldMitEigenemSpielerBesetztException
	 */
	@Test
	public void testSchlageSpielfigurUndGetFreieStartposition() throws UngueltigerLaufWegException, SpielfeldMitEigenemSpielerBesetztException {
		SpielKontrolleInterface spielk = new SpielKontrolleMADN();
		
		SpielerInterface spieler1 = new KISpielerMADN(Color.ALICEBLUE, spielk);
		SpielerInterface spieler2 = new KISpielerMADN(Color.ANTIQUEWHITE, spielk);
		SpielerInterface spieler3 = new KISpielerMADN(Color.AZURE, spielk);
		SpielerInterface spieler4 = new KISpielerMADN(Color.BISQUE, spielk);
		
		SpielfigurInterface spielf1 = new SpielfigurMADN(spieler1);
		SpielfigurInterface spielf2 = new SpielfigurMADN(spieler1);
		SpielfigurInterface spielf3 = new SpielfigurMADN(spieler1);
		SpielfigurInterface spielf4 = new SpielfigurMADN(spieler1);
		spieler1.addSpielfigur(spielf1);
		spieler1.addSpielfigur(spielf2);
		spieler1.addSpielfigur(spielf3);
		spieler1.addSpielfigur(spielf4);
		
		SpielfigurInterface spielf5 = new SpielfigurMADN(spieler2);
		SpielfigurInterface spielf6 = new SpielfigurMADN(spieler2);
		SpielfigurInterface spielf7 = new SpielfigurMADN(spieler2);
		SpielfigurInterface spielf8 = new SpielfigurMADN(spieler2);
		spieler2.addSpielfigur(spielf5);
		spieler2.addSpielfigur(spielf6);
		spieler2.addSpielfigur(spielf7);
		spieler2.addSpielfigur(spielf8);
		
		SpielfigurInterface spielf9 = new SpielfigurMADN(spieler3);
		SpielfigurInterface spielf10 = new SpielfigurMADN(spieler3);
		SpielfigurInterface spielf11= new SpielfigurMADN(spieler3);
		SpielfigurInterface spielf12= new SpielfigurMADN(spieler3);
		spieler3.addSpielfigur(spielf9);
		spieler3.addSpielfigur(spielf10);
		spieler3.addSpielfigur(spielf11);
		spieler3.addSpielfigur(spielf12);
	
		SpielfigurInterface spielf13 = new SpielfigurMADN(spieler4);
		SpielfigurInterface spielf14 = new SpielfigurMADN(spieler4);
		SpielfigurInterface spielf15 = new SpielfigurMADN(spieler4);
		SpielfigurInterface spielf16 = new SpielfigurMADN(spieler4);
		spieler4.addSpielfigur(spielf13);
		spieler4.addSpielfigur(spielf14);
		spieler4.addSpielfigur(spielf15);
		spieler4.addSpielfigur(spielf16);

		List<SpielerInterface> spieler = new ArrayList<SpielerInterface>();
		spieler.add(spieler1);
		spieler.add(spieler2);
		spieler.add(spieler3);
		spieler.add(spieler4);
		
		SpielbrettInterface spielbrett = new SpielbrettMADN(null, spieler, spielk, Color.BEIGE);
		
		Feld feld = spielbrett.getFeldMitId(21);
		Feld feld1 = spielbrett.getFeldMitId(11);
		Feld feld2 = spielbrett.getFeldMitId(202);
		
		SpielfigurInterface figurAktuell = feld1.getSpielfigur();
		
		spielbrett.spielzugAusfuehren(11, 6);	//Figur von Spieler 1 kommt aus Startposition
		spielbrett.spielzugAusfuehren(21, 6);	//Figur von Spieler 2 kommt aus Startposition
		spielbrett.spielzugAusfuehren(102, 5);	//Spieler 1 laeuft
		spielbrett.spielzugAusfuehren(107, 5);	//Spieler 1 kommt zu Startfeld von Spieler 2 und kann Spielfigur dort schlagen
		
		//figur die geschlagen wird, wird auf Startposition zurueck gesetzt, das Feld darf also nicht mehr frei sein
		Assert.assertFalse(feld.istFrei());
		
		// auf dem Startfeld der geschlagenen Figur steht jetzt die Figur der schlagenden Figur
		Assert.assertEquals(figurAktuell, feld2.getSpielfigur());
		

	}

	/**
	 * Hier wird getestet ob die Methode spielzugMoeglichkeitAnzeigen() richtig erkennt, das das Feld, auf dem
	 * die Figur mit dem gewuerfelten Wert landen wuerde besetzt ist und deshalb die Exception 
	 * SpielfeldMitEigenemSpielerBesetztException geworfen wird.
	 * 
	 * @throws UngueltigerLaufWegException
	 * @throws SpielfeldMitEigenemSpielerBesetztException
	 */
	
	@Test (expected = SpielfeldMitEigenemSpielerBesetztException.class)
	public void testSpielzugMoeglichkeitAnzeigen() throws UngueltigerLaufWegException, SpielfeldMitEigenemSpielerBesetztException {
		SpielKontrolleInterface spielk = new SpielKontrolleMADN();
		
		SpielerInterface spieler1 = new KISpielerMADN(Color.ALICEBLUE, spielk);
		SpielerInterface spieler2 = new KISpielerMADN(Color.ANTIQUEWHITE, spielk);
		SpielerInterface spieler3 = new KISpielerMADN(Color.AZURE, spielk);
		SpielerInterface spieler4 = new KISpielerMADN(Color.BISQUE, spielk);
		
		SpielfigurInterface spielf1 = new SpielfigurMADN(spieler1);
		SpielfigurInterface spielf2 = new SpielfigurMADN(spieler1);
		SpielfigurInterface spielf3 = new SpielfigurMADN(spieler1);
		SpielfigurInterface spielf4 = new SpielfigurMADN(spieler1);
		spieler1.addSpielfigur(spielf1);
		spieler1.addSpielfigur(spielf2);
		spieler1.addSpielfigur(spielf3);
		spieler1.addSpielfigur(spielf4);
		
		SpielfigurInterface spielf5 = new SpielfigurMADN(spieler2);
		SpielfigurInterface spielf6 = new SpielfigurMADN(spieler2);
		SpielfigurInterface spielf7 = new SpielfigurMADN(spieler2);
		SpielfigurInterface spielf8 = new SpielfigurMADN(spieler2);
		spieler2.addSpielfigur(spielf5);
		spieler2.addSpielfigur(spielf6);
		spieler2.addSpielfigur(spielf7);
		spieler2.addSpielfigur(spielf8);
		
		SpielfigurInterface spielf9 = new SpielfigurMADN(spieler3);
		SpielfigurInterface spielf10 = new SpielfigurMADN(spieler3);
		SpielfigurInterface spielf11= new SpielfigurMADN(spieler3);
		SpielfigurInterface spielf12= new SpielfigurMADN(spieler3);
		spieler3.addSpielfigur(spielf9);
		spieler3.addSpielfigur(spielf10);
		spieler3.addSpielfigur(spielf11);
		spieler3.addSpielfigur(spielf12);
	
		SpielfigurInterface spielf13 = new SpielfigurMADN(spieler4);
		SpielfigurInterface spielf14 = new SpielfigurMADN(spieler4);
		SpielfigurInterface spielf15 = new SpielfigurMADN(spieler4);
		SpielfigurInterface spielf16 = new SpielfigurMADN(spieler4);
		spieler4.addSpielfigur(spielf13);
		spieler4.addSpielfigur(spielf14);
		spieler4.addSpielfigur(spielf15);
		spieler4.addSpielfigur(spielf16);

		List<SpielerInterface> spieler = new ArrayList<SpielerInterface>();
		spieler.add(spieler1);
		spieler.add(spieler2);
		spieler.add(spieler3);
		spieler.add(spieler4);
		
		SpielbrettInterface spielbrett = new SpielbrettMADN(null, spieler, spielk, Color.BEIGE);

		spielbrett.spielzugAusfuehren(11, 6);	
		spielbrett.spielzugAusfuehren(102, 5);
		spielbrett.spielzugAusfuehren(12, 6);
		
		spielbrett.spielzugMoeglichkeitAnzeigen(102, 5);
		
	}

	/**
	 * Hier wird spielzugLaufen() und spielzugAusfuehren() getestet, mit der zusaetzlichen Funktion, dass wenn 6 der gewuerfelte Wert ist
	 * aber keine Figur von der Startposition ausgewaehlt wurde, eine der Figuren die sich auf den Startpositionen befinden raus gesetzt werden
	 * 
	 * @throws UngueltigerLaufWegException
	 * @throws SpielfeldMitEigenemSpielerBesetztException
	 */
	@Test
	public void testSpielzugLaufenUndSpielzugAusfuehren() throws UngueltigerLaufWegException, SpielfeldMitEigenemSpielerBesetztException {
		
		SpielKontrolleInterface spielk = new SpielKontrolleMADN();
		
		SpielerInterface spieler1 = new KISpielerMADN(Color.ALICEBLUE, spielk);
		SpielerInterface spieler2 = new KISpielerMADN(Color.ANTIQUEWHITE, spielk);
		SpielerInterface spieler3 = new KISpielerMADN(Color.AZURE, spielk);
		SpielerInterface spieler4 = new KISpielerMADN(Color.BISQUE, spielk);
		
		SpielfigurInterface spielf1 = new SpielfigurMADN(spieler1);
		SpielfigurInterface spielf2 = new SpielfigurMADN(spieler1);
		SpielfigurInterface spielf3 = new SpielfigurMADN(spieler1);
		SpielfigurInterface spielf4 = new SpielfigurMADN(spieler1);
		spieler1.addSpielfigur(spielf1);
		spieler1.addSpielfigur(spielf2);
		spieler1.addSpielfigur(spielf3);
		spieler1.addSpielfigur(spielf4);
		
		SpielfigurInterface spielf5 = new SpielfigurMADN(spieler2);
		SpielfigurInterface spielf6 = new SpielfigurMADN(spieler2);
		SpielfigurInterface spielf7 = new SpielfigurMADN(spieler2);
		SpielfigurInterface spielf8 = new SpielfigurMADN(spieler2);
		spieler2.addSpielfigur(spielf5);
		spieler2.addSpielfigur(spielf6);
		spieler2.addSpielfigur(spielf7);
		spieler2.addSpielfigur(spielf8);
		
		SpielfigurInterface spielf9 = new SpielfigurMADN(spieler3);
		SpielfigurInterface spielf10 = new SpielfigurMADN(spieler3);
		SpielfigurInterface spielf11= new SpielfigurMADN(spieler3);
		SpielfigurInterface spielf12= new SpielfigurMADN(spieler3);
		spieler3.addSpielfigur(spielf9);
		spieler3.addSpielfigur(spielf10);
		spieler3.addSpielfigur(spielf11);
		spieler3.addSpielfigur(spielf12);
	
		SpielfigurInterface spielf13 = new SpielfigurMADN(spieler4);
		SpielfigurInterface spielf14 = new SpielfigurMADN(spieler4);
		SpielfigurInterface spielf15 = new SpielfigurMADN(spieler4);
		SpielfigurInterface spielf16 = new SpielfigurMADN(spieler4);
		spieler4.addSpielfigur(spielf13);
		spieler4.addSpielfigur(spielf14);
		spieler4.addSpielfigur(spielf15);
		spieler4.addSpielfigur(spielf16);

		List<SpielerInterface> spieler = new ArrayList<SpielerInterface>();
		spieler.add(spieler1);
		spieler.add(spieler2);
		spieler.add(spieler3);
		spieler.add(spieler4);
		
		SpielbrettInterface spielbrett = new SpielbrettMADN(null, spieler, spielk, Color.BEIGE);

		// Figur auf Startposition und 6 gewürfelt, Startfeld muss als ergebnis raus kommen
		int feldid = spielbrett.spielzugLaufen(11, 6).getId();
		Assert.assertEquals(102, feldid);
		
		//Spielfigur wird gesetzt und getestet ob Feld dann besetzt ist
		spielbrett.spielzugAusfuehren(11, 6);		
		Assert.assertFalse(spielbrett.getFeldMitId(102).istFrei());
		
		
		int feldid1 = spielbrett.spielzugLaufen(102, 5).getId();
		Assert.assertEquals(107, feldid1);
		
		// Spielfigur laeuft weiter Feld von vorher muss jetzt frei sein
		spielbrett.spielzugAusfuehren(102, 5);
		Assert.assertTrue(spielbrett.getFeldMitId(102).istFrei());
		
		// spielzug laufen gibt Feld 203 als neues Feld zurück, 
		// aber die Methode spielzugAusfuehren() prueft ob es noch eine besetzte Startposition gibt,
		// weil 6 gewuerfelt wurde, wenn ja, wird einfach die Figur aus der Startposition auf das 
		// Startfeld gesetzt deshalb ist das Startfeld nicht mehr frei
		int feldid2 = spielbrett.spielzugLaufen(107, 6).getId();
		Assert.assertEquals(203, feldid2); 
		
		spielbrett.spielzugAusfuehren(107, 6);
		Assert.assertFalse(spielbrett.getFeldMitId(102).istFrei());
		
	}
	
	/**
	 * Hier wird spielzugLaufen() und spielzugAusfuehren() getestet, mit verschiedenen Bedingungen wenn 6 gewuerfelt wird.
	 * @throws UngueltigerLaufWegException
	 * @throws SpielfeldMitEigenemSpielerBesetztException
	 */
	@Test
	public void test2SpielzugLaufenUndSpielzugAusfuehren() throws UngueltigerLaufWegException, SpielfeldMitEigenemSpielerBesetztException {
		
		SpielKontrolleInterface spielk = new SpielKontrolleMADN();
		
		SpielerInterface spieler1 = new KISpielerMADN(Color.ALICEBLUE, spielk);
		SpielerInterface spieler2 = new KISpielerMADN(Color.ANTIQUEWHITE, spielk);
		SpielerInterface spieler3 = new KISpielerMADN(Color.AZURE, spielk);
		SpielerInterface spieler4 = new KISpielerMADN(Color.BISQUE, spielk);
		
		SpielfigurInterface spielf1 = new SpielfigurMADN(spieler1);
		SpielfigurInterface spielf2 = new SpielfigurMADN(spieler1);
		SpielfigurInterface spielf3 = new SpielfigurMADN(spieler1);
		SpielfigurInterface spielf4 = new SpielfigurMADN(spieler1);
		spieler1.addSpielfigur(spielf1);
		spieler1.addSpielfigur(spielf2);
		spieler1.addSpielfigur(spielf3);
		spieler1.addSpielfigur(spielf4);
		
		SpielfigurInterface spielf5 = new SpielfigurMADN(spieler2);
		SpielfigurInterface spielf6 = new SpielfigurMADN(spieler2);
		SpielfigurInterface spielf7 = new SpielfigurMADN(spieler2);
		SpielfigurInterface spielf8 = new SpielfigurMADN(spieler2);
		spieler2.addSpielfigur(spielf5);
		spieler2.addSpielfigur(spielf6);
		spieler2.addSpielfigur(spielf7);
		spieler2.addSpielfigur(spielf8);
		
		SpielfigurInterface spielf9 = new SpielfigurMADN(spieler3);
		SpielfigurInterface spielf10 = new SpielfigurMADN(spieler3);
		SpielfigurInterface spielf11= new SpielfigurMADN(spieler3);
		SpielfigurInterface spielf12= new SpielfigurMADN(spieler3);
		spieler3.addSpielfigur(spielf9);
		spieler3.addSpielfigur(spielf10);
		spieler3.addSpielfigur(spielf11);
		spieler3.addSpielfigur(spielf12);
	
		SpielfigurInterface spielf13 = new SpielfigurMADN(spieler4);
		SpielfigurInterface spielf14 = new SpielfigurMADN(spieler4);
		SpielfigurInterface spielf15 = new SpielfigurMADN(spieler4);
		SpielfigurInterface spielf16 = new SpielfigurMADN(spieler4);
		spieler4.addSpielfigur(spielf13);
		spieler4.addSpielfigur(spielf14);
		spieler4.addSpielfigur(spielf15);
		spieler4.addSpielfigur(spielf16);

		List<SpielerInterface> spieler = new ArrayList<SpielerInterface>();
		spieler.add(spieler1);
		spieler.add(spieler2);
		spieler.add(spieler3);
		spieler.add(spieler4);
		
		SpielbrettInterface spielbrett = new SpielbrettMADN(null, spieler, spielk, Color.BEIGE);

		spielbrett.spielzugAusfuehren(11, 6);
		spielbrett.spielzugAusfuehren(102, 1);
		
		// schaut ob Figuren auserhalb der Startpositionen mit 6 laufen duerfen, wenn das Startfeld besetzt ist
		spielbrett.spielzugAusfuehren(12, 6);
		spielbrett.spielzugAusfuehren(103, 6);
		spielbrett.spielzugAusfuehren(102, 1);
		
		spielbrett.spielzugAusfuehren(13, 6);
		spielbrett.spielzugAusfuehren(102, 2);
		
		spielbrett.spielzugAusfuehren(14, 6);
		spielbrett.spielzugAusfuehren(102, 3);
		
		// schaut ob man mit 6 laufen darf, wenn alle Startpositionen frei sind
		spielbrett.spielzugAusfuehren(104, 6);
		
		// alle Felder die jetzt besetzt sein müssten werden darauf getestet
		Assert.assertFalse(spielbrett.getFeldMitId(110).istFrei());
		Assert.assertFalse(spielbrett.getFeldMitId(109).istFrei());
		Assert.assertFalse(spielbrett.getFeldMitId(105).istFrei());
		Assert.assertFalse(spielbrett.getFeldMitId(103).istFrei());
	
		
	}


}
