package vn.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.dao.ProductDao;
import vn.data.RObject;
import vn.data.TinhToan;
import vn.threads.WorkerThreads;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ProductApiController {
	
	private @Autowired ProductDao productDao;
	
	@GetMapping(value="/product")
	public ResponseEntity<?> listProduct(@RequestParam(value="currentPage", required=true, defaultValue="1") Integer currentPage) {
		var tinhtong = new TinhToan(2, 10);
		WorkerThreads.getIntance().excute(tinhtong);
		var product = productDao.listProducts(currentPage - 1);
		return ResponseEntity.ok(RObject.response(product));
	}
	
	@GetMapping(value="/find-product")
	public ResponseEntity<?> findProduct(@RequestParam(value="pid", required=true) Integer pid) {
		var product = productDao.findById(pid);
		return ResponseEntity.ok(RObject.response(product));
	}
}