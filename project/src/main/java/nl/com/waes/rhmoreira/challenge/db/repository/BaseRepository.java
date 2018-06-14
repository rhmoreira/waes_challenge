package nl.com.waes.rhmoreira.challenge.db.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.transaction.annotation.Transactional;

import nl.com.waes.rhmoreira.challenge.db.entity.BaseEntity;

public abstract class BaseRepository<T extends BaseEntity<?>> {

	@PersistenceContext
	protected EntityManager em;	
	
	private Class<T> domainClass;
	
	@SuppressWarnings("unchecked")
	public BaseRepository() {
		 this.domainClass = (Class<T>) ((ParameterizedType) getClass()
                 .getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public List<T> list(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(domainClass);
		query.from(domainClass);
		return em.createQuery(query).getResultList();
	}
	
	public T find(Serializable key){
		return em.find(domainClass, key);
	}
	
	protected <Y> Y getSingleResult(TypedQuery<Y> query){
		try{
			Y result = query.getSingleResult();
			return result;
		}catch (NoResultException e) {
			return null;
		}
	}
	
	@Transactional
	public T save(T entity) {
		return saveEntity(entity);
	}
	
	@Transactional
	public <E extends BaseEntity<?>> E saveEntity(E entity) {
		if (entity.getId() == null)
			this.em.persist(entity);
		else
			entity = this.em.merge(entity);
		
		return entity;
	}
}
