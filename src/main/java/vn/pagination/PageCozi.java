package vn.pagination;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PageCozi {
	private int size;
	private int totalElement;
	private int totalPage;
	private int currentPage;
	
	public PageCozi(int size, int totalElement, int totalPage, int currentPage) {
		this.size = size;
		this.totalElement = totalElement;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
	}
}
