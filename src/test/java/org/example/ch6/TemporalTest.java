package org.example.ch6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.HibernateUtils.openSession;
import static org.example.HibernateUtils.reinitializeDatabase;

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
