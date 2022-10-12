package org.example.ch7;

import jakarta.persistence.EntityManager;
import org.example.JpaUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.JpaUtils.reinitializeDatabase;
import static org.example.JpaUtils.uninitializeDatabase;

public class JpaSessionTest {

    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @AfterEach
    void tearDown() {
        uninitializeDatabase();
    }

    @Test
    public void testJpaSession() {
        EntityManager em = JpaUtils.getJpaEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(new Ch7Role("user"));

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
