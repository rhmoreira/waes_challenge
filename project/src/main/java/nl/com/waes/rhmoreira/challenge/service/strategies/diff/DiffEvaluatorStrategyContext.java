package nl.com.waes.rhmoreira.challenge.service.strategies.diff;

import nl.com.waes.rhmoreira.challenge.service.vo.DiffResultType;

public interface DiffEvaluatorStrategyContext{

	DiffEvaluatorStrategy findStrategy(DiffResultType diffTye);
	DiffEvaluatorStrategy getDefaultStrategy();
}
