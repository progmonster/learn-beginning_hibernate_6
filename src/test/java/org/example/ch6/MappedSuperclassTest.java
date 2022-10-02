package org.example.ch6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.HibernateUtils.openSession;
import static org.example.HibernateUtils.reinitializeDatabase;

public class MappedSuperclassTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    void testMappedSuperclassAndUpdateCreateTimestamps() throws Exception {
        Ch6DerivedEntity entity;

        try (var session = openSession()) {
            session.beginTransaction();

            entity = new Ch6DerivedEntity("title1");

            session.persist(entity);

            session.getTransaction().commit();
        }

        Thread.sleep(2000);

        try (var session = openSession()) {
            session.beginTransaction();

            entity = session.get(Ch6DerivedEntity.class, entity.getId());

            entity.setTitle("updatedTitle");

            session.getTransaction().commit();
        }
    }
}
