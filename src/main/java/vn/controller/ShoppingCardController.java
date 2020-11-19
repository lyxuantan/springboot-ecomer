package vn.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.dao.ProductDao;
import vn.data.CardManager;
import vn.data.Customer;
import vn.exceptions.InvilidParamsException;
import vn.services.CreateOrderService;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCardController extends BaseController {
	
	Logger log = LoggerFactory.getLogger(ShoppingCardController.class);
	
	@Autowired ProductDao productDao;
	@Autowired private HttpSession httpSession;
	@Autowired private CreateOrderService createOrderService;
	
	@GetMapping(value = "/add")
	public String addToCard(
			@RequestParam int productId,
			@RequestParam int quality
	) {
		var product = productDao.findById(productId);
		if(product == null)
			throw new InvilidParamsException("Không tìm thấy sản phẩm");
		var cardManager = new CardManager(httpSession);
		cardManager.addItem(product, quality);
		return "redirect:/shopping-cart/show";
	}
	
	@GetMapping(value = "/delete")
	public String deleteCard( @RequestParam int productId ) {
		var product = productDao.findById(productId);
		if(product == null)
			throw new InvilidParamsException("Không tìm thấy sản phẩm");
		var cardManager = new CardManager(httpSession);
		cardManager.removeItem(productId);
		return "redirect:/shopping-cart/show";
	}
	
	@GetMapping(value = "/show")
	public String addToCard(Model model) {
		model.addAttribute("listCards", new CardManager(httpSession).getItems() );
		return "pages/cards";
	}
	
	@GetMapping(value = "/create")
	public String info(Model model) {
		model.addAttribute("customer", new Customer() );
		return "pages/card-info";
	}
	
	@PostMapping(value = "/create")
	public String create(
			@Valid @ModelAttribute(value="customer") Customer customer,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
            return "pages/card-info";
        }
		var items = new CardManager(httpSession);
		boolean isCreateSuccess = createOrderService.createOrder(customer, items.getItems());
		if(isCreateSuccess) {
			items.clearItems();
		}
		return "pages/card-info";
	}
}
