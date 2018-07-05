package nl.com.waes.rhmoreira.challenge.service.strategies.diff;

import nl.com.waes.rhmoreira.challenge.db.nosql.entity.JsonDocument;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResult;
import nl.com.waes.rhmoreira.challenge.service.vo.NoDiffResult;

class NoDiffEvaluatorStrategy implements DiffEvaluatorStrategy{
	
	@Override
	public DiffResult evaluate(JsonDocument jsonDoc) {
		return new NoDiffResult();
	}

}
