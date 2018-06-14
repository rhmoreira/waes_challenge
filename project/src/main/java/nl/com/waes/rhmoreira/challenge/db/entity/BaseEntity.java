package nl.com.waes.rhmoreira.challenge.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class BaseEntity<PK extends Serializable> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private PK id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	public PK getId() {
		return id;
	}
	public void setId(PK id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	@PrePersist
	private void updateDate(){
		this.date = new Date();
	}
}
