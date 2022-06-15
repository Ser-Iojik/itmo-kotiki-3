package util;

import entity.Cats;
import entity.Owners;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static SessionFactory _instance = null;

    private HibernateUtil() {}

    public static SessionFactory getSessionFactory() {
        if (_instance == null) {
            try {
                var configuration = new Configuration().configure();

                configuration.addAnnotatedClass(Cats.class);
                configuration.addAnnotatedClass(Owners.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                _instance = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                System.out.println("Initial session factory failed:\n" + e);
                throw e;
            }
        }
        return _instance;
    }
}