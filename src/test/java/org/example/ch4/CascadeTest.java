package org.example.ch4;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.DatabaseUtils.openSession;
import static org.example.DatabaseUtils.reinitializeDatabase;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CascadeTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    void testCascadePersist() {
        Email email = new Email("subj1");

        Message message = new Message("text1");

        //email.getMessages().add(message);
        message.setEmail(email);

        try (Session session = openSession()) {
            session.beginTransaction();

            session.persist(message);

            session.getTransaction().commit();
        }

        try (Session session = openSession()) {
            assertAll(
                    () -> assertNotNull(session.get(Email.class, email.getId())),
                    () -> assertNotNull(session.get(Message.class, message.getId()))
            );
        }
    }

    // TODO: 07/09/22 check how types of cascade operations and relation ownership correlate with each other

    @AfterEach
    void tearDown() {
        //uninitializeDatabase();
    }
}
