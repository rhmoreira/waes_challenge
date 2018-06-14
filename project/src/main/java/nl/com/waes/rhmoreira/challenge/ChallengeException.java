package nl.com.waes.rhmoreira.challenge;

public class ChallengeException extends RuntimeException {

	private static final long serialVersionUID = 2570275010012781725L;

	public ChallengeException() {
		super();
	}

	public ChallengeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ChallengeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ChallengeException(String message) {
		super(message);
	}

	public ChallengeException(Throwable cause) {
		super(cause);
	}

}
