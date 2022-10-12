package learn.beginning_hibernate_6.ch5;

import learn.beginning_hibernate_6.ch6.Ch6ComplexIndexEntity1;
import learn.beginning_hibernate_6.ch6.Ch6ComplexIndexEntity2;
import learn.beginning_hibernate_6.ch6.Ch6ComplexIndexEntity3;
import learn.beginning_hibernate_6.ch6.CompoundId1;
import learn.beginning_hibernate_6.ch6.CompoundId2;
import learn.beginning_hibernate_6.ch6.CompoundId3;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static learn.beginning_hibernate_6.HibernateUtils.openSession;
import static learn.beginning_hibernate_6.HibernateUtils.reinitializeDatabase;
import static learn.beginning_hibernate_6.HibernateUtils.uninitializeDatabase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CompoundKeyTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @AfterEach
    void tearDown() {
        uninitializeDatabase();
    }

    @Test
    void test1() {
        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(new Ch6ComplexIndexEntity1(new CompoundId1("aaa", "111"), "content1"));

            session.getTransaction().commit();
        }

        try (Session session = openSession()) {
            Ch6ComplexIndexEntity1 loadedEntity = session
                    .byId(Ch6ComplexIndexEntity1.class)
                    .load(new CompoundId1("aaa", "111"));

            assertNotNull(loadedEntity);
            assertEquals("content1", loadedEntity.getContent());
        }
    }

    @Test
    void test2() {
        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(new Ch6ComplexIndexEntity2(new CompoundId2("bbb", "222"), "content2"));

            session.getTransaction().commit();
        }

        try (Session session = openSession()) {
            Ch6ComplexIndexEntity2 loadedEntity = session
                    .byId(Ch6ComplexIndexEntity2.class)
                    .load(new CompoundId2("bbb", "222"));

            assertNotNull(loadedEntity);
            assertEquals("content2", loadedEntity.getContent());
        }
    }

    @Test
    void test3() {
        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(new Ch6ComplexIndexEntity3("ccc", "333", "content3"));

            session.getTransaction().commit();
        }

        try (Session session = openSession()) {
            Ch6ComplexIndexEntity3 loadedEntity = session
                    .byId(Ch6ComplexIndexEntity3.class)
                    .load(new CompoundId3("ccc", "333"));

            assertNotNull(loadedEntity);
            assertEquals("content3", loadedEntity.getContent());
        }
    }
}
