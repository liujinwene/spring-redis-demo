package com.example.base.lock;

import java.util.concurrent.Callable;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.base.lock.dao.LockDao;
import com.example.base.lock.dao.LockDaoImpl;
import com.example.base.utils.SpringContextUtil;

public class MysqlNamedLock implements NamedLock {
	private static final Logger LOGGER = LoggerFactory.getLogger(MysqlNamedLock.class);
	
	private static final Integer DEFAULT_TIMEOUT = 10000;//unit is second
	private String key;
	private Integer timeout;
	private Session session;
	
	public MysqlNamedLock(Session session, String key) {
		this.key = key;
		this.timeout = DEFAULT_TIMEOUT;
		this.session = session;
	}
	
	public MysqlNamedLock(Session session, String key, Integer timeout) {
		this.key = key;
		this.timeout = timeout;
		this.session = session;
	}

	@Override
	public <T> T enter(Callable<T> callback) throws Exception {
		LockDao lockDao = SpringContextUtil.getBean(LockDaoImpl.class);
		try {
			lockDao.getLock(session, key, timeout);
			T result = callback.call();
			return result;
		} catch (Exception e) {
			LOGGER.error("lock execute exception.key=" + key);
			throw e;
		} finally {
			lockDao.releaseLock(session, key);
			session.close();
		}
	}

}
