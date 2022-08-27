package org.example.ch4;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.DatabaseUtils.openSession;
import static org.example.DatabaseUtils.reinitializeDatabase;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BrokenInversionTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    void testBrokenInversionCode() {
        Message message = new Message("BrokenMessage");

        Email email = new Email("BrokenSubject");

        email.setMessage(message);

        try(Session session = openSession()) {
            session.beginTransaction();

            session.persist(message);
            session.persist(email);

            session.getTransaction().commit();
        }

        assertNotNull(email.getMessage());
        assertNull(message.getEmail());

        try(Session session = openSession()) {
            email = session.get(Email.class, email.getId());
            message = session.get(Message.class, message.getId());

            System.out.println(email);
            System.out.println(message);
        }

        assertNotNull(email.getMessage());
        assertNull(message.getEmail());
    }

    @AfterEach
    void tearDown() {
        //uninitializeDatabase();
    }
}
