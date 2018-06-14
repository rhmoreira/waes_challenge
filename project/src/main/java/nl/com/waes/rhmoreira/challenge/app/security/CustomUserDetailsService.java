package nl.com.waes.rhmoreira.challenge.app.security;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import nl.com.waes.rhmoreira.challenge.db.entity.Role;
import nl.com.waes.rhmoreira.challenge.db.entity.User;
import nl.com.waes.rhmoreira.challenge.db.repository.SecurityRespository;

/**
 * Spring custom user details, to convert the proprietary credentials to the spring UserDetails interface
 * @author renato.moreira
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private SecurityRespository securityRespository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> userOpt = securityRespository.findUserByUserName(username);
		if (!userOpt.isPresent())
			throw new UsernameNotFoundException("Invalid user");
		
		User user = userOpt.get();
		if (user.getRoles().isEmpty())
			throw new UsernameNotFoundException("Insufficient privileges");
		
		org.springframework.security.core.userdetails.User userDetails = 
				new org.springframework.security.core.userdetails.User(username, user.getPassword(), user.isEnabled(), true, true, true, getAuthorities(user.getRoles()));
		
		return userDetails;
	}

	private Collection<GrantedAuthority> getAuthorities(Set<Role> roles) {
		return 
			roles.stream()
				 .map(Role::getRole)
				 .map(SimpleGrantedAuthority::new)
				 .collect(Collectors.toList());
	}

}
