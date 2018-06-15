package br.com.challenge.rhmoreira.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.challenge.rhmoreira.tests.functional.ExceptionCatcher;
import nl.com.waes.rhmoreira.challenge.app.AppStarter;
import nl.com.waes.rhmoreira.challenge.db.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=AppStarter.class, webEnvironment=WebEnvironment.RANDOM_PORT)
public class AuthTestCase extends ChallengeBaseTest{
	
	protected Logger log = LoggerFactory.getLogger(AuthTestCase.class);
	
	@Test
	public void grantedAccess() {
		ResponseEntity<User[]> entity = getForEntity("/private/foo", authUserRestTemplate, User[].class);
		
		User[] users = entity.getBody();
		log.info(users.length + " users found.");
		
		assertTrue(entity.getStatusCode().is2xxSuccessful());
	}
	
	@Test
	public void adminAccess() {
		ResponseEntity<User[]> entity = getForEntity("/private/foo/admin", authAdminUserRestTemplate, User[].class);
		
		User[] users = entity.getBody();
		log.info(users.length + " users found.");
		
		assertTrue(entity.getStatusCode().is2xxSuccessful());
	}
	
	@Test
	public void ungrantedAccess() {
		Throwable exception = 
				ExceptionCatcher
					.doTry(() -> getForEntity("/private/foo/admin", authUserRestTemplate, User[].class))
					.doCatch();
		assertNotNull(exception);
		assertTrue(
			exception instanceof ClientAuthenticationException
			|| exception instanceof OAuth2Exception
		);
	}
	
	@Test
	public void invalidOauthAccess() {
		Throwable exception = 
				ExceptionCatcher
					.doTry(() -> getForEntity("/private/foo", invalidUserRestTemplate, User[].class))
					.doCatch();
		
		assertNotNull(exception);
		assertTrue(
			exception instanceof ClientAuthenticationException
			|| exception instanceof OAuth2Exception
		);
	}
	
	@Test
	public void publicAccess() {
		ResponseEntity<Void> entity = getForEntity("/public/foo", publicRestTemplate, Void.class);
		assertTrue(entity.getStatusCode().is2xxSuccessful());
	}
}
