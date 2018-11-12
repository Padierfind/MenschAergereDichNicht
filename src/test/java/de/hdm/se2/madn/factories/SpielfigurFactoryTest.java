package de.hdm.se2.madn.factories;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import de.hdm.se2.madn.exceptions.InvalidDataException;
import de.hdm.se2.madn.exceptions.UnbekannteSpielfigur;
import de.hdm.se2.madn.exceptions.UnbekannteSpielfigurArtException;
import de.hdm.se2.madn.factories.SpielfigurFactory;
import de.hdm.se2.madn.spieler.KISpielerMADN;
import de.hdm.se2.madn.spieler.SpielerInterface;
import de.hdm.se2.madn.spielfigur.SpielfigurInterface;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleInterface;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleMADN;

import javafx.scene.paint.Color;

public class SpielfigurFactoryTest
{

	/**
	 * Die Methode testGetSpielfigur() prüft, ob bei der Uebergabe der richtigen
	 * Parameter an die Methode getSpielfigur() das erwartete Ergebnis eintritt
	 * bzw. eine Spielfigur erstellt wird.
	 * 
	 * @throws InvalidDataException
	 */
	@Test
	public void testGetSpielfigur() throws InvalidDataException
	{
		SpielKontrolleInterface spielKontrolle1 = new SpielKontrolleMADN();
		SpielerInterface gehoertZuSpieler1 = new KISpielerMADN(Color.ALICEBLUE, spielKontrolle1);

		try
		{
			SpielfigurInterface spielfigur = SpielfigurFactory.getSpielfigur("MADN", gehoertZuSpieler1,
					Color.ALICEBLUE);
		} catch (UnbekannteSpielfigur e)
		{
			Assert.fail();
			e.printStackTrace();
		}
	}

	/**
	 * Die Methode testGetSpielfigur1() testet, ob bei Uebergabe eines falschen
	 * Werts fuer typ an die Methode getSpielfigur() die entsprechende Exception
	 * geworfen wird.
	 * 
	 * @throws UnbekannteSpielfigur
	 * @throws InvalidDataException
	 */
	@Test(expected = UnbekannteSpielfigurArtException.class)
	public void testGetSpielfigur1() throws UnbekannteSpielfigur, InvalidDataException
	{
		SpielKontrolleInterface spielKontrolle1 = new SpielKontrolleMADN();
		SpielerInterface gehoertZuSpieler1 = new KISpielerMADN(Color.ALICEBLUE, spielKontrolle1);

		SpielfigurInterface spielfigur = SpielfigurFactory.getSpielfigur("Test", gehoertZuSpieler1, Color.ALICEBLUE);
	}

	/**
	 * Die Methode testGetSpielfigur2() testet, ob bei Uebergabe eines falschen
	 * Werts fuer den Parameter farbe an die Methode getSpielfigur() die
	 * entsprechende Exception geworfen wird.
	 * 
	 * @throws UnbekannteSpielfigur
	 * @throws InvalidDataException
	 */
	@Test(expected = InvalidDataException.class)
	public void testGetSpielfigur2() throws UnbekannteSpielfigur, InvalidDataException
	{
		SpielKontrolleInterface spielKontrolle1 = new SpielKontrolleMADN();
		SpielerInterface gehoertZuSpieler1 = new KISpielerMADN(Color.ALICEBLUE, spielKontrolle1);

		SpielfigurInterface spielfigur = SpielfigurFactory.getSpielfigur("Test", null, Color.ALICEBLUE);
	}

	/**
	 * Die Methode testGetSpielfigur3() prueft, ob bei Uebergabe eines falschen
	 * Werts fuer den Parameter gehoertZuSpieler1 an die Methode getSpielfigur()
	 * die entsprechende Exception geworfen wird.
	 * 
	 * @throws UnbekannteSpielfigur
	 * @throws InvalidDataException
	 */
	@Test(expected = InvalidDataException.class)
	public void testGetSpielfigur3() throws UnbekannteSpielfigur, InvalidDataException
	{
		SpielKontrolleInterface spielKontrolle1 = new SpielKontrolleMADN();
		SpielerInterface gehoertZuSpieler1 = new KISpielerMADN(Color.ALICEBLUE, spielKontrolle1);

		SpielfigurInterface spielfigur = SpielfigurFactory.getSpielfigur("Test", gehoertZuSpieler1, null);
	}

	/**
	 * Die Methode testGetSpielfigur4() prueft, ob bei Uebergabe falscher Werte
	 * fuer die Parameter farbe und gehoertZuSpieler1 an die Methode
	 * getSpielfigur() die entsprechende Exception geworfen wird.
	 * 
	 * @throws UnbekannteSpielfigur
	 * @throws InvalidDataException
	 */
	@Test(expected = InvalidDataException.class)
	public void testGetSpielfigur4() throws UnbekannteSpielfigur, InvalidDataException
	{
		SpielKontrolleInterface spielKontrolle1 = new SpielKontrolleMADN();
		SpielerInterface gehoertZuSpieler1 = new KISpielerMADN(Color.ALICEBLUE, spielKontrolle1);

		SpielfigurInterface spielfigur = SpielfigurFactory.getSpielfigur("Test", null, null);
	}

}
