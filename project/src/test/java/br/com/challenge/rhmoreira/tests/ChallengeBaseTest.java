package br.com.challenge.rhmoreira.tests;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import nl.com.waes.rhmoreira.challenge.app.AppStarter;

@SpringBootTest(classes=AppStarter.class, webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class ChallengeBaseTest {
	

}
