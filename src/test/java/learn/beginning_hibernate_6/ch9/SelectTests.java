package learn.beginning_hibernate_6.ch9;

import jakarta.persistence.NonUniqueResultException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static learn.beginning_hibernate_6.HibernateUtils.openSession;
import static learn.beginning_hibernate_6.HibernateUtils.reinitializeDatabase;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class SelectTests {

    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    void selectSingleField() {
        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(new Ch9Product("Nokia 3110", 1000));

            session.getTransaction().commit();
        }

        try (Session session = openSession()) {
            List<String> productTitles = session
                    .createQuery("select p.title from Ch9Product p", String.class)
                    .list();

            assertEquals(List.of("Nokia 3110"), productTitles);
        }
    }

    @Test
    void selectListOfFields() {
        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(new Ch9Product("Nokia 3110", 1000));

            session.getTransaction().commit();
        }

        try (Session session = openSession()) {
            Object[] productProperties = session
                    .createQuery("select p.title, p.price from Ch9Product p", Object[].class)
                    .list()
                    .get(0);

            assertArrayEquals(new Object[]{"Nokia 3110", 1000}, productProperties);
        }
    }

    @Test
    void getSingleResult() {
        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(new Ch9Product("Nokia 3110", 1000));
            session.persist(new Ch9Product("Google Pixel", 3000));

            session.getTransaction().commit();
        }

        try (Session session = openSession()) {
            Ch9Product nokia = session
                    .createQuery("from Ch9Product p where p.title = 'Nokia 3110'", Ch9Product.class)
                    .getSingleResult();

            assertNotNull(nokia);
        }
    }

    @Test
    void getSingleResult_exceptionWhenNonUnique() {
        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(new Ch9Product("Nokia 3110", 1000));
            session.persist(new Ch9Product("Google Pixel", 3000));

            session.getTransaction().commit();
        }

        try (Session session = openSession()) {
            Query<Ch9Product> query = session.createQuery("from Ch9Product p", Ch9Product.class);

            assertThrows(NonUniqueResultException.class, query::getSingleResult);
        }
    }

    @Test
    void selectCurrentTime() {
        try (Session session = openSession()) {
            LocalTime currentTime = session
                    .createNativeQuery("select current_timestamp() from dual", LocalTime.class)
                    .getSingleResult();

            assertNotNull(currentTime);

            log.info(currentTime.getClass().toString());
            log.info(currentTime.toString());
        }
    }

    @Test
    void limitAndOffset() {
        Ch9Product pixel = new Ch9Product("Google Pixel", 3000);

        Ch9Product fly = new Ch9Product("Fly", 2000);

        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(new Ch9Product("Nokia 3110", 1000));
            session.persist(pixel);
            session.persist(new Ch9Product("Samsung", 4000));
            session.persist(fly);
            session.persist(new Ch9Product("iPhone", 5000));

            session.getTransaction().commit();
        }

        try (Session session = openSession()) {
            List<Ch9Product> result = session
                    .createQuery("from Ch9Product p order by price desc", Ch9Product.class)
                    .setFirstResult(2)
                    .setMaxResults(2)
                    .list();

            assertEquals(2, result.size());
            assertEquals(pixel, result.get(0));
            assertEquals(fly, result.get(1));
        }
    }

    @Test
    void selectFromMultipleTablesIntoDto() {
        Ch9Supplier s1 = new Ch9Supplier("supplier1");

        Ch9Supplier s2 = new Ch9Supplier("supplier2");

        Ch9Supplier s3 = new Ch9Supplier("supplier3");

        Ch9Product p1 = new Ch9Product("product1", 1000);

        Ch9Product p2 = new Ch9Product("product2", 2000);

        Ch9Product p3 = new Ch9Product("product3", 3000);

        Ch9Product p4 = new Ch9Product("product4", 4000);

        Ch9Product p5 = new Ch9Product("product5", 5000);

        try (Session session = openSession()) {
            session.beginTransaction();


            s1.getProducts().add(p1);
            p1.setSupplier(s1);

            s1.getProducts().add(p2);
            p2.setSupplier(s1);

            s2.getProducts().add(p3);
            p3.setSupplier(s2);

            s2.getProducts().add(p4);
            p4.setSupplier(s2);

            s3.getProducts().add(p5);
            p5.setSupplier(s3);

            session.persist(s1);
            session.persist(s2);
            session.persist(s3);

            session.getTransaction().commit();
        }

        try (Session session = openSession()) {
            List<Ch9ProductSupplier> result = session
                    .createQuery(
                            "select new learn.beginning_hibernate_6.ch9.Ch9ProductSupplier(p, s)" +
                                    " from Ch9Supplier s, Ch9Product p where p.supplier = s order by p.title",
                            Ch9ProductSupplier.class
                    )
                    .list();

            assertEquals(5, result.size());
            assertEquals(new Ch9ProductSupplier(p1, s1), result.get(0));
            assertEquals(new Ch9ProductSupplier(p2, s1), result.get(1));
            assertEquals(new Ch9ProductSupplier(p3, s2), result.get(2));
            assertEquals(new Ch9ProductSupplier(p4, s2), result.get(3));
            assertEquals(new Ch9ProductSupplier(p5, s3), result.get(4));
        }
    }
}
