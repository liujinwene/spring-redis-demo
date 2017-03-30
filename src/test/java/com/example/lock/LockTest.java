package com.example.lock;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.example.base.core.CoreServerApp;
import com.example.base.lock.constants.CoordinationLocks;
import com.example.base.lock.service.GenerateLockService;
import com.example.base.utils.SpringContextUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CoreServerApp.class)
@WebAppConfiguration
public class LockTest {

	@Resource
	private GenerateLockService generateLockService;

	@Test
	public void lock() throws Exception {
		try {
			Integer THREAD_SIZE = 10;
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
	public void lock2() throws Exception {
		String key = generateLockService.getKey(CoordinationLocks.STOCK.getCode(), "444");
		
		TransactionTemplate transactionTemplate = SpringContextUtil.getBean(TransactionTemplate.class);
		String result = transactionTemplate.execute(new TransactionCallback<String>() {

			@Override
			public String doInTransaction(TransactionStatus status) {
				try {
					return generateLockService.getNamedLock(key).enter(() -> {
						System.out.println("hi,I'm in lock");
						return "success";
					});
				} catch (Exception e) {
					e.printStackTrace();
					return "fail";
				}
			}
		});
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
		TransactionTemplate transactionTemplate = SpringContextUtil.getBean(TransactionTemplate.class);
		String result = transactionTemplate.execute(new TransactionCallback<String>() {

			@Override
			public String doInTransaction(TransactionStatus status) {
				try {
					return generateLockService.getNamedLock(key).enter(() -> {
						System.out.println("hi,I'm in lock");
						return "success";
					});
				} catch (Exception e) {
					e.printStackTrace();
					return "fail";
				}
			}
		});
		System.out.println("result=" + result);
	}

}
