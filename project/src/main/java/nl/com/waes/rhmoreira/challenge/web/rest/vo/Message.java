package nl.com.waes.rhmoreira.challenge.web.rest.vo;

import nl.com.waes.rhmoreira.challenge.ChallengeException;
import nl.com.waes.rhmoreira.challenge.web.rest.ChallengeExceptionHandler;

/**
 * Basic class to represent any kind of simple response to be used by the rest controllers.
 * 
 * Note that the class ChallengeExceptionHandler uses this class to respond friendly messages when
 * any ChallengeException are thrown 
 * @author renato.moreira
 *
 * @see {@link ChallengeExceptionHandler}, {@link ChallengeException}
 */
public class Message {
	
	private MessageType type;
	private String message;
	
	Message() {
	}
	
	Message(MessageType type) {
		super();
		this.type = type;
	}

	Message(MessageType type, String message) {
		super();
		this.type = type;
		this.message = message;
	}

	public static Message newSuccess(String message) {
		return new Message(MessageType.SUCCESS, message);
	}
	
	public static Message newError(String message) {
		return new Message(MessageType.ERROR, message);
	}
	
	public static Message newWarning(String message) {
		return new Message(MessageType.WARNING, message);
	}
	
	public static Message newSuccess() {
		return new Message(MessageType.SUCCESS);
	}
	
	public static Message newError() {
		return new Message(MessageType.ERROR);
	}
	
	public static Message newWarning() {
		return new Message(MessageType.WARNING);
	}
	
	public Message message(String message) {
		this.message = message;
		return this;
	}
	
	public MessageType getType() {
		return type;
	}
	public String getMessage() {
		return message;
	}

}
