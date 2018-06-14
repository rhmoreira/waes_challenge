package nl.com.waes.rhmoreira.challenge.service;

import nl.com.waes.rhmoreira.challenge.ChallengeException;
import nl.com.waes.rhmoreira.challenge.db.nosql.entity.JsonDocument;
import nl.com.waes.rhmoreira.challenge.db.nosql.entity.Orientation;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResult;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResultType;

/**
 * Provides business logic to store documents containing two properties containing Base64 data and comparison 
 * of those values, whether they are equal or not.
 * 
 * @author renato.moreira
 *
 *@see {@link JsonDocument}, {@link B64DocumentService#evaluateJsonDocument(String)}
 */
public interface B64DocumentService {

	/**
	 * Save/Update a instance of {@link JsonDocument} to a database
	 * 
	 * @param id
	 * @param lValue The left base64 data
	 * @param rValue The right base64 data
	 * @return The persisted <code>JsonDocument</code>
	 * @throws ChallengeException
	 */
	JsonDocument save(String id, String lValue, String rValue) throws ChallengeException;
	
	/**
	 * Save/Update a instance of {@link JsonDocument} to a database, providing only one side of the base64 data.<br>
	 * The property the data will be set, depends on the {@link Orientation} enum value. The only possible 
	 * enum values that can be used are:
	 * <ul>
	 * 	<li>{@link Orientation#LEFT}</li>
	 * 	<li>{@link Orientation#RIGHT}</li>
	 * </ul>
	 * 
	 * @param id
	 * @param value The base64 data
	 * @param orientation The orientation on which property the data will be set
	 * @return The persisted <code>JsonDocument</code>
	 * @throws ChallengeException
	 */
	JsonDocument save(String id, String value, Orientation orientation) throws ChallengeException;
	
	/**
	 * Given the Document ID, evaluates the document date to check whether both sides are equal or not equal. <br><br>
	 * If equal, returns a {@link DiffResult} containing the type {@link DiffResultType#EQUAL}<br><br>
	 * If not equal, the lengths of the data are compared and if not equal, returns a {@link DiffResult} containing the type {@link DiffResultType#NOT_SAME_SIZE}<br><br>
	 * If not equal but have the same length, returns a {@link DiffResult} containing the type {@link DiffResultType#DIFFERENT_OFFSET}<br>
	 * 
	 * @param docId The id of the JsonDocument
	 * @return {@link DiffResult}
	 * @throws ChallengeException
	 */
	public DiffResult evaluateJsonDocument(String docId) throws ChallengeException;
	
}
