package vn.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.aop.BaseUploads;
import vn.dao.PagesDao;
import vn.model.Pages;

@Controller
@RequestMapping("/pages")
public class PagesController extends BaseController {
	
	Logger log = LoggerFactory.getLogger(PagesController.class);
	
	@Autowired PagesDao pagesDao;
	@Autowired private HttpSession httpSession;
	
	@GetMapping(value = "/create")
	public String createPage(Model model) {
		var page = new Pages();
		model.addAttribute("page", page);
		log.info("SId: {}", httpSession.getAttribute("TOI_LA_USE"));
		return "pages/page";
	}
	
	@PostMapping(value = "/create")
	public String savePage(
			@Valid @ModelAttribute(value="page") Pages page,
			BindingResult bindingResult, Model model) {
		
		String VIEW_FILE = "pages/page";
		log.info("page name: {}, content: {}", page.getName(), page.getContent());
		
		if (bindingResult.hasErrors()) {
            return VIEW_FILE;
        }
		
		try {
			String image = BaseUploads.uploadSignFile(page.getImgPresedent(), page);
			page.setImage(image);
		} catch (Exception e) {
			ObjectError error = new ObjectError("file-error", e.getMessage());
			bindingResult.addError(error);
		}
		
		pagesDao.persist(page);
		return VIEW_FILE;
	}
}
