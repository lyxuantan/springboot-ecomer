package vn.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.dao.CategoryDao;
import vn.model.Category;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryDao categoryDao;
	
	private static CategoryService intance;
	
	@PostConstruct
	public void initProduct() {
		intance = this;
	}
	
	public static CategoryService getIntance() {
		return intance;
	}
	
	public List<Category> getAllCategory() {
		return categoryDao.allCategory();
	}
	
	private List<Category> resultIndent = new ArrayList<Category>();
	public List<Category> arrayWithIndent(List<Category> listCategory, int parentId, String indent) {
		for(Category category : listCategory) {
			if(category.getParentId().equals(parentId)) {
				var tmpCate = new Category();
				BeanUtils.copyProperties(category, tmpCate);
				tmpCate.setName(indent + " " + category.getName());
				resultIndent.add(tmpCate);
				this.arrayWithIndent(listCategory, category.getId(), "-");
			}
		}
		return resultIndent;
	}
	
	/*
	 * Điện thoại
	 * - Iphone
	 * -- Iphone X
	 * -- Iphone XS
	 * - SamSung
	 * -- Galaxy
	 * */
	public List<Category> categoryWithIndent() {
		if(resultIndent.size() > 0)
			return resultIndent;
		return arrayWithIndent(getAllCategory(), 0, "");
	}
	
	public void clear () {
		resultIndent.clear();
	}
}
