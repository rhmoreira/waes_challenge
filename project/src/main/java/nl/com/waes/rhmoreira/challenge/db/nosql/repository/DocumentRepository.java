package nl.com.waes.rhmoreira.challenge.db.nosql.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import nl.com.waes.rhmoreira.challenge.ChallengeException;
import nl.com.waes.rhmoreira.challenge.db.nosql.entity.JsonDocument;

/**
 * Repository class responsible for CRUD operations of {@link JsonDocument} objects
 * 
 * @author renato.moreira
 *
 */
@Repository
public class DocumentRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	
	public JsonDocument save(JsonDocument jsonDoc) throws ChallengeException {
		mongoTemplate.save(jsonDoc);
		return findById(jsonDoc.getId());
	}
	
	public JsonDocument findById(String id) {
		JsonDocument doc = mongoTemplate.findById(id, JsonDocument.class);
		return doc;
	}
}
