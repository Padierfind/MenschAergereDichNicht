package de.hdm.se2.madn.factories;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import de.hdm.se2.madn.exceptions.InvalidDataException;
import de.hdm.se2.madn.exceptions.UnbekannteSpielerArtException;
import de.hdm.se2.madn.exceptions.UnbekannterSpieler;
import de.hdm.se2.madn.exceptions.UnbekannterSpielerTypException;
import de.hdm.se2.madn.factories.SpielerFactory;
import de.hdm.se2.madn.spieler.SpielerInterface;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleInterface;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleMADN;
import javafx.scene.paint.Color;

public class SpielerFactoryTest
{

	/**
	 * Die Methode testGetSpieler() prueft, ob bei der Uebergabe der richtigen
	 * Parameter an die Methode getSpieler() das erwartete Ergebnis eintritt
	 * bzw. ein Spieler erstellt wird.
	 * 
	 * @throws UnbekannterSpieler
	 * @throws InvalidDataException
	 */
	@Test
	public void testGetSpieler() throws UnbekannterSpieler, InvalidDataException
	{
		SpielKontrolleInterface spielKontrolle1 = new SpielKontrolleMADN();

		try
		{
			SpielerInterface spieler = SpielerFactory.getSpieler("MADN", "KI", Color.ALICEBLUE, spielKontrolle1);
		} catch (UnbekannterSpieler e)
		{
			Assert.fail();
			e.printStackTrace();
		}
	}

	/**
	 * Die Methode testGetSpieler1() testet, ob bei Uebergabe eines falschen
	 * Werts des Parameters spielerArt an die Methode getSpieler() die
	 * entsprechende Exception geworfen wird.
	 * 
	 * @throws UnbekannterSpieler
	 * @throws InvalidDataException
	 */
	@Test(expected = UnbekannteSpielerArtException.class)
	public void testGetSpieler1() throws UnbekannterSpieler, InvalidDataException
	{
		SpielKontrolleInterface spielKontrolle1 = new SpielKontrolleMADN();
		SpielerInterface spieler = SpielerFactory.getSpieler("Test", "KI", Color.ALICEBLUE, spielKontrolle1);
	}

	/**
	 * Die Methode testGetSpieler2() testet, ob bei Uebergabe eines falschen
	 * Werts fuer spieler an die Methode getSpieler() die entsprechende
	 * Exception geworfen wird.
	 * 
	 * @throws UnbekannterSpieler
	 * @throws InvalidDataException
	 */
	@Test(expected = UnbekannterSpielerTypException.class)
	public void testGetSpieler2() throws UnbekannterSpieler, InvalidDataException
	{
		SpielKontrolleInterface spielKontrolle1 = new SpielKontrolleMADN();
		SpielerInterface spieler = SpielerFactory.getSpieler("MADN", "Test", Color.ALICEBLUE, spielKontrolle1);
	}

	/**
	 * Die Methode testGetSpieler3() testet, ob bei Uebergabe eines falschen
	 * farbe-Werts an die Methode getSpieler() die entsprechende Exception
	 * geworfen wird.
	 * 
	 * @throws UnbekannterSpieler
	 * @throws InvalidDataException
	 */
	@Test(expected = InvalidDataException.class)
	public void testGetSpieler3() throws UnbekannterSpieler, InvalidDataException
	{
		SpielKontrolleInterface spielKontrolle1 = new SpielKontrolleMADN();
		SpielerInterface spieler = SpielerFactory.getSpieler("MADN", "KI", null, spielKontrolle1);
	}

	/**
	 * Die Methode testGetSpieler4() testet, ob bei Uebergabe des falschen Werts
	 * fuer spielKontrolle an die Methode getSpieler() die entsprechende
	 * Exception geworfen wird.
	 * 
	 * @throws UnbekannterSpieler
	 * @throws InvalidDataException
	 */
	@Test(expected = InvalidDataException.class)
	public void testGetSpieler4() throws UnbekannterSpieler, InvalidDataException
	{
		SpielKontrolleInterface spielKontrolle1 = new SpielKontrolleMADN();
		SpielerInterface spieler = SpielerFactory.getSpieler("MADN", "KI", Color.ALICEBLUE, null);
	}

	/**
	 * Die Methode testGetSpieler5() testet, ob bei Uebergabe der falschen Werte
	 * fuer farbe und spielKontrolle an die Methode getSpieler() die
	 * entsprechende Exception geworfen wird.
	 * 
	 * @throws UnbekannterSpieler
	 * @throws InvalidDataException
	 */
	@Test(expected = InvalidDataException.class)
	public void testGetSpieler5() throws UnbekannterSpieler, InvalidDataException
	{
		SpielKontrolleInterface spielKontrolle1 = new SpielKontrolleMADN();
		SpielerInterface spieler = SpielerFactory.getSpieler("MADN", "KI", null, null);
	}
}
