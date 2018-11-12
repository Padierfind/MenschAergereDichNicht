package de.hdm.se2.madn.oberflaeche;

import de.hdm.se2.madn.spielkontrolle.SpielKontrolleInterface;
import javafx.stage.Stage;

/**
 * @author Patrick Tobler
 * @email pt017@hdm-stuttgart.de
 * @version 1.0
 *
 */
public abstract class Oberflaechenkontrolle implements OberflaechenKontrolleInterface 
{
	protected SpielKontrolleInterface spielKontrolle;
	
	public Oberflaechenkontrolle(SpielKontrolleInterface spielKontrolle) 
	{
		this.spielKontrolle = spielKontrolle;
	}
}
