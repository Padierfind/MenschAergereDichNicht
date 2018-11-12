package de.hdm.se2.madn.exceptions;

public class FailedToReconstructDataException extends SicherungsException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7399624135266400535L;

	public FailedToReconstructDataException() {
		super("Exception : Can not reconstruct Data from savefile!");
		// TODO Auto-generated constructor stub
	}

	public FailedToReconstructDataException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public FailedToReconstructDataException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public FailedToReconstructDataException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public FailedToReconstructDataException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
