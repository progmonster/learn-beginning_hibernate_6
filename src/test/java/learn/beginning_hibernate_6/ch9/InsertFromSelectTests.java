package learn.beginning_hibernate_6.ch9;

import learn.beginning_hibernate_6.HibernateUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.MutationQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class InsertFromSelectTests {
    @BeforeEach
    void setUp() {
        HibernateUtils.reinitializeDatabase();
    }

    @Test
    public void testUpdateVersioned() {
        try (Session session = HibernateUtils.openSession()) {
            session.beginTransaction();

            Ch9Foo foo = new Ch9Foo("Petya");

            session.persist(foo);

            session.getTransaction().commit();
        }

        // After each iteration the number of persisted entities is duplicated
        for (int i = 1; i <= 4; i++) {
            try (Session session = HibernateUtils.openSession()) {
                session.beginTransaction();

                MutationQuery query = session.createMutationQuery(
                        "insert into Ch9Foo (name) select f.name from Ch9Foo f"
                );

                query.executeUpdate();

                session.getTransaction().commit();
            }
        }

        try (Session session = HibernateUtils.openSession()) {
            session
                    .createQuery("from Ch9Foo f", Ch9Foo.class)
                    .stream()
                    .map(Object::toString)
                    .forEach(log::info);
        }
    }
}
