package dao;

import dao.interfaces.ICatDao;
import dao.interfaces.IFriendsDao;
import entity.Cats;
import entity.Friends;

import jakarta.persistence.Basic;
import lombok.NoArgsConstructor;

import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import util.HibernateUtil;

import java.util.*;


@NoArgsConstructor
public class FriendsDao implements IFriendsDao<Friends, Integer> {

    @Override
    public Friends persist(Friends entity) {
        Session session = this.getSession();
        Transaction transaction = this.getTransaction(session);

        session.persist(entity);

        transaction.commit();
        session.close();
        return entity;
    }

    @Override
    public Friends update(Friends entity) {
        Session session = this.getSession();
        Transaction transaction = this.getTransaction(session);

        Friends newEntity = session.merge(entity);

        transaction.commit();
        session.close();
        return newEntity;
    }

    @Override
    public Friends findById(Integer id) {
        Session session = this.getSession();
        Transaction transaction = this.getTransaction(session);

        Friends model = session.get(Friends.class, id);

        transaction.commit();
        session.close();
        return model;
    }

    @Override
    public List<Friends> findAll() {
        Session session = this.getSession();
        Transaction transaction = getTransaction(session);

        HibernateCriteriaBuilder builder = this.getSession().getCriteriaBuilder();
        JpaCriteriaQuery<Friends> criteriaQuery = builder.createQuery(Friends.class);
        JpaRoot<Friends> rootEntry = criteriaQuery.from(Friends.class);
        JpaCriteriaQuery<Friends> all = criteriaQuery.select(rootEntry);

        Query<Friends> allQuery = this.getSession().createQuery(all);
        List<Friends> resultList = allQuery.getResultList();

        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public void delete(Friends entity) {
        Session session = this.getSession();
        Transaction transaction = getTransaction(session);

        session.remove(entity);

        transaction.commit();
        session.close();
    }

    @Override
    public void deleteAll() {
        List<Friends> entityList = findAll();
        for (Friends entity : entityList) {
            delete(entity);
        }
    }

    protected Session getSession() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        if (session == null)
            session = HibernateUtil.getSessionFactory().openSession();

        return session;
    }

    protected Transaction getTransaction(Session session) {
        Transaction transaction = session.getTransaction();

        if (!TransactionStatus.ACTIVE.equals(transaction.getStatus()))
            transaction = session.beginTransaction();

        return transaction;
    }
}