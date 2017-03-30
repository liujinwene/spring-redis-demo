package com.example.base.lock.dao;

import java.math.BigInteger;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class LockDaoImpl implements LockDao {
	
	@Resource
	protected SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void clear() {
		getSession().clear();
	}

	@Override
	public Integer getLock(String key, Integer timeout) {
		String format = "SELECT COALESCE(GET_LOCK('%s', %s),0)";
		String sql = String.format(format, key, timeout);
		
		Query query = getSession().createSQLQuery(sql);
		BigInteger result = (BigInteger) query.uniqueResult();
		System.out.println("getLock-result=" + result.intValue());
		return result.intValue();
	}

	@Override
	public Integer releaseLock(String key) {
		String format = "SELECT COALESCE(RELEASE_LOCK('%s'),0);";
		String sql = String.format(format, key);
		
		Query query = getSession().createSQLQuery(sql);
		BigInteger result = (BigInteger) query.uniqueResult();
		return result.intValue();
	}

}
