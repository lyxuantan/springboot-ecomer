package vn.threads;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class ProcessThread extends Thread {
	
	@PostConstruct
	public void init() {
		this.setPriority(10);
		this.setName("process-thread");
		this.start();
	}
	
	public void run() {
		new WorkerThreads(10);
	}
}
