package br.com.challenge.rhmoreira.tests.integration;

import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.challenge.rhmoreira.tests.ChallengeBaseTest;
import nl.com.waes.rhmoreira.challenge.app.AppStarter;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResultType;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=AppStarter.class, webEnvironment=WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Base64DocumentRestControllerTestCase extends ChallengeBaseTest{
	
	protected Logger log = LoggerFactory.getLogger(Base64DocumentRestControllerTestCase.class);

	@Autowired
	private MockMvc mvc;
	
	private static final String EQUAL_DOC_ID = "1";
	private static final String NOT_EQUAL_DOC_ID = "2";
	
	private static final String DOC_VALUE1 = "aW50ZWdyYXRpb24=";
	private static final String DOC_VALUE2 = "aW50ZWdyYXRpb24";
	private static final String DOC_VALUE3 = "aW50ZWdyPXRpb24=";
	
	private static final String B64_CONTROLLER_BASE_ENDPOINT = "/v1/diff";
	
	@Test
	public void test1ASaveEqualDocument() throws Exception{
		String jsonContent = String.format("{ \"value\": \"%s\"}", DOC_VALUE1);
		
		String leftValueEndpoint = String.format("%s/%s/left", B64_CONTROLLER_BASE_ENDPOINT, EQUAL_DOC_ID);
		String rightValueEndpoint = String.format("%s/%s/right", B64_CONTROLLER_BASE_ENDPOINT, EQUAL_DOC_ID);
		
		save(leftValueEndpoint, jsonContent);
		save(rightValueEndpoint, jsonContent);
	}
	
	@Test
	public void test1BEvaluateEqual() throws Exception{
		String evaluationEndpoint = B64_CONTROLLER_BASE_ENDPOINT + "/" + EQUAL_DOC_ID;
		evaluate(evaluationEndpoint, DiffResultType.EQUAL.name());
	}

	@Test
	public void test2ASaveNotEqualNotSameSizeDocument() throws Exception{
		String leftValueEndpoint = String.format("%s/%s/left", B64_CONTROLLER_BASE_ENDPOINT, NOT_EQUAL_DOC_ID);
		String rightValueEndpoint = String.format("%s/%s/right", B64_CONTROLLER_BASE_ENDPOINT, NOT_EQUAL_DOC_ID);

		String jsonContent = String.format("{ \"value\": \"%s\"}", DOC_VALUE1);
		save(leftValueEndpoint, jsonContent);
		
		jsonContent = String.format("{ \"value\": \"%s\"}", DOC_VALUE2);
		save(rightValueEndpoint, jsonContent);
	}
	
	@Test
	public void test2BEvaluateNotEqualNotSameSizeDocument() throws Exception{
		String evaluationEndpoint = B64_CONTROLLER_BASE_ENDPOINT + "/" + NOT_EQUAL_DOC_ID;
		evaluate(evaluationEndpoint, DiffResultType.NOT_SAME_SIZE.name());
	}
	
	@Test
	public void test3ASaveNotEqualDiffOffsetDocument() throws Exception{
		String leftValueEndpoint = String.format("%s/%s/left", B64_CONTROLLER_BASE_ENDPOINT, NOT_EQUAL_DOC_ID);
		String rightValueEndpoint = String.format("%s/%s/right", B64_CONTROLLER_BASE_ENDPOINT, NOT_EQUAL_DOC_ID);

		String jsonContent = String.format("{ \"value\": \"%s\"}", DOC_VALUE1);
		save(leftValueEndpoint, jsonContent);
		
		jsonContent = String.format("{ \"value\": \"%s\"}", DOC_VALUE3);
		save(rightValueEndpoint, jsonContent);
	}
	
	@Test
	public void test3BEvaluateNotEqualDiffOffsetDocument() throws Exception{
		String evaluationEndpoint = B64_CONTROLLER_BASE_ENDPOINT + "/" + NOT_EQUAL_DOC_ID;
		evaluate(evaluationEndpoint, DiffResultType.DIFFERENT_OFFSET.name());
	}
	
	private void save(String endpoint, String content) throws Exception{
		mvc.perform(
				MockMvcRequestBuilders
					.post(endpoint)
					.accept(MediaType.APPLICATION_JSON_UTF8)
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(content))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
	}
	
	private void evaluate(String evaluationEndpoint, String expectedDiffType) throws Exception {
		mvc.perform(
				MockMvcRequestBuilders
					.get(evaluationEndpoint)
					.accept(MediaType.APPLICATION_JSON_UTF8)
					.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.value.diffType", Matchers.is(expectedDiffType)))
			.andReturn();
	}
}
