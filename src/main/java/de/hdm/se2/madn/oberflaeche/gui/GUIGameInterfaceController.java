package de.hdm.se2.madn.oberflaeche.gui;

import javafx.fxml.FXML;
import javafx.util.Duration;

import de.hdm.se2.madn.spieler.SpielerInterface;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.transitions.JFXFillTransition;

/**
 * @author Patrick Tobler
 * @email pt017@hdm-stuttgart.de
 * @version 1.0
 *
 */

public class GUIGameInterfaceController
{
	@FXML private MaterialDesignIconView matIcon;
	@FXML private Text gewuerfelterWert;
	@FXML private FlowPane wuerfelPane;
	@FXML private JFXButton wuerfelnButton;
	
	private GUI gui;
	
	private final static Logger logger = LogManager.getLogger(GUIGameInterfaceController.class);

	/**
	 * Setzt die guiInstance in die Variable gui.
	 * 
	 * @param guiInstance Eine Instanz der GUI Klasse.
	 */
	public void setGUI(GUI guiInstance)
	{
		logger.trace("setGUI() aufgerufen.");
		
		gui = guiInstance;
		
		logger.trace("GUI Variable in " + this.getClass() + " über setGUI() gesetzt.");
	}

	/**
	 * Methode die über einen Button in der FXML Datei aufgerufen wird.
	 * Ruft die Methode spielzugUeberspringen() in der GUI Klasse auf.
	 * Ruft die Methode toggleButtonWuerfeln() auf.
	 * Ruft die Methode setGewuerfelterWert() auf.
	 */
    @FXML private void onButtonFireSpielzugUeberspringen() 
    {
		logger.trace("onButtonFireSpielzugÜberspringen() aufgerufen.");

    	gui.spielzugUeberspringen();
		toggleButtonWuerfeln();
		setGewuerfelterWertInvisible();
    }

	/**
	 * Methode die über einen Button in der FXML Datei aufgerufen wird.
	 * Ruft die Methode wuerfeln() in der GUI Klasse auf.
	 */
    @FXML private void onButtonFireWuerfeln() 
    {
		logger.trace("onButtonFireWuerfeln() aufgerufen.");

    	gui.wuerfeln();
    }

	/**
	 * Methode die über einen Button in der FXML Datei aufgerufen wird.
	 * Ruft die Methode spielzugUeberspringen() in der GUI Klasse auf.
	 * Ruft die Methode toggleButtonWuerfeln() auf.
	 * Ruft die Methode setGewuerfelterWert() auf.
	 */
    @FXML private void onButtonFireSpielzugAusfuehren()
    {
		logger.trace("onButtonFireSpielzugAusfuehren() aufgerufen.");

		gui.spielzugAusfuehren();
		toggleButtonWuerfeln();
		setGewuerfelterWertInvisible();
    }
    
    /**
     * Wandelt den Integer wert in String um, setzt den String als Text auf gewuerfelterWert
     * 
     * @param wert Integer, der den gewürfelten Wert anzeigt.
     */
	public void setGewuerfelterWert(int wert)
	{
		logger.trace("setGewuerfelterWert() aufgerufen.");

		wuerfelPane.setVisible(true);
		
		String wertString = Integer.toString(wert);
		gewuerfelterWert.setText(wertString);
	}
	
	/**
	 * Setzt die FlowPane, auf der der gewürfelte Wert + ein Icon dargestellt werden, auf unsichtbar.
	 */
	public void setGewuerfelterWertInvisible()
	{
		logger.trace("setGewuerfelterWertInvisible() aufgerufen.");

		wuerfelPane.setVisible(false);
		
	}
	/**
	 * Ändert den Aktiven Status des wuerfelnButtons auf den gelieferten boolean.
	 * 
	 * @param active Bestimmt ob der wuerfelnButton aktiv oder nicht aktiv ist.
	 */
	public void changeWuerfelnButtonStatus(boolean active)
	{
		logger.trace("changeWuerfelnButtonStatus() aufgerufen.");

		wuerfelnButton.setDisable(active);
	}
	
	/**
	 * Ändert den Text des wuerfelnButtons zu "Würfeln".
	 * Aktiviert den wuerfelnButton.
	 * Ändert die onAction-Methode des Wuerfeln Buttons zu onButtonFireWuerfeln().
	 */
	public void toggleButtonWuerfeln()
	{
		logger.trace("toggleButtonWuerfeln() aufgerufen.");

		wuerfelnButton.setText("Würfeln");
		wuerfelnButton.setDisable(false);
		wuerfelnButton.setOnAction(new EventHandler<ActionEvent>() 
		{
		    @Override public void handle(ActionEvent e) 
		    {
		    	onButtonFireWuerfeln();
		    }
		});
	}
	
	/**
	 * Ändert den Text des wuerfelnButtons zu "Bestätigen".
	 * Aktiviert den wuerfelnButton.
	 * Ändert die onAction-Methode des Wuerfeln Buttons zu onButtonFireSpielzugAusfuehren().
	 */
	public void toggleButtonSpielzugBestaetigen()
	{
		logger.trace("toggleButtonSpielzugBestaetigen() aufgerufen.");
				
		wuerfelnButton.setText("Bestätigen");
		wuerfelnButton.setDisable(true);
		wuerfelnButton.setOnAction(new EventHandler<ActionEvent>() 
		{
		    @Override public void handle(ActionEvent e) 
		    {
		    	onButtonFireSpielzugAusfuehren();
		    }
		});
	}

	/**
	 * Setzt den Style des matIcons zurück und gibt ihm dann über das CSS Style Attribut eine Farbe.
	 * 
	 * @param hexColor Die festgelegte Farbe des matIcons als Hexcode.
	 */
	public void zeigeAktuellerSpieler(String hexColor)
	{
		logger.trace("zeigeAktuellerSpieler() aufgerufen.");

		matIcon.setStyle(null);
		matIcon.setStyle("-fx-fill: " + hexColor + ";");
	}
}