package learn.beginning_hibernate_6.ch6;

import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static learn.beginning_hibernate_6.HibernateUtils.openSession;
import static learn.beginning_hibernate_6.HibernateUtils.reinitializeDatabase;

public class LobTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    void testLob() {
        try (Session session = openSession()) {
            session.beginTransaction();

            Ch6LobEntity lobEntity = new Ch6LobEntity(
                    "text content",
                    new byte[] {1, 2, 3},
                    new Byte[] {4, 5, 6}
            );

            session.persist(lobEntity);

            session.getTransaction().commit();
        }
    }
}
