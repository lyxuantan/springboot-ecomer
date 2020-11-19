package vn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.data.CardInfo;
import vn.data.CardManager;
import vn.model.Menu;
import vn.services.MenuService;

@Controller
public class BaseController {
	
	Logger log = LoggerFactory.getLogger(BaseController.class);
	private @Autowired MenuService mnService;
	private @Autowired HttpSession httpSession;
	
	@ModelAttribute("menus")
	public List<Menu> listMenus() {
		var listMenus = mnService.getMenu();
		return listMenus;
	}
	
	@ModelAttribute("configs")
	public Map<String, String> listConfigs() {
		var configs = new HashMap<String, String>();
		configs.put("hotline", "0987938491");
		return configs;
	}
	
	@SuppressWarnings("unchecked")
	@ModelAttribute("cards")
	public List<CardInfo> cardsList() {
		var listItems = (List<CardInfo>) httpSession.getAttribute(CardManager.KEY_IN_CARD);
		return listItems == null ? new ArrayList<CardInfo>() : listItems;
	}
}
