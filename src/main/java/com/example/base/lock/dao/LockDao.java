package com.example.base.lock.dao;

public interface LockDao {
	
	void clear();
	Integer getLock(String key, Integer timeout);
	Integer releaseLock(String key);
	

}
