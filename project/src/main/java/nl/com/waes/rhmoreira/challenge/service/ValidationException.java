package nl.com.waes.rhmoreira.challenge.service;

import nl.com.waes.rhmoreira.challenge.ChallengeException;

/**
 * Exception to be thrown when validations fail
 * 
 * @author renato.moreira
 *
 */
public class ValidationException extends ChallengeException {

	private static final long serialVersionUID = -3229652987082507599L;

	public ValidationException() {
	}

	public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

}
