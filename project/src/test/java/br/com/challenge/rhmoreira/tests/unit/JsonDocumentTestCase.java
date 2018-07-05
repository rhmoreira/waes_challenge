package br.com.challenge.rhmoreira.tests.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.challenge.rhmoreira.tests.ChallengeBaseTest;
import br.com.challenge.rhmoreira.tests.functional.ExceptionCatcher;
import nl.com.waes.rhmoreira.challenge.ChallengeException;
import nl.com.waes.rhmoreira.challenge.db.nosql.entity.JsonDocument;
import nl.com.waes.rhmoreira.challenge.db.nosql.entity.Orientation;
import nl.com.waes.rhmoreira.challenge.db.nosql.repository.DocumentRepository;
import nl.com.waes.rhmoreira.challenge.service.B64DocumentService;
import nl.com.waes.rhmoreira.challenge.service.B64DocumentServiceImpl;
import nl.com.waes.rhmoreira.challenge.service.strategies.diff.DiffEvaluatorStrategyContext;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResult;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResultType;

@RunWith(SpringRunner.class)
public class JsonDocumentTestCase extends ChallengeBaseTest{
	
	protected Logger log = LoggerFactory.getLogger(JsonDocumentTestCase.class);

	private B64DocumentService docService;
	@Mock
	private DocumentRepository docRepo;
	@Autowired
	private DiffEvaluatorStrategyContext strategyContext;
	private boolean setupDone;

	private static final String TEST_DOC_ID = "ABCDEFGH123";

	private static final String TEST_DOC_EQUAL_VALUE1 = "Y2hhbGxlbmdl";
	private static final String TEST_DOC_DIFF_VALUE2 = "ZWduZWxsYWhj";
	private static final String TEST_DOC_DIFF_VALUE3 = "ZWduZWxsYWh";
	
	@Before
	public void setup() {
		if (!setupDone)
			this.docService = new B64DocumentServiceImpl(docRepo, strategyContext);
		
		setupDone = true;
	}
	
	
	@Test
	public void saveLeftAndRight() {
		JsonDocument jsonDoc = new JsonDocument(TEST_DOC_ID, TEST_DOC_EQUAL_VALUE1, TEST_DOC_EQUAL_VALUE1);
		Mockito.doReturn(jsonDoc).when(docRepo).save(Mockito.any(JsonDocument.class));
		Mockito.doReturn(jsonDoc).when(docRepo).findById(Mockito.eq(TEST_DOC_ID));
		
		JsonDocument savedDocMock = docService.save(TEST_DOC_ID, TEST_DOC_EQUAL_VALUE1, TEST_DOC_EQUAL_VALUE1);
		JsonDocument docFoundMock = docRepo.findById(TEST_DOC_ID);
		
		assertEquals(savedDocMock.getId(), jsonDoc.getId());
		assertEquals(docFoundMock.getId(), jsonDoc.getId());
		
		assertNotNull(savedDocMock.getLeftValue());
		assertNotNull(savedDocMock.getRightValue());
		assertNotNull(docFoundMock.getLeftValue());
		assertNotNull(docFoundMock.getRightValue());
	}
	
	@Test
	public void saveLeftOnly() {
		JsonDocument jsonDoc = new JsonDocument(TEST_DOC_ID, Orientation.LEFT, TEST_DOC_EQUAL_VALUE1);
		Mockito.doReturn(jsonDoc).when(docRepo).save(Mockito.any(JsonDocument.class));
		Mockito.doReturn(jsonDoc).when(docRepo).findById(Mockito.eq(TEST_DOC_ID));
		
		JsonDocument savedDocMock = docService.save(TEST_DOC_ID, TEST_DOC_EQUAL_VALUE1, Orientation.LEFT);
		JsonDocument docFoundMock = docRepo.findById(TEST_DOC_ID);
		
		assertNotNull(savedDocMock.getLeftValue());
		assertNotNull(docFoundMock.getLeftValue());
		assertNull(savedDocMock.getRightValue());
		assertNull(docFoundMock.getRightValue());
	}
	
	@Test
	public void saveRightOnly() {
		JsonDocument jsonDoc = new JsonDocument(TEST_DOC_ID, Orientation.RIGHT, TEST_DOC_EQUAL_VALUE1);
		Mockito.doReturn(jsonDoc).when(docRepo).save(Mockito.any(JsonDocument.class));
		Mockito.doReturn(jsonDoc).when(docRepo).findById(Mockito.eq(TEST_DOC_ID));
		
		JsonDocument savedDocMock = docService.save(TEST_DOC_ID, TEST_DOC_EQUAL_VALUE1, Orientation.RIGHT);
		JsonDocument docFoundMock = docRepo.findById(TEST_DOC_ID);
		
		assertNotNull(savedDocMock.getRightValue());
		assertNotNull(docFoundMock.getRightValue());
		assertNull(savedDocMock.getLeftValue());
		assertNull(docFoundMock.getLeftValue());
	}
	
	@Test
	public void saveNullValue() {
		Throwable exception = ExceptionCatcher
										.doTry(() -> docService.save(TEST_DOC_ID, null, Orientation.LEFT))
										.doCatch();
		
		assertNotNull(exception);
		assertTrue(exception instanceof ChallengeException);
		assertEquals(exception.getMessage(), "Can not save a document with null data on both sides");
	}
	
