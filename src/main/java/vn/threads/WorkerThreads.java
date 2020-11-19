package vn.threads;

import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerThreads {
	
	Logger log = LoggerFactory.getLogger(WorkerThreads.class);
	private int poolSize;
	private MyThread[] workers;
	private LinkedBlockingQueue<Runnable> queue;
	
	private static WorkerThreads intance = null;
	public static WorkerThreads getIntance() {
		if(intance == null) {
			intance = new WorkerThreads(5);
		}
		return intance;
	}
	
	public WorkerThreads(int numberOfThread) {
		
		poolSize = numberOfThread;
		queue = new LinkedBlockingQueue<Runnable>();
		workers = new MyThread[numberOfThread];
		
		for(int i = 0; i< numberOfThread; i++) {
			workers[i] = new MyThread("worker-thread-" + i);
			workers[i].start();
		}
		intance = this;
	}
	
	public void excute(Runnable task) {
		synchronized (queue) {
			queue.add(task);
			queue.notify();
		}
	}
	
	private class MyThread extends Thread {
		
		public MyThread(String nameThread) {
			this.setName(nameThread);
		}
		
		@Override
		public void run() {
			Runnable task = null;
			while (true) {
				synchronized (queue) {
					while (queue.isEmpty()) {
						try {
							queue.wait();
						} catch (InterruptedException e) {
							log.error("Loi Trong khi chay thread: {}", e.getMessage());
						}
						task = (Runnable) queue.poll();
					}
				}
				try {
					log.info("Run here task");
					if(task != null) {
						log.info("Run here task1");
						task.run();
					}
				} catch (RuntimeException e) {
					log.error("Loi xu ly thread: {}", e.getMessage());
				}
			}
		}
	}
}
