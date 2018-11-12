package de.hdm.se2.madn.oberflaeche.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm.se2.madn.spieler.SpielerInterface;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.FXML;
import javafx.geometry.Insets;

/**
 * @author Patrick Tobler
 * @email pt017@hdm-stuttgart.de
 * @version 1.0
 *
 */

public class GUIGameOverScreenController 
{

    @FXML private MaterialDesignIconView matIcon;
    
	private GUI gui;
	private final static Logger logger = LogManager.getLogger(GUIMenuController.class);
	
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
	 * Setzt den Style des matIcons zurück und gibt ihm dann über das CSS Style Attribut eine Farbe.
	 * 
	 * @param hexColor Die festgelegte Farbe des matIcons als Hexcode.
	 */
	public void setWinner(String hexColor)
	{
		logger.trace("setWinner() aufgerufen.");
		
		gui.menuController.border.setRight(gui.gameOverPane);
		gui.menuController.border.setMargin(gui.gameOverPane, new Insets(20, 20, 20, 0));

		matIcon.setStyle(null);
		matIcon.setStyle("-fx-fill: " + hexColor + ";");
	}
}