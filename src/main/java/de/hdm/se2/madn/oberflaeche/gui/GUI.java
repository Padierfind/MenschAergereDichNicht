package de.hdm.se2.madn.oberflaeche.gui;

import javafx.scene.paint.Color;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.events.EventListener;

import com.jfoenix.controls.JFXDecorator;

import de.hdm.se2.madn.factories.SpielerFactory;
import de.hdm.se2.madn.spieler.SpielerInterface;
import de.hdm.se2.madn.spielkontrolle.SpielKontrolleMADN;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

/**
 * @author Patrick Tobler
 * @email pt017@hdm-stuttgart.de
 * @version 1.0
 *
 */

public class GUI extends Application
{
	private final static Logger logger = LogManager.getLogger(GUI.class);

	public Stage primeStage;
	private static GUIKontrolle guiKontrolle;
		       	
	public FlowPane menuPane = null;
	public FlowPane setupPane = null;
	public FlowPane gameInterfacePane = null;
	public FlowPane gameOverPane = null;
	public FlowPane rulePane = null;

	private Color red = new Color(0.0, 0.75, 0.49, 0.0);
	private Color blue = new Color(0.7, 0.8, 0.0, 0.0);
	private Color yellow = new Color(0.0, 0.8, 0.77, 0.0);
	private Color green = new Color(0.57,0.0,0.54,0.31);
	
	public GUIMenuController menuController;
	public GUINavigationDrawerController menuPaneController;
	public GUISetupController setupController;
	public GUIGameInterfaceController interfaceController;
	public GUIGameOverScreenController gameOverController;
	
	public boolean secondStepOpened = false;

	/**
	 * Wird als erste Methode aufgerufen. Erstellt darauf hin die GUI Klasse, in welcher das SetupGUI erzeugt wird.
	 */
	public static void main(String[] args) throws Exception
	{
		logger.trace("main() aufgerufen.");

		guiKontrolle = new GUIKontrolle(new SpielKontrolleMADN());
        Application.launch(GUI.class, args);
	}
	
	/**
	 * Ruft die Methode setGUIInstance() in der GUIKontrolle auf. Übergibt ihr diese Klasse.
	 * Lädt alle FXML und speichert sie in den jeweiligen Variablen. 
	 * Erstellt den JFX Decorator, lädt das Stylesheet und lädt den JFX Decorator als Hauptszene.
	 * Ruft jeweils die setGUI() Methode in den Controller-Klassen auf. Übergibt ihr diese Klasse.
	 */
	@Override
	public void start(Stage primaryStage) 
	{
		logger.trace("start() aufgerufen.");

		primeStage = primaryStage;
		guiKontrolle.setGUIInstance(this);

		Parent root = null;
		
		try
		{
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Menu.fxml"));
			root = loader.load();
			menuController = loader.getController();
			
			FXMLLoader navDrawerLoader = new FXMLLoader(getClass().getResource("/NavigationDrawer.fxml"));
			menuPane = navDrawerLoader.load();
			menuPaneController = navDrawerLoader.getController();
			
			FXMLLoader setupLoader = new FXMLLoader(this.getClass().getResource("/Setup.fxml"));
			setupPane = setupLoader.load();
			setupController = setupLoader.getController();
			
			FXMLLoader ruleLoader = new FXMLLoader(this.getClass().getResource("/Rules.fxml"));
			rulePane = ruleLoader.load();

			FXMLLoader interfaceLoader = new FXMLLoader(this.getClass().getResource("/GameInterface.fxml"));
			gameInterfacePane = interfaceLoader.load();
			interfaceController = interfaceLoader.getController();
			
			FXMLLoader gameOverLoader = new FXMLLoader(this.getClass().getResource("/GameOverScreen.fxml"));
			gameOverPane = gameOverLoader.load();
			gameOverController = gameOverLoader.getController();
		}
		catch(Exception e)
		{
			logger.error("Laden der FXML Dateien fehlgeschlagen", e.getMessage());
			e.printStackTrace();
		}
		try{

			JFXDecorator decorator = new JFXDecorator(primeStage, root);
			decorator.setCustomMaximize(true);
			decorator.getStyleClass().add("window");

			Scene scene = new Scene(decorator, 770, 680);
		    scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Lato:400,700"); // Lädt die Schriftart, um sie im CSS zuweisen zu können
		    scene.getStylesheets().add("/MenschAergerDichNichtStyle.css");
		    primeStage.setMinWidth(770);
		    primeStage.setMinHeight(680);
		    
		    primeStage.setTitle("Mensch Ärgere Dich Nicht");
		    primeStage.setScene(scene);
		    primeStage.show();
		}
		catch(Exception e)
		{

			logger.error("Anlegen der Stage fehlgeschlagen.", e.getMessage());
			e.printStackTrace();
		}
	    
		menuController.setGUI(this);
		menuPaneController.setGUI(this);
		setupController.setGUI(this);
		interfaceController.setGUI(this);
		gameOverController.setGUI(this);
	}

	/**
	 * Ruft die start()-Methode in der GUIKontrolle auf. Übergibt ihr die Gesamtanzahl der Spieler und die Anzahl der Bots.
	 * Aktiviert den saveButton.
	 *
	 * @param playerCount Anzahl der Spieler
	 * @param botCount Anzahl der Bots
	 */
	public void starteSpiel(int playerCount, int botCount)
	{
		logger.trace("starteSpiel() aufgerufen.");

		int anzahlSpieler = playerCount + botCount;
		guiKontrolle.start(anzahlSpieler, botCount, red, blue, yellow, green);
		
		menuPaneController.activateSaveButton();
	}
		
