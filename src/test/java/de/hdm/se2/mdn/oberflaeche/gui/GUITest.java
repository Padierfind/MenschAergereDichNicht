package de.hdm.se2.mdn.oberflaeche.gui;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import de.hdm.se2.madn.exceptions.SicherungsException;
import de.hdm.se2.madn.oberflaeche.gui.GUI;
import javafx.scene.paint.Color;

/**
 * @author Patrick Tobler
 * @email pt017@hdm-stuttgart.de
 * @version 1.0
 *
 */

public class GUITest 
{
	@Test
	public void hexTest()
	{
		Color testColor = Color.RED;
		
		assertEquals("#FF0000", new GUI().hex(testColor));
	}		
}