package org.example.ch10;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.example.HibernateUtils.reinitializeDatabase;
import static org.example.HibernateUtils.withSession;
import static org.example.HibernateUtils.withTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class FilteringTests {

    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    void testCount() {
        C10User u1 = new C10User("user1", true);

        C10User u2 = new C10User("user2", false);

        C10Form f1 = new C10Form("form1", false);

        C10Form f2 = new C10Form("form2", true);

        withTransaction(session -> {
            session.persist(u1);
            session.persist(u2);
            session.persist(f1);
            session.persist(f2);
        });

        withSession(session -> {
            session
                    .enableFilter("byActive")
                    .setParameter("active", true);

            List<C10User> users = session.createQuery("from C10User", C10User.class).list();

            List<C10Form> forms = session.createQuery("from C10Form", C10Form.class).list();

            assertEquals(List.of(u1), users);
            assertEquals(List.of(f2), forms);
        });
    }
}
