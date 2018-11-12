package de.hdm.se2.madn.exceptions;

public class BuildingDataException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9050140492110683600L;

	public BuildingDataException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public BuildingDataException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public BuildingDataException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public BuildingDataException() {
		super("Error, cant initialise game due wrong parameter!");
		// TODO Auto-generated constructor stub
	}

	public BuildingDataException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
