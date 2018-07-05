package nl.com.waes.rhmoreira.challenge.service.strategies.diff;

import nl.com.waes.rhmoreira.challenge.db.nosql.entity.JsonDocument;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResult;

public interface DiffEvaluatorStrategy{

	DiffResult evaluate(JsonDocument jsonDoc);
	
}
