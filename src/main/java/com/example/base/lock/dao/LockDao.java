package com.example.base.lock.dao;

import org.hibernate.Session;

public interface LockDao {
	
	void clear();
	Integer getLock(Session session, String key, Integer timeout);
	Integer releaseLock(Session session, String key);
	

}
