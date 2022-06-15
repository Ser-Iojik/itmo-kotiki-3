package dao;

import dao.interfaces.ICatDao;
import entity.Cats;

import lombok.NoArgsConstructor;

import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import util.HibernateUtil;

import java.util.List;


@NoArgsConstructor
public class CatDao implements ICatDao<Cats, Integer> {

    @Override
    public Cats persist(Cats entity) {
        Session session = this.getSession();
        Transaction transaction = this.getTransaction(session);

        session.persist(entity);

        transaction.commit();
        session.close();
        return entity;
    }

    @Override
    public Cats update(Cats entity) {
        Session session = this.getSession();
        Transaction transaction = this.getTransaction(session);

        Cats newEntity = session.merge(entity);

        transaction.commit();
        session.close();
        return newEntity;
    }

    @Override
    public Cats findById(Integer id) {
        Session session = this.getSession();
        Transaction transaction = this.getTransaction(session);

        Cats model = session.get(Cats.class, id);

        transaction.commit();
        session.close();
        return model;
    }

    @Override
    public List<Cats> findAll() {
        Session session = this.getSession();
        Transaction transaction = getTransaction(session);

        HibernateCriteriaBuilder builder = this.getSession().getCriteriaBuilder();
        JpaCriteriaQuery<Cats> criteriaQuery = builder.createQuery(Cats.class);
        JpaRoot<Cats> rootEntry = criteriaQuery.from(Cats.class);
        JpaCriteriaQuery<Cats> all = criteriaQuery.select(rootEntry);

        Query<Cats> allQuery = this.getSession().createQuery(all);
        List<Cats> resultList = allQuery.getResultList();

        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public void delete(Cats entity) {
        Session session = this.getSession();
        Transaction transaction = getTransaction(session);

        session.remove(entity);

        transaction.commit();
        session.close();
    }

    @Override
    public void deleteAll() {
        List<Cats> entityList = findAll();
        for (Cats entity : entityList) {
            delete(entity);
        }
    }

    protected Session getSession() {
        Session session = HibernateUtil.getSessionFactory().openSession();

//        if (session == null)
//            session = HibernateUtil.getSessionFactory().openSession();

        return session;
    }

    protected Transaction getTransaction(Session session) {
        Transaction transaction = session.getTransaction();

        if (!TransactionStatus.ACTIVE.equals(transaction.getStatus()))
            transaction = session.beginTransaction();

        return transaction;
    }
}