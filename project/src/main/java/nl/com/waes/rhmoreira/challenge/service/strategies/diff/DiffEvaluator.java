package nl.com.waes.rhmoreira.challenge.service.strategies.diff;

import java.util.HashMap;
import java.util.Map;

import nl.com.waes.rhmoreira.challenge.db.nosql.entity.JsonDocument;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResult;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResultType;

public class DiffEvaluator {
	
	private static final Map<DiffResultType, DiffEvaluatorStrategy> STRATEGIES = new HashMap<>();
	
	static {
		STRATEGIES.put(DiffResultType.EQUAL, new DiffEqualEvaluatorStrategy());
		STRATEGIES.put(DiffResultType.NOT_SAME_SIZE, new DiffNotSameSizeEvaluatorStrategy());
		STRATEGIES.put(DiffResultType.DIFFERENT_OFFSET, new DiffSameSizeDiffOffsetsEvaluatorStrategy());
	}
	
	public DiffResult evaluate(JsonDocument jsonDoc) {
		DiffResult diffResult = null;
		for (DiffResultType diffType: DiffResultType.values()) {
			diffResult = evaluate(diffType, jsonDoc);
			if (diffResult != null)
				break;
		}
		
		return diffResult;
	}
	
	public DiffResult evaluate(DiffResultType diffType, JsonDocument jsonDoc) {
		DiffEvaluatorStrategy evaluatorStrategy = STRATEGIES.get(diffType);
		return evaluatorStrategy.evaluate(jsonDoc);
	}
	
}
