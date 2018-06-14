package nl.com.waes.rhmoreira.challenge.db.entity;

import javax.persistence.Entity;

@Entity
public class EntityTeste extends BaseEntity<Long> {

	private String teste;

	public String getTeste() {
		return teste;
	}
	public void setTeste(String teste) {
		this.teste = teste;
	}
}