	/**
	 * Ruft die Methode wuerfeln() in der GUIKontrolle auf. Bekommt von ihr einen Integer.
	 * Leitet den Integer an die setGewuerfelterWert()-Methode im Interfacecontroller weiter.
	 * Ruft die Methode toggleButtonSpielzugBestätigen() im Interfacecontroller auf.
	 */
	public void wuerfeln()
	{
		logger.trace("wuerfeln() aufgerufen.");

		int gewuerfelterWert = guiKontrolle.wuerfeln();
		interfaceController.setGewuerfelterWert(gewuerfelterWert);
		interfaceController.toggleButtonSpielzugBestaetigen();
	}
	
	/**
	 * Ruft die Methode speichern() in der GUIKontrolle auf. Übergibt der Methode den Pfad, auf dem gespeichert werden soll und
	 * einen Boolean, der bestimmt überschrieben werden soll.
	 * 
	 * @param pfad Der zu speichernde Pfad.
	 * @param overwrite Bestimmt ob der Pfad überschrieben werden soll.
	 */
	public void speichereSpiel(String pfad, boolean overwrite)
	{
		logger.trace("speichereSpiel() aufgerufen.");

    	guiKontrolle.speichern(pfad, overwrite);
	}
	
	/**
	 * Ruft die Methode laden() in der GUIKontrolle auf. Übergibt ihr den Pfad, der geladen werden soll.
	 * @param pfad Der zu ladene Pfad.
	 */
	public void ladeSpiel(String pfad)
	{
		logger.trace("ladeSpiel() aufgerufen.");

    	guiKontrolle.laden(pfad);
	}

	/**
	 * Ruft die Methode spielzugUeberspringen() in der GUIKontrolle auf.
	 */
	public void spielzugUeberspringen()
	{
		logger.trace("spielzugÜberspringen() aufgerufen.");

		guiKontrolle.spielzugUeberspringen();
	}
	
	/**
	 * Ruft die Methode spielzugAusfuehren() in der GUIKontrolle auf.
	 */
	public void spielzugAusfuehren()
	{
		logger.trace("spielzugAusfuehren() aufgerufen.");
		
		guiKontrolle.spielzugAusfuehren();
	}
	
	/**
	 * Beendet das Spiel, indem es System.exit(0) aufruft.
	 */
	public void beendeSpiel()
	{
		logger.trace("beendeSpiel() aufgerufen.");

	    System.exit(0);
	}

	/**
	 * Ruft die Methode changeWuerfelnButtonStatus() im Interfacecontroller auf. Übergibt ihr den Status "false".
	 * Ruft die Methode feldAusgewaehlt() in der GUIKontrolle auf. Übergibt ihr die feldID.
	 * 
	 * @param feldID Die ID des soeben ausgewählten Feldes als Integer.
	 */
	public void spielFeldPositionAusgewaehlt(int feldID)
	{
		logger.trace("spielFeldPositionAusgewaehlt() aufgerufen mit feldID: " + feldID);

		interfaceController.changeWuerfelnButtonStatus(false);
		guiKontrolle.feldAusgewaehlt(feldID);
	}
	
	/**
	 * Speichert die Farbe von spieler. Wandelt diese Farbe in einen String mit der Hexfarbe um, indem die Methode hex() aufgerufen wird.
	 * Ruft die Methode zeigeAktuellerSpieler() im Interfacecontroller auf und übergibt ihr die Hexfarbe.
	 * 
	 * @param spieler Der aktuelle Spieler.
	 */
	
	public void zeigeAktuellerSpieler(SpielerInterface spieler)
	{
		logger.trace("zeigeAktuellerSpieler() aufgerufen mit Spielerinteface: " + spieler);

		Color entryColor = spieler.getFarbe();
		String hexColor = hex(entryColor);
		
		interfaceController.zeigeAktuellerSpieler(hexColor);
	}
	
	/**
	 * Ruft die Methode maleGameGUI() im Menucontroller auf. Übergibt ihr die Hashmap felderFarben.
	 * 
	 * @param felderFarben Die Farben in denen die Felder dargestellt werden sollen. Keys der Hashmap sind die FelderIDs, Values die Farben.
	 */
	public void maleGameGUI(HashMap felderFarben)
	{
		logger.trace("maleGameGUI() aufgerufen.");

		menuController.maleGameGUI(felderFarben);
	}
	
	/**
	 * Speichert die Farbe von spieler. Wandelt diese Farbe in einen String mit der Hexfarbe um, indem die Methode hex() aufgerufen wird.
	 * Ruft die Methode setWinner() im Interfacecontroller auf und übergibt ihr die Hexfarbe.
	 * 
	 * @param winner Das SpielerInterface des Siegers.
	 */
	public void displayVictoryScreen(SpielerInterface winner)
	{
		logger.trace("displayVictoryScreen() aufgerufen.");
		
		Color entryColor = winner.getFarbe();
		String hexColor = hex(entryColor);
		
		gameOverController.setWinner(hexColor);
	}
	
	/**	 
	 * Wandelt eine JavaFX Farbe in einen Hexcode um.
	 * 
	 * @param entryColor Die JavaFX Ursprungsfarbe.
	 * @return Den Hexcode als String.
	 */
	public String hex(Color entryColor)
	{
		String hexColor = String.format( "#%02X%02X%02X", (int)( entryColor.getRed() * 255 ), (int)( entryColor.getGreen() * 255 ), (int)( entryColor.getBlue() * 255 ) );
		return hexColor;
	}
	
	/**
	 * Ruft die Methode toggleButtonWuerfeln() im InterfaceController auf.
	 * Ruft die Methode setGewuerfelterWertInvisible() im InterfaceController auf.
	 */
	public void resetGameInterface()
	{
		interfaceController.toggleButtonWuerfeln();
		interfaceController.setGewuerfelterWertInvisible();
	}
}