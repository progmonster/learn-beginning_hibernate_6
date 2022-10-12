package learn.beginning_hibernate_6.ch6;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static learn.beginning_hibernate_6.HibernateUtils.openSession;
import static learn.beginning_hibernate_6.HibernateUtils.reinitializeDatabase;

class InheritanceTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @AfterEach
    void tearDown() {
        //uninitializeDatabase();
    }

    @Test
    void testSingleInheritance() {
        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(new Ch6SingleFoo(null, "singleBase", "singleFoo"));
            session.persist(new Ch6SingleBoo(null, "singleBase", "singleBoo"));

            session.getTransaction().commit();
        }
    }

    @Test
    void testJoinedInheritance() {
        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(new Ch6JoinedFoo(null, "joinedBase", "joinedFoo"));
            session.persist(new Ch6JoinedBoo(null, "joinedBase", "joinedBoo"));

            session.getTransaction().commit();
        }
    }

    @Test
    void testPerClassInheritance() {
        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(new Ch6PerClassFoo(null, "perClassBase", "perClassFoo"));
            session.persist(new Ch6PerClassBoo(null, "perClassBase", "perClassBoo"));

            session.getTransaction().commit();
        }
    }
}
