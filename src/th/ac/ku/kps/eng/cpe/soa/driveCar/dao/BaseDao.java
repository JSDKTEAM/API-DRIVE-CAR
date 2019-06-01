package th.ac.ku.kps.eng.cpe.soa.driveCar.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import th.ac.ku.kps.eng.cpe.soa.driveCar.model.Rent;

public class BaseDao<T, Id extends Serializable> implements DaoInterface<T, Id> {

	private Class<T> entityClass;

	public BaseDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public boolean persist(T entity) {
		// TODO Auto-generated method stub
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		try {

			if (!tx.isActive())
				tx.begin();

			session.save(entity);

			tx.commit();

			session.close();

		} catch (TransactionException e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		}

		return true;

	}

	public boolean update(Object entity) {
		// TODO Auto-generated method stub
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		try {
			if (!tx.isActive())
				tx.begin();
			session.update(entity);

			tx.commit();

			session.close();
		} catch (TransactionException e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public T findById(Serializable id) {
		// TODO Auto-generated method stub
		Session session = SessionUtil.getSession();
		T entityClass = session.get(this.entityClass, id);
		session.close();
		return entityClass;
	}


	public boolean delete(Object entity) {
		// TODO Auto-generated method stub
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		try {

			if (!tx.isActive())
				tx.begin();

			session.delete(entity);

			tx.commit();

			session.close();

		} catch (TransactionException e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public List<T> findAll() {
		// TODO Auto-generated method stub
		Session session = SessionUtil.getSession();
		try {
			Criteria cr = session.createCriteria(this.entityClass);
			return cr.list();
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	public List<T> findAll(String orderBy, boolean isAsc) {
		// TODO Auto-generated method stub
		Session session = SessionUtil.getSession();
		try {
			Criteria cr = session.createCriteria(this.entityClass);
			if (isAsc) {
				cr.addOrder(Order.asc(orderBy));
			} else {
				cr.addOrder(Order.desc(orderBy));
			}
			return cr.list();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public void deleteAll() {
		// TODO Auto-generated method stub
		List<T> entityList = findAll();
		for (T entity : entityList) {
			delete(entity);
		}
	}

}