	@Test
	public void saveNullId() {
		Throwable exception = ExceptionCatcher
										.doTry(() -> docService.save(null, TEST_DOC_EQUAL_VALUE1, Orientation.LEFT))
										.doCatch();
		
		assertNotNull(exception);
		assertTrue(exception instanceof ChallengeException);
		assertEquals(exception.getMessage(), "Can not save document with null id");
	}
	
	@Test
	public void saveInvalidBase64Value() {
		String value = "123456789";
		
		// <LEFT VALUE>
		Throwable leftValueException = ExceptionCatcher
										.doTry(() -> docService.save(TEST_DOC_ID, value, Orientation.LEFT))
										.doCatch();
		
		assertNotNull(leftValueException);
		assertTrue(leftValueException instanceof ChallengeException);
		assertEquals(leftValueException.getMessage(), "The data is not a valid base64 value");
		// </LEFT VALUE>
		
		// <RIGHT VALUE>
		Throwable rightValueException = ExceptionCatcher
										.doTry(() -> docService.save(TEST_DOC_ID, value, Orientation.RIGHT))
										.doCatch();
		
		assertNotNull(rightValueException);
		assertTrue(rightValueException instanceof ChallengeException);
		assertEquals(rightValueException.getMessage(), "The data is not a valid base64 value");
		// </RIGHT VALUE>
		
		// <LEFT AND RIGHT VALUES> 
		//	NULL RIGHT
		String nullValue = null;
		Throwable leftAndRightValueException = ExceptionCatcher
													.doTry(() -> docService.save(TEST_DOC_ID, value, nullValue))
													.doCatch();

		assertNotNull(leftAndRightValueException);
		assertTrue(leftAndRightValueException instanceof ChallengeException);
		assertEquals(leftAndRightValueException.getMessage(), "Left side of the data is not a valid base64 value");
		
		//	NULL LEFT
		leftAndRightValueException = ExceptionCatcher
										.doTry(() -> docService.save(TEST_DOC_ID, nullValue, value))
										.doCatch();

		assertNotNull(leftAndRightValueException);
		assertTrue(leftAndRightValueException instanceof ChallengeException);
		assertEquals(leftAndRightValueException.getMessage(), "Right side of the data is not a valid base64 value");
		// </LEFT AND RIGHT VALUES> 
	}
	
	@Test
	public void docNotFoundForEvaluation() {
		Throwable exception = ExceptionCatcher
									.doTry(() -> docService.evaluateJsonDocument("INEXISTENT_ID"))
									.doCatch();
		
		assertNotNull(exception);
		assertTrue(exception instanceof ChallengeException);
		assertEquals(exception.getMessage(), "Document not found for ID [INEXISTENT_ID]");
	}
	
	@Test
	public void missingDataForEvaluation() {
		JsonDocument jsonDoc = new JsonDocument(TEST_DOC_ID);
		Mockito.doReturn(jsonDoc).when(docRepo).findById(Mockito.eq(TEST_DOC_ID));
		
		Throwable exception = ExceptionCatcher
									.doTry(() -> docService.evaluateJsonDocument(TEST_DOC_ID))
									.doCatch();
		
		assertNotNull(exception);
		assertTrue(exception instanceof ChallengeException);
		assertEquals(exception.getMessage(), "Base64 data missing for comparison for document id [" + TEST_DOC_ID + "]");
	}
	
	@Test
	public void successfulEvaluation() {
		JsonDocument jsonDoc = new JsonDocument(TEST_DOC_ID, TEST_DOC_EQUAL_VALUE1, TEST_DOC_EQUAL_VALUE1);
		Mockito.doReturn(jsonDoc).when(docRepo).findById(Mockito.eq(TEST_DOC_ID));
		
		DiffResult diffResult = docService.evaluateJsonDocument(TEST_DOC_ID);
		
		assertEquals(DiffResultType.EQUAL, diffResult.getDiffType());
		assertEquals(DiffResultType.EQUAL.getDescription(), diffResult.getDiffType().getDescription());
	}
	
	@Test
	public void differentOffsetEvaluation() {
		JsonDocument jsonDoc = new JsonDocument(TEST_DOC_ID, TEST_DOC_EQUAL_VALUE1, TEST_DOC_DIFF_VALUE2);
		Mockito.doReturn(jsonDoc).when(docRepo).findById(Mockito.eq(TEST_DOC_ID));
		
		DiffResult diffResult = docService.evaluateJsonDocument(TEST_DOC_ID);
		
		assertEquals(DiffResultType.DIFFERENT_OFFSET, diffResult.getDiffType());
	}
	
	@Test
	public void notSameSizeEvaluation() {
		JsonDocument jsonDoc = new JsonDocument(TEST_DOC_ID, TEST_DOC_EQUAL_VALUE1, TEST_DOC_DIFF_VALUE3);
		Mockito.doReturn(jsonDoc).when(docRepo).findById(Mockito.eq(TEST_DOC_ID));
		
		DiffResult diffResult = docService.evaluateJsonDocument(TEST_DOC_ID);
		
		assertEquals(DiffResultType.NOT_SAME_SIZE, diffResult.getDiffType());
		assertEquals(DiffResultType.NOT_SAME_SIZE.getDescription(), diffResult.getDiffType().getDescription());
	}
	
	
}
