package nl.com.waes.rhmoreira.challenge.app;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import nl.com.waes.rhmoreira.challenge.db.entity.BaseEntity;
import nl.com.waes.rhmoreira.challenge.db.entity.Role;
import nl.com.waes.rhmoreira.challenge.db.entity.User;
import nl.com.waes.rhmoreira.challenge.db.repository.SecurityRespository;

@SpringBootApplication
@EntityScan(basePackageClasses=BaseEntity.class)
@ComponentScan(basePackages="nl.com.waes.rhmoreira.challenge")
@EnableAutoConfiguration
public class AppStarter implements CommandLineRunner {

	@Autowired
	private SecurityRespository secRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public static void main(String[] args) {
		SpringApplication.run(AppStarter.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		//Preload default users
		Role user = new Role();
		user.setRole("CHALLENGE_AUTH");
		user = secRepo.saveRole(user);
		
		Set<Role> roles = new HashSet<>();
		roles.add(user);
		
		User renato = new User();
		renato.setEnabled(true);
		renato.setUsername("rhmoreira");
		renato.setPassword(encoder.encode("1234"));
		renato.setRoles(roles);
		
		secRepo.save(renato);
		
		Role admin = new Role();
		admin.setRole("CHALLENGE_AUTH_ADMIN");
		admin = secRepo.saveRole(admin);
		roles.add(admin);
		
		User pedro = new User();
		pedro.setEnabled(true);
		pedro.setUsername("pedro");
		pedro.setPassword(encoder.encode("1234"));
		pedro.setRoles(roles);
		
		secRepo.save(pedro);
	}
}
