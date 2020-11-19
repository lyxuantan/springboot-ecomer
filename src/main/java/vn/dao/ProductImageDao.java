package vn.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import vn.model.ProductImage;

@Repository("productImageDao")
@Transactional
public class ProductImageDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public ProductImage create(ProductImage model) {
		entityManager.persist(model);
		return model;
	}

	public void delete(ProductImage model) {
		entityManager.remove(model);
	}
	
	public void update(ProductImage model) {
		entityManager.merge(model);
	}
	
	public List<ProductImage> listImageByProducts(int productId) {
		return EntityQuery.create(entityManager, ProductImage.class)
			.integerEqualsTo("productId", productId)
			.list();
	}

	public ProductImage findById(int id) {
		try {
			return entityManager.find(ProductImage.class, id);
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	public void resetPresident(int pId) {
		var qe = "update `product_image` set `is_president` =:preSi where `product_id` =:pId";
		entityManager.createNativeQuery(qe)
		.setParameter("preSi", 0)
		.setParameter("pId", pId).executeUpdate();
	}
}
