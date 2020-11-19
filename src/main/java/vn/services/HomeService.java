package vn.services;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import vn.data.TinhToan;
import vn.threads.WorkerThreads;

@Service
public class HomeService {
	private String fixStr = "Hello Spring Spring .!";
	
	public String hello() {
		return fixStr;
	}
}
