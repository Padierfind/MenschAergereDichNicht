package de.hdm.se2.madn.oberflaeche.gui;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderPane;

/**
 * @author Patrick Tobler
 * @email pt017@hdm-stuttgart.de
 * @version 1.0
 *
 */

public class GUIMenuController implements Initializable
{
	@FXML private JFXHamburger menuBurger;
	@FXML private JFXDrawer menuDrawer;
	@FXML private GridPane grid;
	@FXML public BorderPane border;
	
	private GUI gui;
	
    private ObservableList<javafx.scene.Node> buttonIDs;
    private HashMap<Integer, Integer> buttonObsListIndex = new HashMap<Integer, Integer>();

	private final static Logger logger = LogManager.getLogger(GUIMenuController.class);

	/**
	 * Setzt die Transitions für den JFXHamburger.
	 * Weißt den Ingame Buttons die waehleSpielfeldPositionAus() mit der jeweiligen ID als Parameter als onAction() Methode zu.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		logger.trace("initialize() aufgerufen.");

		try
		{
			HamburgerBackArrowBasicTransition menuburgerTransition = new HamburgerBackArrowBasicTransition(menuBurger);
			menuDrawer.setOnDrawerClosing((e) ->
			{
				menuburgerTransition.setRate(-1);
				menuburgerTransition.play();
				
			});

			menuDrawer.setOnDrawerClosed((e) ->
			{
				menuDrawer.setSidePane(gui.menuPane);
			});

			menuDrawer.setOnDrawerOpening((e) ->
			{
				menuburgerTransition.setRate(1);
				menuburgerTransition.play();
			});
		}
		catch(Exception e)
		{
			logger.error("Fehler beim Versuch die Hamburger Transitions zu setzen.");
			e.printStackTrace();
		}
		
		
		try
		{
			buttonIDs = grid.getChildren().sorted();
			int x = 0;
			
			for(javafx.scene.Node node : buttonIDs)
			{	
				String a = node.getId();
				a = a.substring(4,a.length());
				
				int foo = Integer.parseInt(a);

				JFXButton button = (JFXButton) node;
				
				button.setOnAction(new EventHandler<ActionEvent>() 
				{
				    @Override public void handle(ActionEvent e) 
				    {
				    	waehleSpielfeldPositionAus(foo);
				    }
				});

				buttonObsListIndex.put(foo, x);
				x++;
			}
		}
		catch(Exception e)
		{
			logger.error("Fehler beim Versuch den Ingame-Buttons onAction-Funktionen zuzuweisen.");
			e.printStackTrace();
		}
	}
	
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

		menuDrawer.setSidePane(gui.menuPane);
		menuDrawer.setDefaultDrawerSize(170);
		menuDrawer.open();
	}

	/**
	 * Ruft die Methode spielFeldPositionAusgewaehlt() in der GUI Klasse auf. Übergibt ihr den Integer x, der die FeldID ist.
	 * 
	 * @param x
	 */
	public void waehleSpielfeldPositionAus(int x)
	{
		logger.trace("Feld mit der ID: " + x + " in der Methode waehleSpielfeldPositionAus() aufgerufen.");

		gui.spielFeldPositionAusgewaehlt(x);
	}
	
	/**
	 * Checkt ob sich das Menü im ersten oder im zweiten Step befindet oder geschlossen. 
	 * Reagiert dementsprechend, indem entweder das Menü schließt, öffnet oder einen Step zurück geht.
	 */
	@FXML public void maleMenuGUI()
	{
		logger.trace("maleMenuGUI() aufgerufen.");

		if(menuDrawer.isShown() == true)
		{
			if(gui.secondStepOpened == false)
			{
				menuDrawer.close();
			}
			else
			{
				menuDrawer.setSidePane(gui.menuPane);
				gui.secondStepOpened = false;
			}
		}
		else
		{
			menuDrawer.open();
		}
	}
	
	/**
	 * Ändert die SidePane des menuDrawers.
	 * 
	 * @param pane Die neue SidePane
	 */
	public void setSidePane(FlowPane pane)
	{
		logger.trace("setSidePane() mit der FlowPane " + pane + "aufgerufen.");

		menuDrawer.setSidePane(pane);
	}
	
	/**
	 * Loopt durch die HashMap felderFarben und setzt die Farben der Buttons dementsprechend. 
	 * Keys der Hashmap sind die FeldIDs, Values die Farben.
	 * @param felderFarben
	 */
	public void maleGameGUI(HashMap felderFarben)
	{
		logger.trace("maleGameGUIs() aufgerufen.");

		try
		{
			HashMap<Integer, Color> hashMap = felderFarben;
			menuDrawer.close();
			border.setRight(gui.gameInterfacePane);
			border.setMargin(gui.gameInterfacePane, new Insets(20, 20, 20, 0));

		    gui.primeStage.setMinWidth(910);
		    
		    for(Entry<Integer, Color> entry : hashMap.entrySet())
		    {
		    	int currButtonID = buttonObsListIndex.get(entry.getKey());
		    	
		    	JFXButton button = (JFXButton) buttonIDs.get(currButtonID);
		    	button.getStyleClass().clear();
		    	button.getStyleClass().add("button-unfilled");
		    	
		    	Color entryColor = entry.getValue();
		    	String hexColor = gui.hex(entryColor);
		    	
		    	button.setStyle("-fx-background-color: " + hexColor + ";");
		    }

		}
		catch(Exception e)
		{
			logger.error("Fehler beim Versuch die Hashmap felderFarben in der Methode maleGAmeGUI() auf das GUI zu zeichnen.");
			e.printStackTrace();
		}
	}
}
