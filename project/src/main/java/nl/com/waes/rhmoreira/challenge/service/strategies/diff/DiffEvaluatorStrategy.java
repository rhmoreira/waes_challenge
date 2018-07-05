package nl.com.waes.rhmoreira.challenge.service.strategies.diff;

import nl.com.waes.rhmoreira.challenge.db.nosql.entity.JsonDocument;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResult;

/**
 * Strategy Pattern interface to deal with all the evaluation base 64 algorithms
 * 
 * @author Renato
 *
 */
public interface DiffEvaluatorStrategy{


	/**
	 * Evaluates both sides of the data within the JsonDocument. The result depends on what strategy is 
	 * actually running the algorithm 
	 * 
	 * @param jsonDoc The document to be evaluated
	 * @return {@link DiffResult} The result, depending on the strategy implementation
	 */
	DiffResult evaluate(JsonDocument jsonDoc);
	
}
