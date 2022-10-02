package org.example.ch4;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.HibernateUtils.openSession;
import static org.example.HibernateUtils.reinitializeDatabase;
import static org.example.HibernateUtils.uninitializeDatabase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

public class PersistTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    void testPersistAndEqualityInSessions() {
        Ch4SimpleObject obj1 = new Ch4SimpleObject();

        Ch4SimpleObject obj2;

        Ch4SimpleObject obj3;

        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(obj1);

            session.getTransaction().commit();

            obj2 = session.get(Ch4SimpleObject.class, obj1.getId());
        }

        assertEquals(obj1, obj2);
        assertSame(obj1, obj2);

        try (Session session = openSession()) {
            obj3 = session.get(Ch4SimpleObject.class, obj1.getId());
        }

        assertEquals(obj1, obj3);
        assertNotSame(obj1, obj3);
    }



    @AfterEach
    void tearDown() {
        uninitializeDatabase();
    }
}
