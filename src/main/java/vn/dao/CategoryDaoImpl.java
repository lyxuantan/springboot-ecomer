package vn.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.model.Category;
import vn.services.CategoryService;

@Repository("categoryDao")
@Transactional
public class CategoryDaoImpl implements CategoryDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private CategoryService categoryService;
	
	@Override
	public Category create(Category ct) {
		entityManager.persist(ct);
		categoryService.clear();
		return ct;
	}

	@Override
	public Category update(Category ct) {
		entityManager.merge(ct);
		categoryService.clear();
		return ct;
	}

	@Override
	public void delete(Category ct) {
		entityManager.remove(ct);
	}
	
	public List<Category> allCategory() {
		return EntityQuery.create(entityManager, Category.class).list();
	}

	@Override
	public Category findById(int id) {
		try {
			return entityManager.find(Category.class, id);
		} catch (RuntimeException e) {
			throw e;
		}
	}
}
