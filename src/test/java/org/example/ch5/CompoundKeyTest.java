package org.example.ch5;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.DatabaseUtils.openSession;
import static org.example.DatabaseUtils.reinitializeDatabase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CompoundKeyTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @AfterEach
    void tearDown() {
//        uninitializeDatabase();
    }

    @Test
    void test1() {
        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(new Ch5ComplexIndexEntity1(new CompoundId1("aaa", "111"), "content1"));

            session.getTransaction().commit();
        }

        try (Session session = openSession()) {
            Ch5ComplexIndexEntity1 loadedEntity = session
                    .byId(Ch5ComplexIndexEntity1.class)
                    .load(new CompoundId1("aaa", "111"));

            assertNotNull(loadedEntity);
            assertEquals("content1", loadedEntity.getContent());
        }
    }

    @Test
    void test2() {
        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(new Ch5ComplexIndexEntity2(new CompoundId2("bbb", "222"), "content2"));

            session.getTransaction().commit();
        }

        try (Session session = openSession()) {
            Ch5ComplexIndexEntity2 loadedEntity = session
                    .byId(Ch5ComplexIndexEntity2.class)
                    .load(new CompoundId2("bbb", "222"));

            assertNotNull(loadedEntity);
            assertEquals("content2", loadedEntity.getContent());
        }
    }

    @Test
    void test3() {
        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(new Ch5ComplexIndexEntity3("ccc", "333", "content3"));

            session.getTransaction().commit();
        }

        try (Session session = openSession()) {
            Ch5ComplexIndexEntity3 loadedEntity = session
                    .byId(Ch5ComplexIndexEntity3.class)
                    .load(new CompoundId3("ccc", "333"));

            assertNotNull(loadedEntity);
            assertEquals("content3", loadedEntity.getContent());
        }
    }
}
