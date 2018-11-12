package de.hdm.se2.madn.spielsicherung;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.paint.Color;

import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

import de.hdm.se2.madn.exceptions.SicherungsException;
import de.hdm.se2.madn.spielfigur.SpielfigurInterface;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleMADN;
import de.inandout.Terminal;
import inandout.TerminalTest;

public class SpielsicherungMADNTest {

	@Test
	public void sichereSpielstandTest() {
		System.out.println("sichereSpielstandTest gestartet");
		String sicherungsPfad = "Spiele_Backups/test.txt";
		int anzahlSpieler = 4;
		int anzahlKISpieler = 3;
		int aktiverSpieler = 0;
		List<Color> spielerFarben = new ArrayList();
		spielerFarben.addAll(Arrays.asList(Color.BLACK, Color.WHITE, Color.RED, Color.BLUE));
		

		System.out.println("Variablen erstellt");
		List<List<Integer>> figuren = new ArrayList<>();
		for (int i = 0; i < anzahlSpieler; i++) {
			figuren.add(new ArrayList<>());
			for (int j = 0; j < 4; j++) {
				figuren.get(i).add((i+1) * 10 + (j+1));
			}
		}
		
		

		System.out.println("figuren erstellt");
		
		SpielsicherungMADN sicherung = new SpielsicherungMADN(new SpielKontrolleMADN());
		
		if (Terminal.checkIfFileExists(sicherungsPfad)) {
			Terminal.deleteFile(sicherungsPfad);
		}

		System.out.println("spielsicherung erstellt");
		
		try {
			sicherung.sichereSpielstand(sicherungsPfad, anzahlSpieler, anzahlKISpieler, new ArrayList<>(), figuren, aktiverSpieler, spielerFarben, false);
		} catch (FileAlreadyExistsException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			Assert.fail();
		} catch (SicherungsException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			Assert.fail();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			Assert.fail();
		}
		
		String sicherungsPfad2 = "Spiele_Backups/test2.txt";
		if (Terminal.checkIfFileExists(sicherungsPfad2)) {
			Terminal.deleteFile(sicherungsPfad2);
		}
		
		new TerminalTest().addToFileTest(sicherungsPfad2);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("something went wrong");
			e.printStackTrace();
		}
		
		try {
			Assert.assertEquals("Versuche inhalt zu testen", Terminal.readFromFile(sicherungsPfad), Terminal.readFromFile(sicherungsPfad2));
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Konnte Datei nicht einlesen");
		}
		
	}
	
	/**
	 * Wie bei allen JUnit Tests, die im Verlauf auf das GUI zugreifen, 
	 * scheitert dieser Test, wenn der GUI Zugriff nicht auskommentiert wird!
	 * Liegt an der Erstellungsreihenfolge der Klassen, 
	 * ist aber nicht behebbar, außer durch das auskommentieren!
	 */
	@Test
	public void ladeSpielstand() {
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
