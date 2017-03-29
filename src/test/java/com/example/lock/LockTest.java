package com.example.lock;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.example.base.core.CoreServerApp;
import com.example.base.lock.constants.CoordinationLocks;
import com.example.base.lock.service.GenerateLockService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CoreServerApp.class)
@WebAppConfiguration
public class LockTest {

	@Autowired
	private GenerateLockService generateLockService;

	@Test
	public void lock() throws Exception {
		try {
			Integer THREAD_SIZE = 4;
			String key = generateLockService.getKey(CoordinationLocks.STOCK.getCode(), "444");

			for(int i=0; i<THREAD_SIZE; i++) {
				Thread thread = new Thread(new TestRunable(generateLockService, key), "Thread-"+i);
				thread.start();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Thread.sleep(10000);
	}
	
	@Test
	@Ignore
	public void lock2() {
		String key = generateLockService.getKey(CoordinationLocks.STOCK.getCode(), "555");
		System.out.println("key=" + key);
		
		String result = null;
		try {
			result = generateLockService.getNamedLock(key).enter(() -> {
				System.out.println("hi,I'm in lock");
				return "123";
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("result=" + result);
	}


}

class TestRunable implements Runnable {
	private GenerateLockService generateLockService;
	private String key;

	public TestRunable(GenerateLockService generateLockService, String key) {
		this.generateLockService = generateLockService;
		this.key = key;
	}

	@Override
	public void run() {
		String result = null;
		try {
			result = generateLockService.getNamedLock(key).enter(() -> {
				System.out.println("hi,I'm in lock");
				return "123";
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("result=" + result);
	}

}
