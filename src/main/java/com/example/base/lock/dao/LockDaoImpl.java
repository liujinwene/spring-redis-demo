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
		Session session = sessionFactory.getCurrentSession();
		System.err.println("session-hashCode=" + session.hashCode());
		return session;
	}
	
	@Override
	public void clear() {
		getSession().clear();
	}

	@Override
	public Integer getLock(Session session, String key, Integer timeout) {
		String format = "SELECT COALESCE(GET_LOCK('%s', %s),0)";
		String sql = String.format(format, key, timeout);
		
		printSession(session);
		Query query = session.createSQLQuery(sql);
//		Query query = getSession().createSQLQuery(sql);
		BigInteger result = (BigInteger) query.uniqueResult();
		System.out.println("getLock-result=" + result.intValue());
		return result.intValue();
	}

	@Override
	public Integer releaseLock(Session session, String key) {
		System.out.println("releaseLock");
		
		String format = "SELECT COALESCE(RELEASE_LOCK('%s'),0);";
		String sql = String.format(format, key);
		
		printSession(session);
		Query query = session.createSQLQuery(sql);
//		Query query = getSession().createSQLQuery(sql);
		BigInteger result = (BigInteger) query.uniqueResult();
		return result.intValue();
	}
	private void printSession(Session session) {
		System.err.println("lock-sesion-hashCode=" + session.hashCode());
		
	}

}
