package de.hdm.se2.madn.oberflaeche.gui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;

/**
 * @author Patrick Tobler
 * @email pt017@hdm-stuttgart.de
 * @version 1.0
 *
 */

public class GUINavigationDrawerController 
{
	private GUI gui;
	private final static Logger logger = LogManager.getLogger(GUINavigationDrawerController.class);

	@FXML private JFXButton saveButton;
	
	/**
	 * Setzt die guiInstance in die Variable gui.
	 * 
	 * @param guiInstance Eine Instanz der GUI Klasse.
	 */
	public void setGUI(GUI guiInstance)
	{
		logger.trace("setGUI() aufgerufen.");

		gui = guiInstance;
	}
	
	/**
	 * Methode die über einen Button in der FXML Datei aufgerufen wird.
	 * Ändert die Anzeige der sidePane zur SetupPane.
	 * Aktiviert den boolean secondStepOpened in der GUI Klasse, damit der Back-Button erkennt, in welchem Stadium sich die sidePane befindet.
	 */
	@FXML private void onButtonFireStarteNeuesSpiel()
	{
		logger.trace("onButtonFireStarteNeuesSpiel() aufgerufen.");
		
		gui.menuController.setSidePane(gui.setupPane);
		gui.secondStepOpened = true;
	}
	
	/**
	 * Methode die über einen Button in der FXML Datei aufgerufen wird.
	 * Öffnet einen JFileChooser. 
	 * Übergibt die Auswahl des Pfades an die Methode ladeSpiel() in der GUI Klasse.
	 */
	@FXML private void onButtonFireLaden()
	{
		logger.trace("onButtonFireLaden() aufgerufen.");

		try{			
	        JFileChooser chooser = new JFileChooser();
	        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        chooser.setMultiSelectionEnabled(false);

	        if (chooser.showDialog(chooser, "Datei öffnen") == JFileChooser.APPROVE_OPTION) 
	        {
	        	String pfad = chooser.getSelectedFile().getAbsoluteFile().toString();
	    		gui.ladeSpiel(pfad);
	        }
	        
			logger.trace("FileChooser geöffnet.");
		}
		catch(Exception e)
		{
			logger.error("Öffnen des Filechoosers fehlgeschlagen.", e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Methode die über einen Button in der FXML Datei aufgerufen wird.
	 * Öffnet einen JFileChooser. 
	 * Übergibt die Auswahl des Pfades und ob der Pfad bereits existiert, in Form des boolean overwrite, an die Methode speichereSpiel() in der GUI Klasse.	 
	 */
	@FXML private void onButtonFireSicher()
	{
		logger.trace("onButtonFireSicher() aufgerufen.");
		
		try
		{

	        JFileChooser chooser = new JFileChooser();
	        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        chooser.setMultiSelectionEnabled(false);

	        if (chooser.showDialog(chooser, "Datei öffnen") == JFileChooser.APPROVE_OPTION) 
	        {
	        	File f = chooser.getSelectedFile();
	        	boolean overwrite = false;
	        	
	        	if(f.exists())
	        	{
	        		overwrite = true;
	        	}
	        	else
	        	{
	        		overwrite = false;
	        	}
	        	
	        	String pfad = f.getAbsoluteFile().toString();
	        	gui.speichereSpiel(pfad, overwrite);
	        }
		}
		catch(Exception e)
		{
			logger.error("Öffnen des Filechoosers fehlgeschlagen.", e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Methode die über einen Button in der FXML Datei aufgerufen wird.
	 * Ändert die Anzeige der SidePane zur RulePane.
	 * Aktiviert den boolean secondStepOpened in der GUI Klasse, damit der Back-Button erkennt, in welchem Stadium sich die sidePane befindet.
	 */
	@FXML private void onButtonFireRegeln()
	{
		logger.trace("onButtonFireRegeln() aufgerufen.");

		gui.menuController.setSidePane(gui.rulePane);
		gui.secondStepOpened = true;
	}
	

	/**
	 * Methode die über einen Button in der FXML Datei aufgerufen wird.
	 * Ruft die Methode beendeSpiel() in der GUI Klasse auf.
	 */
	@FXML private void onButtonFireBeenden()
	{
		logger.trace("onButtonFireBeenden() aufgerufen.");

		gui.beendeSpiel();
	}
	
	/**
	 * Aktiviert den SaveButton.
	 */
	public void activateSaveButton()
	{
		saveButton.setDisable(false);
	}
}
