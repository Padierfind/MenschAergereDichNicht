package de.hdm.se2.madn.exceptions;

public class InvalidDataException extends SicherungsException{

	public InvalidDataException() {
		super("Exception : Datas to save are invalid! Can not create Savegame!");
		// TODO Auto-generated constructor stub
	}

	public InvalidDataException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public InvalidDataException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidDataException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidDataException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
