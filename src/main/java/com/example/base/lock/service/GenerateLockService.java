package com.example.base.lock.service;

import java.util.concurrent.locks.Lock;

import com.example.base.lock.NamedLock;

public interface GenerateLockService {
	Lock getLock(Byte lockType, String key) throws Exception;
	
	NamedLock getNamedLock(String key);
	
	String getKey(String type, String numberNo);

}
