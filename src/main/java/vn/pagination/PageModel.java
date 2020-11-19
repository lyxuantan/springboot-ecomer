package vn.pagination;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PageModel {
	private int buttomToShow = 3;
	private int startPage;
	private int endPage;
	private int currentPage;
	private int totalPage;
	
	public PageModel(int totalPage, int currentPage, int buttomToShow) {
		setButtomToShow(buttomToShow);
		int haftPageToShow = getButtomToShow() / 2;
		if(totalPage <= getButtomToShow()) {
			setStartPage(1);
			setEndPage(totalPage);
		} else if(currentPage - haftPageToShow <= 0) {
			setStartPage(1);
			setEndPage(getButtomToShow());
		} else if (currentPage + haftPageToShow == totalPage) {
			setStartPage(currentPage - haftPageToShow);
			setEndPage(totalPage);
		} else if(currentPage + haftPageToShow > totalPage) {
			setStartPage(totalPage-getButtomToShow() + 1);
			setEndPage(totalPage);
		} else {
			setStartPage(totalPage - haftPageToShow);
			setEndPage(totalPage + haftPageToShow);
		}
		this.currentPage = currentPage;
		this.totalPage = totalPage;
	}
	
	public void setButtomToShow(int buttomToShow) {
		if(buttomToShow % 2 != 0) {
			this.buttomToShow = buttomToShow;
		} else {
			throw new IllegalArgumentException("Số page hiển thị phải là số lẻ");
		}
	}
}
