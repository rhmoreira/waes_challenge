package br.com.challenge.rhmoreira.tests;

import java.util.Collections;

import org.junit.Before;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.web.client.RestTemplate;

import nl.com.waes.rhmoreira.challenge.app.AppStarter;

@SpringBootTest(classes=AppStarter.class, webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class ChallengeBaseTest {
	
	@LocalServerPort
    protected int port;
	public String hostUrl;
	
	protected RestTemplate authUserRestTemplate;
	protected RestTemplate invalidUserRestTemplate;
	protected RestTemplate publicRestTemplate = new RestTemplate();
	
	@Before
	public void setupHost() {
		this.hostUrl = String.format("http://localhost:%d/challenge", port);
	}
	
	@Before
	public void setupValidAuthTemplate() {
		this.authUserRestTemplate = createTemplate("rhmoreira", "1234");
	}
	
	@Before
	public void setupInvalidAuthTemplate() {
		this.invalidUserRestTemplate = createTemplate("rhmoreira", "4321");
	}
	
	private OAuth2RestTemplate createTemplate(String username, String password) {
		ResourceOwnerPasswordResourceDetails rsrcDetails = new ResourceOwnerPasswordResourceDetails();
		rsrcDetails.setAccessTokenUri(String.format("%s/oauth/token", hostUrl));
		
		rsrcDetails.setClientId("client-challenge");
		rsrcDetails.setClientSecret("1234");
		
		rsrcDetails.setUsername(username);
		rsrcDetails.setPassword(password);
		
		rsrcDetails.setGrantType("password");
		
		
		DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();

		OAuth2RestTemplate template = new OAuth2RestTemplate(rsrcDetails, clientContext);
        template.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));
        
        return template;
	}
	
	protected <T> ResponseEntity<T> getForEntity(String url, RestTemplate template, Class<T> responseEntityClass){
		String uri = String.format("%s%s", this.hostUrl, url);
		
		ResponseEntity<T> responseEntity = template.getForEntity(uri, responseEntityClass);
		return responseEntity;
	}
}
