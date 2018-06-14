package nl.com.waes.rhmoreira.challenge.web.rest.privatte;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.com.waes.rhmoreira.challenge.db.entity.User;
import nl.com.waes.rhmoreira.challenge.db.repository.SecurityRespository;

@RestController
@RequestMapping(value="/private/foo", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserRestController {
	
	@Autowired
	private SecurityRespository secRepo;

	@GetMapping
	@PreAuthorize("hasAuthority('CHALLENGE_AUTH')")
	public ResponseEntity<List<User>> listUsers(){
		List<User> users = secRepo.list();
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasAuthority('CHALLENGE_AUTH_ADMIN')")
	public ResponseEntity<List<User>> listAdminUsers(){
		List<User> users = secRepo.listAdmin();
		return ResponseEntity.ok(users);
	}
}
