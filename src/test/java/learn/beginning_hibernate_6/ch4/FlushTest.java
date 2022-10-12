package learn.beginning_hibernate_6.ch4;

import learn.beginning_hibernate_6.HibernateUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlushTest {
    @BeforeEach
    void setUp() {
        HibernateUtils.reinitializeDatabase();
    }

    @Test
    void testIsDirtyAndFlush() {
        Ch4SimpleObject obj = new Ch4SimpleObject("key1", 123);

        try (Session session = HibernateUtils.openSession()) {
            session.beginTransaction();

            session.persist(obj);

            assertTrue(session.isDirty());

            session.flush();

            assertFalse(session.isDirty());

            obj.setValue(345);

            assertTrue(session.isDirty());

            session.getTransaction().commit();

            assertFalse(session.isDirty());
        }
    }

    @AfterEach
    void tearDown() {
        HibernateUtils.uninitializeDatabase();
    }
}
