package nl.com.waes.rhmoreira.challenge.web.rest.vo;

import org.springframework.http.ResponseEntity;

/**
 * Generic class that can wrapped into a {@link ResponseEntity} to represent a uniform
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
	public ResponseObject() {
	}

	public ResponseObject(MessageType type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public ResponseObject(MessageType type, String message) {
		super(type, message);
		// TODO Auto-generated constructor stub
	}

}
