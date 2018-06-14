package nl.com.waes.rhmoreira.challenge.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Passwd encoder config
 * @author renato.moreira
 *
 */
@Configuration
public class PasswordEncoderConfig {

	@Bean
	public PasswordEncoder createPassEncoder() {
		return new BCryptPasswordEncoder();
	}
}
