package org.example.ch4;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.DatabaseUtils.openSession;
import static org.example.DatabaseUtils.reinitializeDatabase;
import static org.example.DatabaseUtils.uninitializeDatabase;
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
        SimpleObject obj1 = new SimpleObject();

        SimpleObject obj2;

        SimpleObject obj3;

        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(obj1);

            session.getTransaction().commit();

            obj2 = session.get(SimpleObject.class, obj1.getId());
        }

        assertEquals(obj1, obj2);
        assertSame(obj1, obj2);

        try (Session session = openSession()) {
            obj3 = session.get(SimpleObject.class, obj1.getId());
        }

        assertEquals(obj1, obj3);
        assertNotSame(obj1, obj3);
    }



    @AfterEach
    void tearDown() {
        uninitializeDatabase();
    }
}
