package de.hdm.se2.madn.exceptions;

/**
 * 
 * @author Philip Betzler
 * @email pb060@hdm-stuttgart.de
 * @version 1.0
 * 
 * UngueltigerLaufWegException wird ausgeloest, wenn ein Vorgeschlagener Weg nicht umsetztbar ist oder gegen gesetzte Regeln verstoesst!
 *
 */
public class UngueltigerLaufWegException extends Exception {

	public UngueltigerLaufWegException()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public UngueltigerLaufWegException(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}

	public UngueltigerLaufWegException(Throwable cause)
	{
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
