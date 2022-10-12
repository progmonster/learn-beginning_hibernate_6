package learn.beginning_hibernate_6.ch7;

import jakarta.persistence.EntityManager;
import learn.beginning_hibernate_6.JpaUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JpaSessionTest {

    @BeforeEach
    void setUp() {
        JpaUtils.reinitializeDatabase();
    }

    @AfterEach
    void tearDown() {
        JpaUtils.uninitializeDatabase();
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
