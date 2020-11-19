package vn.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import vn.model.CustomerOrderDetail;

@Repository("customerOrderDetailDao")
@Transactional
public class CustomerOrderDetailDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public CustomerOrderDetail create(CustomerOrderDetail ct) {
		entityManager.persist(ct);
		return ct;
	}

	public CustomerOrderDetail update(CustomerOrderDetail ct) {
		entityManager.merge(ct);
		return ct;
	}

	public void delete(CustomerOrderDetail ct) {
		entityManager.remove(ct);
	}
	
	public CustomerOrderDetail findById(int id) {
		try {
			return entityManager.find(CustomerOrderDetail.class, id);
		} catch (RuntimeException e) {
			throw e;
		}
	}
}
