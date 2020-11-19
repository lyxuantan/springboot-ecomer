package vn.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import vn.model.CustomerOrder;

@Repository("customerOrderDao")
@Transactional
public class CustomerOrderDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public CustomerOrder create(CustomerOrder ct) {
		entityManager.persist(ct);
		return ct;
	}

	public CustomerOrder update(CustomerOrder ct) {
		entityManager.merge(ct);
		return ct;
	}

	public void delete(CustomerOrder ct) {
		entityManager.remove(ct);
	}
	
	public CustomerOrder findById(int id) {
		try {
			return entityManager.find(CustomerOrder.class, id);
		} catch (RuntimeException e) {
			throw e;
		}
	}
}
