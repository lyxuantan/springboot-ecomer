package vn.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.dao.ProductDao;
import vn.dao.ProductImageDao;
import vn.model.Menu;
import vn.model.Product;
import vn.services.MenuService;

@RestController
@RequestMapping("/api")
public class ProductImageApiController {
	
	Logger log = LoggerFactory.getLogger(ProductImageApiController.class);
	
	@Autowired
	private ProductImageDao productImageDao;
	
	@GetMapping(value="/product-image")
	public String update(
		@RequestParam(name="id") Integer id,
		@RequestParam(name="data") String data,
		@RequestParam(name="value") Integer value
	) {
		log.info("id: {}, data:{}, value: {}", id, data, value);
		var modelImage = productImageDao.findById(id);
		if(modelImage == null)
			return "Not Okie";
		
		if("isPresident".equals(data)) {
			// Reset tat ca hinh dai dien ve 0.
			productImageDao.resetPresident(modelImage.getProductId());
			modelImage.setIsPresident(value);
		} else {
			modelImage.setIsSlider(value);
		}
		productImageDao.update(modelImage);
		return "Okie";
	}
}