import entity.Cats;
import org.hibernate.Session;
import util.HibernateUtil;

import java.sql.Date;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        // сразу получаем готовый SessionFactory и сразу создаем сессию
        Session session = HibernateUtil.getSessionFactory().openSession();
//
        session.getTransaction().begin();
//        Cats cats = new Cats();
//
//        cats.setName("Charly");
//        cats.setBirthday(new Date(122, 2, 11));
//        cats.setBreed("Metis");
//        cats.setColor("Red");

//        session.persist(cats); // save db

        session.getTransaction().commit();
        session.close();
    }

}
