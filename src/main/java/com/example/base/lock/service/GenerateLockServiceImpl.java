package com.example.base.lock.service;

import java.util.concurrent.locks.Lock;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.base.lock.MysqlNamedLock;
import com.example.base.lock.NamedLock;

@Service
public class GenerateLockServiceImpl implements GenerateLockService {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Lock getLock(Byte lockType, String key) throws Exception {
		return null;
	}

	@Override
	public NamedLock getNamedLock(String key) {
		return new MysqlNamedLock(sessionFactory.openSession(), key);
	}

	@Override
	public String getKey(String type, String numberNo) {
		return type + "-" + numberNo;
	}
}
