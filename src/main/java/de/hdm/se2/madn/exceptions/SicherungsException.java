package de.hdm.se2.madn.exceptions;

public abstract class SicherungsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected SicherungsException (String message) {
		super(message);
	}
	
	protected SicherungsException () {
		super();
	}

	protected SicherungsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	protected SicherungsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	protected SicherungsException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
