package nl.com.waes.rhmoreira.challenge.service;


import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.com.waes.rhmoreira.challenge.ChallengeException;
import nl.com.waes.rhmoreira.challenge.db.nosql.entity.JsonDocument;
import nl.com.waes.rhmoreira.challenge.db.nosql.entity.Orientation;
import nl.com.waes.rhmoreira.challenge.db.nosql.repository.DocumentRepository;
import nl.com.waes.rhmoreira.challenge.service.strategies.diff.DiffEvaluator;
import nl.com.waes.rhmoreira.challenge.service.strategies.diff.DiffEvaluatorStrategy;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResult;

/**
 * Service class to provide business operations for {@link JsonDocument} classes
 * 
 * @author renato.moreira
 *
 */
@Service
public class B64DocumentServiceImpl implements B64DocumentService{
	
	private Logger log = LoggerFactory.getLogger(B64DocumentServiceImpl.class);

	@Autowired
	private DocumentRepository docRepo;

	public JsonDocument save(String id, String lValue, String rValue) throws ChallengeException {
		if (lValue != null && !isBase64(lValue))
			throw new ValidationException("Left side of the data is not a valid base64 value");
		if (rValue != null && !isBase64(rValue))
			throw new ValidationException("Right side of the data is not a valid base64 value");

		JsonDocument jsonDoc = getDocument(id);
		jsonDoc.setLeftValue(lValue);
		jsonDoc.setRightValue(rValue);
		
		log.trace("Saving document {} with both data sides", id);
		return save(jsonDoc);
	}

	public JsonDocument save(String id, String value, Orientation orientation) throws ChallengeException {
		if (value != null && !isBase64(value))
			throw new ValidationException("The data is not a valid base64 value");
		
		JsonDocument jsonDoc = getDocument(id);
		jsonDoc.setValue(orientation, value);
		
		log.trace("Saving {} side data of document {}", orientation.name(), id);
		return save(jsonDoc);
	}
	
	/**
	 * Validate the document before saving it.
	 * 
	 * @param jsonDoc
	 * @return Returns the persisted document
	 */
	private JsonDocument save(JsonDocument jsonDoc) {
		if (jsonDoc.getId() == null)
			throw new ValidationException("Can not save document with null id");
		
		if (jsonDoc.getLeftValue() == null && jsonDoc.getRightValue() == null)
			throw new ValidationException("Can not save a document with null data on both sides");
		
		log.debug("Saving document with id {}", jsonDoc.getId());
		
		jsonDoc = docRepo.save(jsonDoc);
		
		log.trace("Document {} saved. Left side: {}, Right side: {}", 
				jsonDoc.getId(), 
				jsonDoc.getLeftValue() != null ? "SET" : "EMPTY",
				jsonDoc.getRightValue() != null ? "SET" : "EMPTY");
		
		return jsonDoc;
	}
	
	private JsonDocument getDocument(String id) {
		JsonDocument jsonDoc = docRepo.findById(id);
		if (jsonDoc == null)
			jsonDoc = new JsonDocument(id);
		
		return jsonDoc;
	}
	
	/**
	 * Tries to decode the provided base64 value. If its possible, returns true, false otherwise
	 * 
	 * @param value The base64 contents
	 * @return False if the contents are not a valid base64 data, true otherwise.
	 */
	public boolean isBase64(String value) {
		try {
			Base64.getDecoder().decode(value);
			return true;
		}catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	public DiffResult evaluateJsonDocument(String docId) throws ChallengeException{
		log.debug("Searching for document id {}", docId);
		
		JsonDocument jsonDoc = docRepo.findById(docId);
		if (jsonDoc == null) {
			log.trace("Document {} not found", docId);
			throw new ValidationException(String.format("Document not found for ID [%s]", docId));
		}
		
		log.trace("Document {} found", docId);
		
		if (jsonDoc.getLeftValue() == null || jsonDoc.getRightValue() == null) {
			log.trace("Data for document {} is missing", docId);
			throw new ValidationException(String.format("Base64 data missing for comparison for document id [%s]", docId));
		}
		
		String leftValue = jsonDoc.getLeftValue();
		String rightValue = jsonDoc.getRightValue();
		byte[] leftValueBytes = leftValue.getBytes();
		byte[] rightValueBytes = rightValue.getBytes();
		
		log.debug("Document {}: Left length = {}, Right length: {}", docId, leftValueBytes.length, rightValueBytes.length);
		
		DiffEvaluatorStrategy diffStrategy = new DiffEvaluator();
		DiffResult diffResult = diffStrategy.evaluate(jsonDoc);
			
		return diffResult;
	}
	
}
