package com.example.base.lock;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.base.lock.dao.LockDao;
import com.example.base.lock.dao.LockDaoImpl;
import com.example.base.utils.JsonUtil;
import com.example.base.utils.SpringContextUtil;

public class MysqlNamedLock implements NamedLock {
	private static final Logger LOGGER = LoggerFactory.getLogger(MysqlNamedLock.class);
	
	private static final Integer DEFAULT_TIMEOUT = 10;//unit is second
	private String key;
	private Integer timeout;
	
	public MysqlNamedLock(String key) {
		this.key = key;
		this.timeout = DEFAULT_TIMEOUT;
	}
	
	public MysqlNamedLock(String key, Integer timeout) {
		this.key = key;
		this.timeout = timeout;
	}

	@Override
	public <T> T enter(Callable<T> callback) throws Exception {
		LockDao lockDao = SpringContextUtil.getBean(LockDaoImpl.class);
		try {
			lockDao.clear();
			lockDao.getLock(key, timeout);
			T result = callback.call();
			Thread.sleep(2000);
			return result;
		} catch (Exception e) {
			System.err.println("lock execute exception.key=" + key);
			LOGGER.error("lock execute exception.key=" + key);
			throw e;
		} finally {
			lockDao.releaseLock(key);
		}
	}

}
