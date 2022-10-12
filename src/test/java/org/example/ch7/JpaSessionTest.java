package org.example.ch7;

import jakarta.persistence.EntityManager;
import org.example.JpaUtils;
import org.example.ch6.Ch6User;
import org.junit.jupiter.api.Test;

public class JpaSessionTest {
    @Test
    public void testJpaSession() {
        EntityManager em = JpaUtils.getJpaEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(new Ch6User("Don", "Cook"));

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
