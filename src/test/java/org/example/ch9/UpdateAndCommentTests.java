package org.example.ch9;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.HibernateUtils.openSession;
import static org.example.HibernateUtils.reinitializeDatabase;
import static org.example.JpaUtils.getEntityManager;

@Slf4j
public class UpdateAndCommentTests {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    public void testUpdateVersioned() {
        long fooId;

        long fooId2;

        try (Session session = openSession()) {
            session.beginTransaction();

            Ch9Foo foo = new Ch9Foo("Petya");

            session.persist(foo);

            Ch9Foo foo2 = new Ch9Foo("Petya2");

            session.persist(foo2);

            session.getTransaction().commit();

            fooId = foo.getId();
            fooId2 = foo2.getId();
        }

        logPersistedEntity(fooId);
        logPersistedEntity(fooId2);

        try (Session session = openSession()) {
            session.beginTransaction();

            Query query = session.createQuery("update versioned Ch9Foo f set f.name = 'Vasya'");

            query.executeUpdate();

            session.getTransaction().commit();
        }

        logPersistedEntity(fooId);
        logPersistedEntity(fooId2);

        try (Session session = openSession()) {
            session.beginTransaction();

            MutationQuery query = session.createMutationQuery("update Ch9Foo f set f.name = 'Vasya'");

            query.setComment("Update #2");

            query.executeUpdate();

            session.getTransaction().commit();
        }

        logPersistedEntity(fooId);
        logPersistedEntity(fooId2);
    }

    @Test
    public void testUpdateVersionedJpa() {
        long fooId;

        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();

            Ch9Foo foo = new Ch9Foo("Petya");

            em.persist(foo);

            em.getTransaction().commit();

            fooId = foo.getId();
        } finally {
            em.close();
        }

        logPersistedEntity(fooId);

        em = getEntityManager();

        try {
            em.getTransaction().begin();

            // "update versioned" is not supported by JPA. You should update a version manually in your JPQL query
            em.createQuery("update Ch9Foo f set f.name = 'Vasya'");

            em.getTransaction().commit();
        } finally {
            em.close();
        }

        logPersistedEntity(fooId);
    }

    private void logPersistedEntity(long fooId) {
        try (Session session = openSession()) {
            log.info(session.get(Ch9Foo.class, fooId).toString());
        }
    }
}
