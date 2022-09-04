package org.example.ch4;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.example.DatabaseUtils.openSession;
import static org.example.DatabaseUtils.reinitializeDatabase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BrokenInversionTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    void testBrokenInversionCode() {
        Message message = new Message("BrokenMessage");

        Email email = new Email("BrokenSubject");

        message.setEmail(email);

        Message message2 = new Message("BrokenMessage2");

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
            email = session.get(Email.class, email.getId());

            assertNotNull(message.getEmail());
            assertEquals(2, email.getMessages().size());
        }


        try (Session session = openSession()) {
            session.beginTransaction();

            email = session.get(Email.class, email.getId());

            Iterator<Message> messageIter = email.getMessages().iterator();

            messageIter.next();

            messageIter.remove();

            session.getTransaction().commit();
        }

        try (Session session = openSession()) {
            List<Message> messages = session
                    .createQuery("from Message m", Message.class)
                    .list();

            assertEquals(1, messages.size());
        }
    }

    @AfterEach
    void tearDown() {
        //uninitializeDatabase();
    }
}
