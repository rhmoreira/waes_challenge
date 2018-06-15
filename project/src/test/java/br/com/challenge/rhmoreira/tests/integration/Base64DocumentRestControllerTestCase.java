package br.com.challenge.rhmoreira.tests.integration;

import java.util.Base64;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.com.challenge.rhmoreira.tests.ChallengeBaseTest;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResultType;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Base64DocumentRestControllerTestCase extends ChallengeBaseTest{
	
	protected Logger log = LoggerFactory.getLogger(Base64DocumentRestControllerTestCase.class);

	@Autowired
	private MockMvc mvc;
	
	/*
	 * Integration test values
	 */
	private static final String EQUAL_DOC_ID = "1";
	private static final String NOT_EQUAL_DOC_ID = "2";
	
	private static final String DOC_VALUE1 = "aW50ZWdyYXRpb24=";
	private static final String DOC_VALUE2 = "aW50ZWdyYXRpb24";
	private static final String DOC_VALUE3 = "aW50ZWdyPXRpb24=";
	
	private static final String B64_CONTROLLER_BASE_ENDPOINT = "/v1/diff";
	
	/*
	 * OAUTH2 Credentials
	 */
	private String accessToken;
	private static final String USER_NAME = "rhmoreira";
	private static final String USER_PASSWORD = "1234";
	private static final String CLIENTID_PASSWORD = "client-challenge:1234";
	
	@Before
	public void setupAccessToken() throws Exception{
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "password");
	    params.add("username", USER_NAME);
	    params.add("password", USER_PASSWORD);
	 
	    String clientIdPasswordB64 = Base64.getEncoder().encodeToString(CLIENTID_PASSWORD.getBytes());
	    String clientIdPasswordHeaderValue = "Basic " + clientIdPasswordB64;
		
	    MvcResult andResult = mvc.perform(MockMvcRequestBuilders.post("/oauth/token")
	        .params(params)
	        .header("Authorization", clientIdPasswordHeaderValue)
	        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	        .accept(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(MockMvcResultMatchers.status().isOk())
	        .andReturn();
	 
	    String resultString = andResult.getResponse().getContentAsString();
	 
	    JacksonJsonParser jsonParser = new JacksonJsonParser();
	    accessToken = jsonParser.parseMap(resultString).get("access_token").toString();
	}
	
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
					.header("Authorization", "Bearer " + accessToken)
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
					.header("Authorization", "Bearer " + accessToken)
					.accept(MediaType.APPLICATION_JSON_UTF8)
					.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.value.diffType", Matchers.is(expectedDiffType)))
			.andReturn();
	}
}
