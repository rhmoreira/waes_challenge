package nl.com.waes.rhmoreira.challenge.service.strategies.diff;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import nl.com.waes.rhmoreira.challenge.service.vo.DiffResultType;

@Service
public class DiffEvaluator implements DiffEvaluatorStrategyContext{
	
	private static final Map<DiffResultType, DiffEvaluatorStrategy> STRATEGIES = new LinkedHashMap<>();
	
	static {
		STRATEGIES.put(DiffResultType.EQUAL, new DiffEqualEvaluatorStrategy());
		STRATEGIES.put(DiffResultType.NOT_SAME_SIZE, new DiffNotSameSizeEvaluatorStrategy());
		STRATEGIES.put(DiffResultType.DIFFERENT_OFFSET, new DiffSameSizeDiffOffsetsEvaluatorStrategy());
		STRATEGIES.put(null, new NoDiffEvaluatorStrategy());
	}
	
	@Override
	public DiffEvaluatorStrategy findStrategy(DiffResultType diffType) {
		DiffEvaluatorStrategy evaluatorStrategy = STRATEGIES.get(diffType);
		return evaluatorStrategy;
	}
	
	@Override
	public DiffEvaluatorStrategy getDefaultStrategy() {
		return findStrategy(null);
	}
	
}
