package vn.admin;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.aop.BaseUploads;
import vn.controller.BaseController;
import vn.dao.CategoryDao;
import vn.dao.ProductDao;
import vn.dao.ProductImageDao;
import vn.model.Category;
import vn.model.Product;
import vn.model.ProductImage;
import vn.pagination.PageModel;
import vn.services.CategoryService;
import vn.services.ProductService;

@Controller
@RequestMapping("admin/product")
public class AdminProductController extends BaseController {
	
	private Logger log = LoggerFactory.getLogger(AdminProductController.class);
	@Autowired ProductService productService;
	@Autowired CategoryService categoryService;
	@Autowired ProductDao productDao;
	@Autowired ProductImageDao productImageDao;
	
	private final int BUTTOM_TO_SHOW = 3;
	
	@GetMapping(value = {"", "/"})
	public String getProducts(
			@RequestParam(value="currentPage", required=true, defaultValue="1") Integer currentPage,
			Model model) {
		var pageIndex = currentPage - 1;
		var ipage = productService.getAllProduct(pageIndex);
		PageModel pageModel = new PageModel(ipage.page.getTotalPage(), pageIndex, BUTTOM_TO_SHOW);
		model.addAttribute("listProduct", ipage.embedded);
		model.addAttribute("pager", pageModel);
		return "admin/product/index";
	}
	
	@GetMapping(value = "/create")
	public String createProduct(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("listCategory", categoryService.categoryWithIndent());
		return "admin/product/form";
	}
	
	@PostMapping(value = "/create")
	public String productForm(@Valid @ModelAttribute(value="product") Product product, 
			BindingResult bindingResult,  Model model) {
		
		model.addAttribute("listImages", new ArrayList<>());
		if(bindingResult.hasErrors()) {
			model.addAttribute("product", product);
			model.addAttribute("listCategory", categoryService.categoryWithIndent());
			return "admin/product/form";
		}
		var productAfterSave = productDao.create(product);
		if(product.multipartFile != null) {
			product.multipartFile.forEach(multipart -> {
				try {
					String file = BaseUploads.uploadSignFile(multipart, product);
					var modelImage = ProductImage.builder()
						.file(file)
						.productId(productAfterSave.getId()).build();
					modelImage.revertSlider();
					modelImage.revertPresident();
					productImageDao.create(modelImage);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			});
		}
		
		return "redirect:/admin/product";
	}
	
	@GetMapping(value = "/update")
	public String updateProduct(
			@RequestParam Integer id,
			Model model) {
		var product = productDao.findById(id);
		if(product == null) {
			throw new RuntimeException("Không có record id: " + id);
		}
		List<ProductImage> listImages = product.getProductImages();
		model.addAttribute("product", product);
		model.addAttribute("listImages", listImages);
		model.addAttribute("listCategory", categoryService.categoryWithIndent());
		return "admin/product/form";
	}
	
	@PostMapping(value = "/update")
	public String categoryFormUpdate(
			@Valid 
			@ModelAttribute(value="product") Product product, 
			BindingResult bindingResult,
			@RequestParam Integer id,
			Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("product", product);
			model.addAttribute("listCategory", categoryService.categoryWithIndent());
			return "admin/product/form";
		}
		var prodInDb = productDao.findById(id);
		if(prodInDb == null) {
			throw new RuntimeException("Không có record id: " + id);
		}
		
		BeanUtils.copyProperties(product, prodInDb, new String[] {"createTime", "updateTime"});
		productDao.update(prodInDb);
		return "redirect:/admin/product";
	}
}