package org.example.ch4;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.DatabaseUtils.openSession;
import static org.example.DatabaseUtils.reinitializeDatabase;
import static org.example.DatabaseUtils.uninitializeDatabase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeleteTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    void testDelete() {
        Ch4SimpleObject obj1 = new Ch4SimpleObject("key1", 123);

        Ch4SimpleObject obj2 = new Ch4SimpleObject("key2", 234);

        Ch4SimpleObject obj3 = new Ch4SimpleObject("key3", 345);

        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(obj1);
            session.persist(obj2);
            session.persist(obj3);

            session.getTransaction().commit();
        }

        try (Session session = openSession()) {
            session.beginTransaction();

            session.remove(obj1);

            session.getTransaction().commit();

            assertNull(session.get(Ch4SimpleObject.class, obj1.getId()));
        }

        try (Session session = openSession()) {
            session.beginTransaction();

            int removedAmount = session
                    .createMutationQuery("delete from Ch4SimpleObject so")
                    .executeUpdate();

            session.getTransaction().commit();

            assertEquals(2, removedAmount);

            assertEquals(
                    0,
                    session.createQuery("from Ch4SimpleObject so", Ch4SimpleObject.class).list().size()
            );
        }
    }

    @AfterEach
    void tearDown() {
        uninitializeDatabase();
    }
}
