package vn.dao;

import java.util.List;

import vn.model.Category;

public interface CategoryDao {
	public Category create(Category ct);
	public Category update(Category ct);
	public Category findById(int id);
	public void delete(Category ct);
	public List<Category> allCategory();
}