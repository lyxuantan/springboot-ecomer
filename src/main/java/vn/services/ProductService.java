package vn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.dao.ProductDao;
import vn.model.Product;
import vn.pagination.Ipage;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	public Ipage<Product> getAllProduct(int page) {
		return productDao.listProducts(page);
	}
}
