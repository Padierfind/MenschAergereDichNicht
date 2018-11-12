package de.hdm.se2.madn.spielbrett;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.hdm.se2.madn.Feld;
import de.hdm.se2.madn.spieler.KISpielerMADN;
import de.hdm.se2.madn.spieler.SpielerInterface;
import de.hdm.se2.madn.spielfigur.SpielfigurInterface;
import de.hdm.se2.madn.spielfigur.SpielfigurMADN;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleInterface;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleMADN;
import javafx.scene.paint.Color;

public class SpielbrettTest {

	// bevor die Tests richtig durchlaufen koennen, muss in der Klasse SpielbrettMADN.java im package de.hdm.se2.madn.spielbrett
	// in der letzten Methode zeichneIstZustand() die Zeile:
	// 551	spielKontrolle.zeichne(felderFarben);
	// auskommentiert werden, da sonst auf auf GUI Methoden zugegriffen wird, die aber für die Tests nicht initialisiert wurde.
	
	@Test
	public void testGetSpielfelderListe() {
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
	
		List<Feld> spielfiguraufFeld = spielbrett.getSpielfelderListe(spieler1);
		int s = spielfiguraufFeld.size();
		
		Assert.assertEquals(4, s);
	}

	@Test
	public void testGetFeldMitId() {
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

		Feld f = spielbrett.getFeldMitId(12);
		Assert.assertTrue(f.getId() == 12);
		
		Feld f1 = spielbrett.getFeldMitId(4);
		Assert.assertTrue(f1 == null);
	}

}
