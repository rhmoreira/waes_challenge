package nl.com.waes.rhmoreira.challenge.db.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Role extends BaseEntity<Long>{

	private String role;
	
	@ManyToMany(mappedBy="roles")
	@JsonBackReference
	private Set<User> users;

	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
