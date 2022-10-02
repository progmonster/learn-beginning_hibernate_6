package org.example.ch6;

import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.HibernateUtils.openSession;
import static org.example.HibernateUtils.reinitializeDatabase;

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
