package learn.beginning_hibernate_6.ch6;

import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static learn.beginning_hibernate_6.HibernateUtils.openSession;
import static learn.beginning_hibernate_6.HibernateUtils.reinitializeDatabase;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NamedQueryTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    void testNamedQuery() {
        Ch6User johnDoe;

        try (Session session = openSession()) {
            session.beginTransaction();

            johnDoe = new Ch6User("John", "Doe");

            session.persist(johnDoe);
            session.persist(new Ch6User("Bob", "Smith"));

            session.getTransaction().commit();
        }

        try (Session session = openSession()) {
            List<Ch6User> result = session
                    .createNamedQuery("findByLastNameAsc", Ch6User.class)
                    .setParameter("lastName", "Doe")
                    .list();

            assertEquals(1, result.size());
            assertEquals(johnDoe, result.get(0));
        }
    }
}
