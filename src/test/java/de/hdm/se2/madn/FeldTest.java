package de.hdm.se2.madn;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import de.hdm.se2.madn.Feld.feldTyp;
import de.hdm.se2.madn.spielbrett.SpielbrettInterface;
import de.hdm.se2.madn.spielbrett.SpielbrettMADN;
import de.hdm.se2.madn.spieler.KISpielerMADN;
import de.hdm.se2.madn.spieler.MenschlicherSpielerMADN;
import de.hdm.se2.madn.spieler.SpielerInterface;
import de.hdm.se2.madn.spielfigur.SpielfigurInterface;
import de.hdm.se2.madn.spielfigur.SpielfigurMADN;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolle;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleInterface;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleMADN;

public class FeldTest {
	
	// bevor die Tests richtig durchlaufen koennen, muss in der Klasse SpielbrettMADN.java im package de.hdm.se2.madn.spielbrett
	// in der letzten Methode zeichneIstZustand() die Zeile:
	// 551	spielKontrolle.zeichne(felderFarben);
	// auskommentiert werden, da sonst auf auf GUI Methoden zugegriffen wird, die aber für die Tests nicht initialisiert wurde.

	/**
	 * Die Methode testet die Methoden getFeldTyp und SetFeldTyp, indem sie 3 neue Felder 
	 * erstellt und diese miteinander und mit bestimmten Werten vergleicht.
	 */
	
	@Test
	public void testSetUndGetFeldTyp() {
		Feld feld = new Feld(55, null);
		feld.setFeldTyp(feldTyp.NORMAL);
		
		Feld feld2 = new Feld(66, null);
		feld2.setFeldTyp(feldTyp.STARTPOSITION);
		
		Feld feld3 = new Feld(77, null);
		
		Assert.assertTrue(feld.getFeldTyp().equals(feldTyp.NORMAL));
		Assert.assertFalse(feld.getFeldTyp().equals(feldTyp.ENDPOSITION));
		Assert.assertFalse(feld.getFeldTyp().equals(feld2.getFeldTyp()));
		//testet default Wert
		Assert.assertEquals(feldTyp.NORMAL, feld3.getFeldTyp());
	}

	/**
	 * Die Methode testet die Methode getFarbe und setFarbe, in dem sie 3 neue Felder
	 * erstellt, ihnen Farben zuordnet und mit bestimmten Werten vergleicht.
	 */
	@Test
	public void testSetUndGetFarbe() {
		Feld feld = new Feld(55, null);
		feld.setFarbe(Color.ANTIQUEWHITE);
		
		Feld feld2 = new Feld(66, null);
		feld2.setFarbe(Color.ALICEBLUE);
		
		Feld feld3 = new Feld(104, null);
		
		Assert.assertTrue(feld.getFarbe().equals(Color.ANTIQUEWHITE));
		Assert.assertFalse(feld.getFarbe().equals(Color.AZURE));
		Assert.assertFalse(feld.getFarbe().equals(feld2.getFarbe()));
		//testet default Wert
		Assert.assertEquals(Color.rgb(237, 237, 237), feld3.getFarbe());
	}

	/**
	 * Die Methode tested die istFrei() in dem sie 2 Felder erstellt, auf eins eine Spielfigur
	 * setzt und dann testet ob sie frei oder besetzt sind.
	 */
	@Test
	public void testIstFrei() {
		SpielerInterface spieler = new KISpielerMADN(Color.ALICEBLUE, null);
		SpielfigurInterface spielfigur = new SpielfigurMADN(spieler);
		
		Feld feld = new Feld(55, null);
		feld.setSpielfigur(spielfigur);
		Feld feld2 = new Feld(66, null);
		
		Assert.assertTrue(feld2.istFrei());
		Assert.assertFalse(feld.istFrei());
	}

	@Test
	public void testSetNaechstesFeldUndGetNaechsteFelder() {
				
		Feld feld1 = new Feld(11, null);
		Feld feld2 = new Feld(12, null);
		Feld feld3 = new Feld(13, null);
		Feld feld4 = new Feld(14, null);
		Feld feld5 = new Feld(15, null);
		Feld feld6 = new Feld(16, null);
		Feld feld7 = new Feld(17, null);
		
		feld1.setNaechstesFeld(feld2);
		feld1.setNaechstesFeld(feld3);
		feld1.setNaechstesFeld(feld4);
		feld1.setNaechstesFeld(feld5);
		feld1.setNaechstesFeld(feld6);
		feld1.setNaechstesFeld(feld7);
		
		Assert.assertTrue(feld1.getNaechsteFelder().size() == 6);
		
		List<Feld> test = new ArrayList<Feld>();
		test.add(feld2);
		test.add(feld3);
		test.add(feld4);
		test.add(feld5);
		test.add(feld6);
		test.add(feld7);
		
		Assert.assertEquals(test, feld1.getNaechsteFelder());

	}

	/**
	 * Die Methode testet getId.
	 */
	@Test
	public void testGetId() {
		Feld feld1 = new Feld(11, null);
		Feld feld2 = new Feld(12, null);
		
		Assert.assertEquals(11, feld1.getId());
		Assert.assertEquals(12, feld2.getId());
		Assert.assertFalse(feld1.getId() == feld2.getId());
	}

	/**
	 * Die Methode testet getSpielfigur() und setSpielfigur().
	 */
	@Test
	public void testGetUndSetSpielfigur() {
		SpielKontrolleInterface spielk = new SpielKontrolleMADN();
		
		SpielerInterface spieler1 = new KISpielerMADN(Color.ALICEBLUE, spielk);
		SpielerInterface spieler2 = new KISpielerMADN(Color.ANTIQUEWHITE, spielk);
		SpielerInterface spieler3 = new KISpielerMADN(Color.AZURE, spielk);
		SpielerInterface spieler4 = new MenschlicherSpielerMADN(Color.BISQUE, spielk);
		
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
		
		Feld feld1 = spielbrett.getFeldMitId(109);
		Feld feld2 = spielbrett.getFeldMitId(108);
		
		
		//wollte testen ob figur vorige überschreibt, aber problem ist, das Schlage SPielfigur aufgerufen wird, 
		// was aber nicht möglich ist, da spielf1 nie von Feld entfernt wird ... Lösung ?
		
	//	feld1.setSpielfigur(spielf1);
	//	feld1.setSpielfigur(spielf8);
		
	//	Assert.assertEquals(spielf8, feld1.getSpielfigur());
		
		feld2.setSpielfigur(spielf8);
		feld1.setSpielfigur(spielf1);
		
		Assert.assertEquals(spielf1, feld1.getSpielfigur());
		Assert.assertEquals(spielf8, feld2.getSpielfigur());
		Assert.assertFalse(feld2.getSpielfigur() == spielf1);
	}

}
