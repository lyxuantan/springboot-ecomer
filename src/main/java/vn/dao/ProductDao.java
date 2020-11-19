package vn.dao;

import java.util.List;

import vn.model.Product;
import vn.model.ProductJionImage;
import vn.pagination.Ipage;

public interface ProductDao {
	public Product create(Product ct);
	public Product update(Product ct);
	public Product findById(int id);
	public void delete(Product ct);
	public Ipage<Product> listProducts(int currentPage);
	public List<ProductJionImage> productJionImage();
}