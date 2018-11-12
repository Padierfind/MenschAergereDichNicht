package de.hdm.se2.madn.exceptions;

public class WrongFileFormatException extends SicherungsException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrongFileFormatException() {
		super("Exception : File Format doesnt match Savegame format! Can not read from file.");
		// TODO Auto-generated constructor stub
	}

	public WrongFileFormatException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public WrongFileFormatException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public WrongFileFormatException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public WrongFileFormatException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	

}
