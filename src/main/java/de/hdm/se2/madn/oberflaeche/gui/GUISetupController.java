package de.hdm.se2.madn.oberflaeche.gui;


import javafx.fxml.FXML;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jfoenix.controls.JFXSlider;

/**
 * @author Patrick Tobler
 * @email pt017@hdm-stuttgart.de
 * @version 1.0
 *
 */

public class GUISetupController 
{
    @FXML private JFXSlider playerCountSlider;
    @FXML private JFXSlider botCountSlider;

	private final static Logger logger = LogManager.getLogger(GUISetupController.class);

	private GUI gui;
	
	/**
	 * Setzt die guiInstance in die Variable gui.
	 * 
	 * @param guiInstance Eine Instanz der GUI Klasse.
	 */
	public void setGUI(GUI guiInstance)
	{
		logger.trace("setGUIInstance() mit GUIInstance: " + guiInstance + " aufgerufen.");

		gui = guiInstance;
	}
	
	/**
	 * Methode die über einen Button in der FXML Datei aufgerufen wird.
	 * Frägt die Werte der beiden Slider ab.
	 * Ruft die Methode starteSpiel() in der GUI Klasse auf und übergibt ihr die Slider-Werte.
	 */
    @FXML private void onButtonFireStart()
    {
    	logger.trace("onButtonFireStart() aufgerufen.");
    	
    	int playerCount = (int) playerCountSlider.getValue();
    	int botCount = (int) botCountSlider.getValue();

    	gui.starteSpiel(playerCount, botCount);
    }
    
	/**
	 * Methode die über einen Klick auf den botSlider aufgerufen wird.
	 * Frägt den Wert des Sliders ab. Berechnet die Differenz zu 4 und setzt den Wert des playerSliders auf diese Differenz.
	 */
	@FXML private void botSliderClicked ()
	{
    	logger.trace("botSliderClicked() aufgerufen.");

    	int botCount = (int) botCountSlider.getValue();
		int x = 4 - botCount;
		
		playerCountSlider.setValue(x);
	}
	

	/**
	 * Methode die über einen Klick auf den playerSlider aufgerufen wird.
	 * Frägt den Wert des Sliders ab. Berechnet die Differenz zu 4 und setzt den Wert des botSliders auf diese Differenz.
	 */
	@FXML private void playerSliderClicked ()
	{
    	logger.trace("playerSliderClicked() aufgerufen.");

    	int playerCount = (int) playerCountSlider.getValue();
		int x = 4 - playerCount;
		
		botCountSlider.setValue(x);
	}
}
