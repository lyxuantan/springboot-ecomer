package vn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import vn.dao.ProductDao;

@Controller
public class ProductController extends BaseController {
	
	@Autowired private ProductDao productDao;
	
	@GetMapping(value = "/{title}-i{id}")
	public String productInfo(
			@PathVariable("id") Integer id,
			Model model
	) {
		var modelProduct = productDao.findById(id);
		model.addAttribute("listImages", modelProduct.getProductImages());
		model.addAttribute("product", modelProduct);
		return "pages/product";
	}
}
