package learn.beginning_hibernate_6.ch6;

import learn.beginning_hibernate_6.HibernateUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NaturalIdTest {
    @BeforeEach
    void setUp() {
        HibernateUtils.reinitializeDatabase();
    }

    @Test
    void testNaturalIds() {
        try (Session session = HibernateUtils.openSession()) {
            session.beginTransaction();

            Ch6User johnDoe = new Ch6User("John", "Doe", "123");

            session.persist(johnDoe);
            session.persist(new Ch6User("Bob", "Smith", "345"));

            session.getTransaction().commit();
        }

        try (Session session = HibernateUtils.openSession()) {
            var bob = session.bySimpleNaturalId(Ch6User.class).load("345");

            assertEquals("Bob", bob.getFirstName());
            assertEquals("345", bob.getInn());

            var john = session
                    .byNaturalId(Ch6User.class)
                    .using("inn", "123")
                    .getReference();

            assertEquals("John", john.getFirstName());
            assertEquals("123", john.getInn());
        }
    }
}
