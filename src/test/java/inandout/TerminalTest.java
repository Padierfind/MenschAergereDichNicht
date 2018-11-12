package inandout;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.*;

import de.hdm.se2.madn.exceptions.SicherungsException;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleMADN;
import de.hdm.se2.madn.spielsicherung.SpielsicherungMADN;
import de.inandout.Terminal;
import javafx.scene.paint.Color;



public class TerminalTest {

	
	@Test
	public void readFromFileStringNullAsNullPointerTest(){

		String sicherungsPfad = "Spiele_Backups/test2.txt";
		
		List<String> urspruenglich = null;
		List<String> neu = null;
		
		try {
			urspruenglich = Terminal.readFromFile(sicherungsPfad);
			neu = Terminal.readFromFileStrinNullASNullPointer(sicherungsPfad);
		} catch (IOException e) {
			
			e.printStackTrace();
			Assert.fail("Failed in Try and Catch Block");
			return;
		}
		
		
		if (urspruenglich.size() != neu.size()) {
			Assert.fail("ungleiche Laenge");
		}
		
		for (int i = 0; i < urspruenglich.size(); i++) {
			if (!urspruenglich.get(i).equals(neu.get(i))) {
				Assert.assertTrue("neu String null == Null Pointer!", neu.get(i) == null);
				return;
			}
		}
		
		Assert.fail("Test Ende erreicht");
		
	}
	
	@Test
	public void addToFileTest() {
		System.out.println("addToFileTest gestartet");
		String sicherungsPfad = "Spiele_Backups/test2.txt";
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
		

		System.out.println("spielsicherung erstellt");
		List<String> daten = this.createSaveGameDataVersion1_0(anzahlSpieler, anzahlKISpieler, new ArrayList(), figuren, aktiverSpieler, spielerFarben);
		System.out.println(daten);
		
		try {
			Terminal.addToFile(sicherungsPfad, daten);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			Assert.fail("Error while adding content to file");
		}
		
	}
	
	@Test
	public void addToFileTest(String pfad) {
		System.out.println("addToFileTest gestartet");
		String sicherungsPfad = pfad;
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
		

		System.out.println("spielsicherung erstellt");
		List<String> daten = this.createSaveGameDataVersion1_0(anzahlSpieler, anzahlKISpieler, new ArrayList(), figuren, aktiverSpieler, spielerFarben);
		System.out.println(daten);
		
		try {
			Terminal.addToFile(sicherungsPfad, daten);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			Assert.fail("Error while adding content to file");
		}
		
	}
	
	
	
	private List<String> createSaveGameDataVersion1_0(int anzahlSpieler, int anzahlKISpieler, List<Integer> gewinner,
			List<List<Integer>> figuren, int aktiverSpieler, List<Color> spielerFarben) {
		
		/*
		 * Fehlerbehandlung
		 */
		
		if (gewinner == null) {
			return null;
		}
		
		if (figuren == null) {
			return null;
		}
		
		if (spielerFarben == null) {
			return null;
		}
		
		
		List<String> datasToSave = new ArrayList<String>();
		
		datasToSave.add("madnSave");
		datasToSave.add("V1.0");
		datasToSave.add(null);
		datasToSave.add(Integer.toString(anzahlSpieler));
		datasToSave.add(Integer.toString(anzahlKISpieler));
		datasToSave.add(Integer.toString(aktiverSpieler));
		datasToSave.add(null);

		for (int i = 0; i < spielerFarben.size(); i++) {
			datasToSave.add(spielerFarben.get(i).toString());
		}
		datasToSave.add(null);
		
		
		if (gewinner != null && !gewinner.isEmpty()) {
			for (int i = 0; i < gewinner.size(); i++) {
				datasToSave.add(Integer.toString(gewinner.get(i)));
			}
		} else {
			datasToSave.add("none");
		}
		
		datasToSave.add(null);
		
		for (int i = 0; i < figuren.size(); i++) {
			for (int j = 0; j < figuren.get(i).size(); j++) {
				datasToSave.add(Integer.toString(figuren.get(i).get(j)));
			}
		}
		
		return datasToSave;
		
	}
}
