package nl.com.waes.rhmoreira.challenge.service.vo;

import nl.com.waes.rhmoreira.challenge.db.nosql.entity.JsonDocument;
import nl.com.waes.rhmoreira.challenge.service.B64DocumentService;

/**
 * Class responsible for holding the results of the comparison between 2 base64 data
 * 
 * @author renato.moreira
 * 
 * @see {@link JsonDocument}, {@link B64DocumentService}
 *
 */
public class DiffResult {
	
	/**
	 * Friendly message of the comparison
	 */
	private String result;
	
	/**
	 * The result type
	 */
	private DiffResultType diffType;

	public DiffResult() {
	}
	
	/**
	 * Creates a result given the provided type
	 * @param diffType
	 */
	public DiffResult(DiffResultType diffType) {
		this(diffType, "");
	}

	/**
	 * Creates a result given the provided type with extra info.
	 * @param diffType The type
	 * @param result Extra info
	 */
	public DiffResult(DiffResultType diffType, String result) {
		this.diffType = diffType;
		this.result = diffType.getDescription() + ". " + result;
	}

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public DiffResultType getDiffType() {
		return diffType;
	}
	public void setDiffType(DiffResultType diffType) {
		this.diffType = diffType;
	}

}
