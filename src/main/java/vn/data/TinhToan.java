package vn.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TinhToan implements Runnable {
	
	Logger log = LoggerFactory.getLogger(TinhToan.class);
	private String name;
	private int a;
	private int b;
	
	public TinhToan(int a, int b) {
		this.name = "Tinh-Tong-" + a;
		this.a = a;
		this.b = b;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public void run() {
		log.info("Run Tinh Toan a + b");
	}
}
