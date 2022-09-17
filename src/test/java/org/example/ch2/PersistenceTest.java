package org.example.ch2;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.example.DatabaseUtils.openSession;
import static org.example.DatabaseUtils.reinitializeDatabase;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersistenceTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @AfterEach
    void tearDown() {
//        uninitializeDatabase();
    }

    @Test
    void shouldReadMessage() {
        Message savedMessage = saveMessage("Hello, World!");

        List<Message> actualMessages;

        try(Session session = openSession()) {
            actualMessages = session
                    .createQuery("from Ch2Message", Message.class)
                    .list();
        }

        assertEquals(1, actualMessages.size());

        actualMessages.forEach(System.out::println);

        assertEquals(savedMessage, actualMessages.get(0));
    }

    private Message saveMessage(String text) {
        Message message = new Message(text);

        try(Session session = openSession()) {
            Transaction tx = session.beginTransaction();

            session.persist(message);

            tx.commit();
        }

        return message;
    }
}
