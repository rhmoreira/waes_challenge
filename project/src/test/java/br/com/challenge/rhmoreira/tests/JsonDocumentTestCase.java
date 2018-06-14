package br.com.challenge.rhmoreira.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import nl.com.waes.rhmoreira.challenge.db.nosql.entity.JsonDocument;
import nl.com.waes.rhmoreira.challenge.db.nosql.repository.DocumentRepository;
import nl.com.waes.rhmoreira.challenge.service.B64DocumentServiceImpl;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResult;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResultType;

@RunWith(SpringRunner.class)
public class JsonDocumentTestCase extends ChallengeBaseTest{
	
	protected Logger log = LoggerFactory.getLogger(JsonDocumentTestCase.class);

	@InjectMocks
	private B64DocumentServiceImpl docService;
	
	@Mock
	private DocumentRepository docRepo;

	private static final String TEST_DOC_ID = "ABCDEFGH123";

	private static final String TEST_DOC_EQUAL_VALUE1 = "Y2hhbGxlbmdl";
	private static final String TEST_DOC_DIFF_VALUE2 = "ZWduZWxsYWhj";
	
	@Before
	public void setupMock() {
		JsonDocument jsonDoc = new JsonDocument(TEST_DOC_ID, TEST_DOC_EQUAL_VALUE1, TEST_DOC_EQUAL_VALUE1);
		Mockito.doReturn(jsonDoc).when(docRepo).save(Mockito.any(JsonDocument.class));
		Mockito.doReturn(jsonDoc).when(docRepo).findById(Mockito.eq(TEST_DOC_ID));
	}
	
	@Test
	public void save() {
		JsonDocument jsonDoc = new JsonDocument(TEST_DOC_ID, TEST_DOC_EQUAL_VALUE1, TEST_DOC_EQUAL_VALUE1);
		
		JsonDocument savedDocMock = docService.save(TEST_DOC_ID, TEST_DOC_EQUAL_VALUE1, TEST_DOC_EQUAL_VALUE1);
		JsonDocument docFoundMock = docRepo.findById(TEST_DOC_ID);
		
		assertEquals(savedDocMock.getId(), jsonDoc.getId());
		assertEquals(docFoundMock.getId(), jsonDoc.getId());
	}
	
	@Test
	public void successfulEvaluation() {
		docService.save(TEST_DOC_ID, TEST_DOC_EQUAL_VALUE1, TEST_DOC_EQUAL_VALUE1);
		
		DiffResult diffResult = docService.evaluateJsonDocument(TEST_DOC_ID);
		
		assertEquals(DiffResultType.EQUAL, diffResult.getDiffType());
		assertEquals(DiffResultType.EQUAL.getDescription(), diffResult.getDiffType().getDescription());
	}
	
	@Test
	public void unsuccessfulEvaluation() {
		docService.save(TEST_DOC_ID, TEST_DOC_EQUAL_VALUE1, TEST_DOC_DIFF_VALUE2);
		
		DiffResult diffResult = docService.evaluateJsonDocument(TEST_DOC_ID);
		
		assertNotEquals(DiffResultType.EQUAL, diffResult.getDiffType());
		assertNotEquals(DiffResultType.EQUAL.getDescription(), diffResult.getDiffType().getDescription());
	}
}
