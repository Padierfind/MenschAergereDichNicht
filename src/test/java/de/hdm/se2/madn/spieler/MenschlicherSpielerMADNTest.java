package de.hdm.se2.madn.spieler;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.hdm.se2.madn.exceptions.SicherungsException;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleInterface;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleMADN;
import de.hdm.se2.madn.spielsicherung.SpielsicherungMADN;
import javafx.scene.paint.Color;

public class MenschlicherSpielerMADNTest
{

	@Test
	public void testSpiele()
	{
		String sicherungsPfad = "Spiele_Backups/test.txt";
		SpielsicherungMADN sicherung = new SpielsicherungMADN(new SpielKontrolleMADN());
		
		System.out.println("variablen zugewiesen und erstellt.");
		try {
			sicherung.ladeSpielstand(sicherungsPfad);
		} catch (SicherungsException | IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			Assert.fail();
		}

	}
}
