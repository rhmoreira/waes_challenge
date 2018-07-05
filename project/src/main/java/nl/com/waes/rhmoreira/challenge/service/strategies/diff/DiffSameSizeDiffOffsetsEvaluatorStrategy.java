package nl.com.waes.rhmoreira.challenge.service.strategies.diff;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.com.waes.rhmoreira.challenge.db.nosql.entity.JsonDocument;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResult;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResultType;

class DiffSameSizeDiffOffsetsEvaluatorStrategy implements DiffEvaluatorStrategy{
	
	private Logger log = LoggerFactory.getLogger(DiffSameSizeDiffOffsetsEvaluatorStrategy.class);

	@Override
	public DiffResult evaluate(JsonDocument jsonDoc) {
		byte[] leftValueBytes = jsonDoc.getLeftValue().getBytes();
		byte[] rightValueBytes = jsonDoc.getRightValue().getBytes();
		
		if (leftValueBytes.length == rightValueBytes.length) {
			List<String> diffs = new ArrayList<>();
			for (int i = 0; i < leftValueBytes.length; i++) {
				byte leftByte = leftValueBytes[i];
				byte rightByte = rightValueBytes[i];
				
				if ( (leftByte & rightByte) != leftByte) {
					int offset = leftByte ^ rightByte;
	
					log.trace("Document {}: Offset={}, Index={}", jsonDoc.getId(),  offset, i);
					diffs.add(String.format(" Index=%d <-> Offset=%d", i, offset));
				}
			}
			
			return new DiffResult(DiffResultType.DIFFERENT_OFFSET, "Differences: " + diffs.toString());
		}else
			return null;
		
	}

}
