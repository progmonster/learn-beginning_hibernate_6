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

public class RefreshTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    void testRefresh() {
        SimpleObject obj = new SimpleObject("key1", 123);

        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(obj);

            session.getTransaction().commit();
        }

        obj.setValue(234);

        try (Session session = openSession()) {
            session.refresh(obj);
        }

        assertEquals(obj.getValue(), 123);

        ValidateSimpleObject.validate(obj.getId(), "key1", 123);
    }

    @AfterEach
    void tearDown() {
        uninitializeDatabase();
    }
}
