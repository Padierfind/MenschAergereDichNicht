package de.hdm.se2.madn.exceptions;

public class WrongFileEndingException extends SicherungsException{

	public WrongFileEndingException() {
		super("Exception : File ending doesnt match Savegame ending!");
		// TODO Auto-generated constructor stub
	}

	public WrongFileEndingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public WrongFileEndingException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public WrongFileEndingException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public WrongFileEndingException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
