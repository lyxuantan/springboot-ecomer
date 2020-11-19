package vn.pagination;

import java.util.List;

/*
{
	"embedded": [],
	"page" : {
		"size" : 2,
		"totalElement": 5,
		"totalPage": 3,
		"currentPage": 1
	}
}
*/
public class Ipage <T> {
	public PageCozi page;
	public List<T> embedded;
	private int size;
	private int totalElement;
	private int totalPage;
	private int currentPage;
	public Ipage(int limit, int totalElement, int currentPage, List<T> embedded) {
		this.size = limit;
		this.totalElement = totalElement;
		this.currentPage = currentPage + 1;
		this.embedded = embedded;
		if(totalElement % size == 0) {
			this.totalPage = totalElement/size;
		} else {
			this.totalPage = (totalElement/size) + 1;
		}
		this.setPage();
	}
	
	private void setPage() {
		this.page = new PageCozi(size, totalElement, totalPage, currentPage);
	}
}
