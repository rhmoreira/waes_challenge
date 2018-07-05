package nl.com.waes.rhmoreira.challenge.service.strategies.diff;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.com.waes.rhmoreira.challenge.db.nosql.entity.JsonDocument;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResult;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResultType;

class DiffEqualEvaluatorStrategy implements DiffEvaluatorStrategy{
	
	private Logger log = LoggerFactory.getLogger(DiffEqualEvaluatorStrategy.class);

	@Override
	public DiffResult evaluate(JsonDocument jsonDoc) {
		if (jsonDoc.getLeftValue().equals(jsonDoc.getRightValue()) && Arrays.equals(jsonDoc.getLeftValue().getBytes(), jsonDoc.getRightValue().getBytes())) {
			log.trace("Document [{}] data are equal", jsonDoc.getId());
			return new DiffResult(DiffResultType.EQUAL);
		}else
			return null;
	}

}
