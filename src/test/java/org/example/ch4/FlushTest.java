package org.example.ch4;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.DatabaseUtils.openSession;
import static org.example.DatabaseUtils.reinitializeDatabase;
import static org.example.DatabaseUtils.uninitializeDatabase;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlushTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    void testIsDirtyAndFlush() {
        SimpleObject obj = new SimpleObject("key1", 123);

        try (Session session = openSession()) {
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
        uninitializeDatabase();
    }
}