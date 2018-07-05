package nl.com.waes.rhmoreira.challenge.service.strategies.diff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.com.waes.rhmoreira.challenge.db.nosql.entity.JsonDocument;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResult;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResultType;

class DiffNotSameSizeEvaluatorStrategy implements DiffEvaluatorStrategy{
	
	private Logger log = LoggerFactory.getLogger(DiffNotSameSizeEvaluatorStrategy.class);

	@Override
	public DiffResult evaluate(JsonDocument jsonDoc) {
		if (jsonDoc.getLeftValue().getBytes().length != jsonDoc.getRightValue().getBytes().length) {
			log.trace("Document {} data are not equal and have different byte lengths", jsonDoc.getId());
			
			return new DiffResult(DiffResultType.NOT_SAME_SIZE);
		}else
			return null;
	}

}
