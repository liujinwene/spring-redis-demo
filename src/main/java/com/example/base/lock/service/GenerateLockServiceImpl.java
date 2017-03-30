package com.example.base.lock.service;

import java.util.concurrent.locks.Lock;

import org.springframework.stereotype.Service;

import com.example.base.lock.MysqlNamedLock;
import com.example.base.lock.NamedLock;

@Service
public class GenerateLockServiceImpl implements GenerateLockService {
	@Override
	public Lock getLock(Byte lockType, String key) throws Exception {
		return null;
	}

	@Override
	public NamedLock getNamedLock(String key) {
		return new MysqlNamedLock(key);
	}

	@Override
	public String getKey(String type, String numberNo) {
		return type + "-" + numberNo;
	}
}
