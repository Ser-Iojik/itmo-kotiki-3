package dao;

import dao.interfaces.IOwnerDao;
import entity.Cats;
import entity.Owners;

import lombok.NoArgsConstructor;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import util.HibernateUtil;

import java.util.List;


@NoArgsConstructor
public class OwnerDao implements IOwnerDao<Owners, Integer> {

    @Override
    public Owners persist(Owners entity) {
        Session session = this.getSession();
        Transaction transaction = this.getTransaction(session);

        session.persist(entity);

        transaction.commit();
        session.close();
        return entity;
    }

    @Override
    public Owners update(Owners entity) {
        Session session = this.getSession();
        Transaction transaction = this.getTransaction(session);

        Owners newEntity = session.merge(entity);

        transaction.commit();
        session.close();
        return newEntity;
    }

    @Override
    public Owners findById(Integer id) {
        Session session = this.getSession();
        Transaction transaction = this.getTransaction(session);

        Owners model = session.get(Owners.class, id);

        transaction.commit();
        session.close();
        return model;
    }

    @Override
    public List<Owners> findAll() {
        Session session = this.getSession();
        Transaction transaction = getTransaction(session);

        HibernateCriteriaBuilder builder = this.getSession().getCriteriaBuilder();
        JpaCriteriaQuery<Owners> criteriaQuery = builder.createQuery(Owners.class);
        JpaRoot<Owners> rootEntry = criteriaQuery.from(Owners.class);
        JpaCriteriaQuery<Owners> all = criteriaQuery.select(rootEntry);

        Query<Owners> allQuery = this.getSession().createQuery(all);
        List<Owners> resultList = allQuery.getResultList();

        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public void delete(Owners entity) {
        Session session = this.getSession();
        Transaction transaction = getTransaction(session);

        session.remove(entity);

        transaction.commit();
        session.close();    }

    @Override
    public void deleteAll() {
        List<Owners> entityList = findAll();
        for (Owners entity : entityList) {
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
