package nl.com.waes.rhmoreira.challenge.service.vo;

import nl.com.waes.rhmoreira.challenge.db.nosql.entity.JsonDocument;

/**
 * Result types of the data evaluation for {@link JsonDocument}
 * @author renato.moreira
 *
 *@see DiffResult
 */
public enum DiffResultType {

	EQUAL("Both base64 data are equal"),
	NOT_SAME_SIZE("Base64 data are not the same size"),
	DIFFERENT_OFFSET("Base64 data are the same size but not equal")
	;

	private String desc;
	
	
	private DiffResultType(String desc) {
		this.desc = desc;
	}

	public String getDescription() {
		return desc;
	}
}
