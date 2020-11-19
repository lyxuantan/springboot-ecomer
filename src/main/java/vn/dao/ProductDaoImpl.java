package vn.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import vn.model.Product;
import vn.model.ProductJionImage;
import vn.pagination.Ipage;

@Repository("productDao")
@Transactional
public class ProductDaoImpl implements ProductDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Product create(Product model) {
		entityManager.persist(model);
		return model;
	}

	@Override
	public Product update(Product model) {
		entityManager.merge(model);
		return model;
	}

	@Override
	public void delete(Product model) {
		entityManager.remove(model);
	}
	
	@Override
	public Ipage<Product> listProducts(int currentPage) {
		int limit = 2;
		var et = EntityQuery.create(entityManager, Product.class)
			.setMaxResults(limit)
			.setFirstResult(currentPage*limit);
		var listItems = et.list();
		int count = Math.toIntExact(et.count());
		Ipage<Product> ipage = new Ipage<Product>(limit, count, currentPage, listItems);
		return ipage;
	}

	@Override
	public Product findById(int id) {
		try {
			return entityManager.find(Product.class, id);
		} catch (RuntimeException e) {
			throw e;
		}
	}

	@Override
	public List<ProductJionImage> productJionImage() {
		String query = "SELECT t1.`name`, t1.title, t2.`file` FROM spring_ecomer.product t1 " + 
				"INNER JOIN spring_ecomer.product_image t2 ON t1.product_id = t2.product_id";
		@SuppressWarnings("unchecked")
		List<ProductJionImage> listResults = entityManager.createNativeQuery(query, ProductJionImage.class)
		.getResultList();
		return listResults;
	}
}
