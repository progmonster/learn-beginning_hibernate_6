package learn.beginning_hibernate_6.ch4;

import learn.beginning_hibernate_6.HibernateUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CascadeTest {
    @BeforeEach
    void setUp() {
        HibernateUtils.reinitializeDatabase();
    }

    @Test
    void testCascadePersist() {
        Ch4Email email = new Ch4Email("subj1");

        Ch4Message message = new Ch4Message("text1");

        message.setEmail(email);

        try (Session session = HibernateUtils.openSession()) {
            session.beginTransaction();

            session.persist(message);

            session.getTransaction().commit();
        }

        try (Session session = HibernateUtils.openSession()) {
            assertAll(
                    () -> assertNotNull(session.get(Ch4Email.class, email.getId())),
                    () -> assertNotNull(session.get(Ch4Message.class, message.getId()))
            );
        }
    }

    @AfterEach
    void tearDown() {
        HibernateUtils.uninitializeDatabase();
    }
}
