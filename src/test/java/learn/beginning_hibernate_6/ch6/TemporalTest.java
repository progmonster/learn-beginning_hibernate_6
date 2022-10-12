package learn.beginning_hibernate_6.ch6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static learn.beginning_hibernate_6.HibernateUtils.openSession;
import static learn.beginning_hibernate_6.HibernateUtils.reinitializeDatabase;

public class TemporalTest {

    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    void testPersistTemporalData() {
        try (var session = openSession()) {
            session.beginTransaction();

            session.persist(new Ch6TemporalEntity());

            session.getTransaction().commit();
        }
    }
}
