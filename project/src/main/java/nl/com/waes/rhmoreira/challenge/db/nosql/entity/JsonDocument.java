package nl.com.waes.rhmoreira.challenge.db.nosql.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import nl.com.waes.rhmoreira.challenge.ChallengeException;

@Document(collection="JSON_DOCS")
public class JsonDocument {
	
	@Id
	private String id;
	private String rightValue;
	private String leftValue;
	
	public JsonDocument() {
	}
	
	public JsonDocument(String id) {
		this.id = id;
	}
	
	public JsonDocument(String id, Orientation orientation, String value) {
		super();
		this.id = id;
		setValue(orientation, value);
	}
	
	public JsonDocument(String id, String rightValue, String leftValue) {
		super();
		this.id = id;
		this.rightValue = rightValue;
		this.leftValue = leftValue;
	}

	public void setValue(Orientation orientation, String value) {
		if (Orientation.LEFT == orientation)
			setLeftValue(value);
		else if (Orientation.RIGHT == orientation)
			setRightValue(value);
		else
			throw new ChallengeException("The orientation [" + orientation + "] does not match neither [LEFT, or RIGHT]");
	}
	
	public void setValues(String lValue, String rValue) {
		this.leftValue = lValue;
		this.rightValue = rValue;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRightValue() {
		return rightValue;
	}
	public void setRightValue(String rightValue) {
		this.rightValue = rightValue;
	}
	public String getLeftValue() {
		return leftValue;
	}
	public void setLeftValue(String leftValue) {
		this.leftValue = leftValue;
	}
}
