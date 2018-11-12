package de.hdm.se2.madn.spieler;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import de.hdm.se2.madn.exceptions.UngueltigerIndexWertException;
import de.hdm.se2.madn.spielfigur.SpielfigurInterface;
import de.hdm.se2.madn.spielfigur.SpielfigurMADN;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleInterface;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleMADN;
import javafx.scene.paint.Color;

public class SpielerTest
{

	/**
	 * (Exception-Test) 
	 * Der Test prueft, ob bei dem Aufruf der Methode
	 * istAktiverSpieler() eine RuntimeException geworfen wird. Die Methode
	 * sollte nicht aufgerufen werden.
	 * 
	 * @throws RuntimeException
	 */
	@Test(expected = RuntimeException.class)
	public void testIstAktiverSpieler()
	{
		SpielKontrolleInterface spielKontrolle1 = new SpielKontrolleMADN();
		SpielerInterface spieler = new KISpielerMADN(Color.ALICEBLUE, spielKontrolle1);

		spieler.istAktiverSpieler();
	}

	/**
	 * (Positiv-Test) 
	 * testAddSpielfigur() testet, ob bei Uebergabe der richtigen
	 * Parameter die Spielfigur wird zur figurenListe hizugefuegt.
	 */
	@Test
	public void testAddSpielfigur()
	{
		SpielKontrolleInterface spielKontrolle1 = new SpielKontrolleMADN();
		SpielerInterface spieler1 = new KISpielerMADN(Color.ALICEBLUE, spielKontrolle1);
		SpielfigurInterface spielfigur1 = new SpielfigurMADN(spieler1);

		try
		{
			spieler1.addSpielfigur(spielfigur1);
		} catch (Exception e)
		{
			Assert.fail();
			e.printStackTrace();
		}
	}

	/**
	 * (Exception-Test) 
	 * Der Test prueft, ob bei der Uebergabe der falschen
	 * Parameter an die Methode addSpielfigur() eine NullPointerException
	 * geworfen wird.
	 * 
	 * @throws NullPointerException
	 */
	@Test(expected = NullPointerException.class)
	public void testAddSpielfigur1()
	{
		SpielKontrolleInterface spielKontrolle1 = new SpielKontrolleMADN();
		SpielerInterface spieler1 = new KISpielerMADN(Color.ALICEBLUE, spielKontrolle1);
		SpielfigurInterface spielfigur1 = new SpielfigurMADN(spieler1);
		spielfigur1 = null;

		spieler1.addSpielfigur(spielfigur1);
	}

	/**
	 * (Positiv-Test) 
	 * Die Methode testDelSpielfigur() prueft, ob bei Uebergabe der
	 * richtigen Parameter die Spielfigur aus der figurenListe entfernt wird.
	 */
	@Test
	public void testDelSpielfigur()
	{
		SpielKontrolleInterface spielKontrolle1 = new SpielKontrolleMADN();
		SpielerInterface spieler1 = new KISpielerMADN(Color.ALICEBLUE, spielKontrolle1);
		SpielfigurInterface spielfigur1 = new SpielfigurMADN(spieler1);

		try
		{
			spieler1.delSpielfigur(spielfigur1);
		} catch (Exception e)
		{
			Assert.fail();
			e.printStackTrace();
		}
	}

	/**
	 * (Exception-Test) 
	 * Die Test-Methode testDelSpielfigur1() testet, ob bei
	 * Uebergabe falscher Parameter an die Methode delSpielfigur() eine
	 * NullPointerException geworfen wird.
	 * 
	 * @throws NullPointerException
	 */
	@Test(expected = NullPointerException.class)
	public void testDelSpielfigur1()
	{
		SpielKontrolleInterface spielKontrolle1 = new SpielKontrolleMADN();
		SpielerInterface spieler1 = new KISpielerMADN(Color.ALICEBLUE, spielKontrolle1);
		SpielfigurInterface spielfigur1 = new SpielfigurMADN(spieler1);
		spielfigur1 = null;

		spieler1.delSpielfigur(spielfigur1);
	}

	/**
	 * (Positiv-Test) 
	 * Die Methode testGetSpielfigur() prueft, ob die Methode
	 * getSpielfigur(), bei einer korrekten figurenListe und der Uebergabe des
	 * richtigen Index, die gesuchte Spielfigur aus der Liste ausgegeben werden
	 * kann.
	 * 
	 * @throws UngueltigerIndexWertException
	 */
	@Test
	public void testGetSpielfigur() throws UngueltigerIndexWertException
	{

		SpielKontrolleInterface spielKontrolle1 = new SpielKontrolleMADN();
		SpielerInterface spieler1 = new KISpielerMADN(Color.ALICEBLUE, spielKontrolle1);
		SpielfigurInterface spielfigur1 = new SpielfigurMADN(spieler1);
		SpielfigurInterface spielfigur2 = new SpielfigurMADN(spieler1);
		SpielfigurInterface spielfigur3 = new SpielfigurMADN(spieler1);
		SpielfigurInterface spielfigur4 = new SpielfigurMADN(spieler1);

		spieler1.addSpielfigur(spielfigur1);
		spieler1.addSpielfigur(spielfigur2);
		spieler1.addSpielfigur(spielfigur3);
		spieler1.addSpielfigur(spielfigur4);

		try
		{
			spieler1.getSpielfigur(1);
		} catch (UngueltigerIndexWertException e)
		{
			Assert.fail();
			e.printStackTrace();
		}
	}

	/**
	 * (Exception-Test) 
	 * Die Methode testGetSpielfigur1() testet, die Methode
	 * getSpielfigur() bei einer korrekten figurenListe und aber einem falschen
	 * Index eine UngueltigerIndexWertException wirft.
	 * 
	 * @throws UngueltigerIndexWertException
	 */
	@Test(expected = UngueltigerIndexWertException.class)
	public void testGetSpielfigur1() throws UngueltigerIndexWertException
	{

		SpielKontrolleInterface spielKontrolle1 = new SpielKontrolleMADN();
		SpielerInterface spieler1 = new KISpielerMADN(Color.ALICEBLUE, spielKontrolle1);
		SpielfigurInterface spielfigur1 = new SpielfigurMADN(spieler1);
		SpielfigurInterface spielfigur2 = new SpielfigurMADN(spieler1);
		SpielfigurInterface spielfigur3 = new SpielfigurMADN(spieler1);
		SpielfigurInterface spielfigur4 = new SpielfigurMADN(spieler1);

		spieler1.addSpielfigur(spielfigur1);
		spieler1.addSpielfigur(spielfigur2);
		spieler1.addSpielfigur(spielfigur3);
		spieler1.addSpielfigur(spielfigur4);

		spieler1.getSpielfigur(4);
	}

}
