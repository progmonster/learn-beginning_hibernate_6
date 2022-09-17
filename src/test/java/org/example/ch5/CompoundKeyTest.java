package org.example.ch5;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.DatabaseUtils.openSession;
import static org.example.DatabaseUtils.reinitializeDatabase;

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

            session.persist(new Ch5ComplexIndexEntity1(new CompoundId1("abc", "123"), "content"));

            session.getTransaction().commit();
        }
    }

    @Test
    void test2() {
        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(new Ch5ComplexIndexEntity2(new CompoundId2("abc", "123"), "content"));

            session.getTransaction().commit();
        }
    }

    @Test
    void test3() {
        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(new Ch5ComplexIndexEntity3("abc", "123", "content"));

            session.getTransaction().commit();
        }
    }
}
