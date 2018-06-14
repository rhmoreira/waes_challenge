package nl.com.waes.rhmoreira.challenge.db.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.com.waes.rhmoreira.challenge.db.entity.Role;
import nl.com.waes.rhmoreira.challenge.db.entity.User;

@Repository
public class SecurityRespository extends BaseRepository<User> {

	@Transactional
	public Role saveRole(Role role) {
		return super.saveEntity(role);
	}
	
	public Optional<User> findUserByUserName(String username) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> query = cb.createQuery(User.class);
		
		Root<User> from = query.from(User.class);
		from.<User, Role>fetch("roles");
		
		query.where(cb.equal(from.<String>get("username"), username));
		
		return Optional.ofNullable(getSingleResult(em.createQuery(query)));
	}
	
	public List<User> listAdmin(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> query = cb.createQuery(User.class);
		Path<String> pathRoleName = query.from(User.class)
										 .<User, Role>join("roles")
										 .<String>get("role");
		
		query.where(cb.equal(pathRoleName, "CHALLENGE_AUTH_ADMIN"));
		
		return em.createQuery(query).getResultList();
	}
}
