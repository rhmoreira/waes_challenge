package nl.com.waes.rhmoreira.challenge.web.rest.vo;

import nl.com.waes.rhmoreira.challenge.web.rest.privatte.Base64DocumentRestController;

/**
 * Value Object that holds the base64 data posted
 * 
 * @author renato.moreira
 *
 * @see {@link Base64DocumentRestController}
 */
public class Base64Data {
	
	private String value;

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
