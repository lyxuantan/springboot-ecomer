package vn.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import lombok.Getter;
import vn.model.Product;

public class CardManager {
	
	public static final String KEY_IN_CARD = "SHOPPING_CARD";
	
	@Getter
	private List<CardInfo> items;
	
	private HttpSession httpSession;
	
	@SuppressWarnings({ "unchecked" })
	public CardManager(HttpSession httpSession) {
		this.httpSession = httpSession;
		var listCard = (List<CardInfo>) httpSession.getAttribute(CardManager.KEY_IN_CARD);
		if(listCard != null && !listCard.isEmpty()) {
			items = listCard;
		} else {
			items = new ArrayList<CardInfo>();
		}
	}
	
	public void clearItems() {
		items = new ArrayList<CardInfo>();
		httpSession.setAttribute(CardManager.KEY_IN_CARD, items);
	}
	
	public void addItem(Product product, int quality) {
		for (CardInfo lineItem : items) {
			if(lineItem.getProductId() == product.getId()){
				lineItem.setQuality(lineItem.getQuality() + quality);
				return;
			}
		}
		CardInfo item = CardInfo.builder()
			.productId(product.getId())
			.quality(quality)
			.price(product.getPriceSale())
			.name(product.getName())
			.image(product.getImage()).build();
		this.items.add(item);
		httpSession.setAttribute(CardManager.KEY_IN_CARD, this.items);
	}
	
	public void removeItem(int productId) {
		var newLists = items.stream().filter(item -> {
			return item.getProductId() != productId;
		}).collect(Collectors.toList());
		this.items = newLists;
		httpSession.setAttribute(CardManager.KEY_IN_CARD, this.items);
	}
}
