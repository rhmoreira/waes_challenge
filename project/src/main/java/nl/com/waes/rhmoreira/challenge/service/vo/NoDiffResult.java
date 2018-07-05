package nl.com.waes.rhmoreira.challenge.service.vo;

import nl.com.waes.rhmoreira.challenge.db.nosql.entity.JsonDocument;
import nl.com.waes.rhmoreira.challenge.service.B64DocumentService;

/**
 * Null Object Pattern to represent no result in the evaluation;
 * 
 * @author renato.moreira
 * 
 * @see {@link JsonDocument}, {@link B64DocumentService}
 *
 */
public class NoDiffResult extends DiffResult {
	
	public NoDiffResult() {
		super(null, "No evaluation could be made");
	}

}
