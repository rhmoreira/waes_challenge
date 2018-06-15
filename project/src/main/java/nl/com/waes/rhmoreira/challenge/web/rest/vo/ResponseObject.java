package nl.com.waes.rhmoreira.challenge.web.rest.vo;

import org.springframework.http.ResponseEntity;

/**
 * Generic class that can be wrapped by {@link ResponseEntity} to represent a uniform
 * response to be used by the rest controllers.
 * @author renato.moreira
 *
 */
public class ResponseObject extends Message {
	
	public Object value;

	public ResponseObject(Object value) {
		this(MessageType.SUCCESS, value);
	}
	public ResponseObject(MessageType type, Object value) {
		super(type);
		this.value = value;
	}

}
