package org.example.ch4;

import org.example.ValidateSimpleObject;
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

public class MergeTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    void testMerge() {
        Ch4SimpleObject obj = new Ch4SimpleObject("key1", 123);

        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(obj);

            session.getTransaction().commit();
        }

        obj.setValue(234);

        Ch4SimpleObject mergedObj;

        try (Session session = openSession()) {
            session.beginTransaction();

            mergedObj = session.merge(obj);

            assertEquals(obj, mergedObj);
            assertNotSame(obj, mergedObj);

            assertSame(mergedObj, session.merge(mergedObj));

            session.getTransaction().commit();
        }

        ValidateSimpleObject.validate(obj.getId(), "key1", 234);
    }

    @AfterEach
    void tearDown() {
        uninitializeDatabase();
    }
}
