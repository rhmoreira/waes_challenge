package nl.com.waes.rhmoreira.challenge.web.rest.publlic;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/public/foo", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ChallengePublicRestController {
	
	@GetMapping
	public ResponseEntity<Void> listUsers(){
		return ResponseEntity.ok().build();
	}
}
