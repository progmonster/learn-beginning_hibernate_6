package org.example.ch4;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.example.HibernateUtils.openSession;
import static org.example.HibernateUtils.reinitializeDatabase;
import static org.example.HibernateUtils.uninitializeDatabase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrphanRemovalTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    void testRemoveOrphan() {
        Ch4Message message = new Ch4Message("BrokenMessage");

        Ch4Email email = new Ch4Email("BrokenSubject");

        message.setEmail(email);

        Ch4Message message2 = new Ch4Message("BrokenMessage2");

        message2.setEmail(email);


        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(message);
            session.persist(message2);
            session.persist(email);

            session.getTransaction().commit();
        }

        assertNotNull(email.getMessages());
        assertNotNull(message.getEmail());

        try (Session session = openSession()) {
            email = session.get(Ch4Email.class, email.getId());

            assertNotNull(message.getEmail());
            assertEquals(2, email.getMessages().size());
        }

        try (Session session = openSession()) {
            session.beginTransaction();

            email = session.get(Ch4Email.class, email.getId());

            Iterator<Ch4Message> messageIter = email.getMessages().iterator();

            messageIter.next();

            messageIter.remove();

            session.getTransaction().commit();
        }

        try (Session session = openSession()) {
            List<Ch4Message> messages = session
                    .createQuery("from Ch4Message m", Ch4Message.class)
                    .list();

            assertEquals(1, messages.size());
        }
    }

    @Test
    void testRemoveOrphans() {
        Ch4Message message = new Ch4Message("BrokenMessage");

        Ch4Email email = new Ch4Email("BrokenSubject");

        message.setEmail(email);

        Ch4Message message2 = new Ch4Message("BrokenMessage2");

        message2.setEmail(email);


        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(message);
            session.persist(message2);
            session.persist(email);

            session.getTransaction().commit();
        }

        assertNotNull(email.getMessages());
        assertNotNull(message.getEmail());

        try (Session session = openSession()) {
            email = session.get(Ch4Email.class, email.getId());

            assertNotNull(message.getEmail());
            assertEquals(2, email.getMessages().size());
        }


        try (Session session = openSession()) {
            session.beginTransaction();

            email = session.get(Ch4Email.class, email.getId());

            session.remove(email);

            session.getTransaction().commit();
        }

        try (Session session = openSession()) {
            List<Ch4Message> messages = session
                    .createQuery("from Ch4Message m", Ch4Message.class)
                    .list();

            assertEquals(0, messages.size());
        }
    }

    @AfterEach
    void tearDown() {
        uninitializeDatabase();
    }
}
