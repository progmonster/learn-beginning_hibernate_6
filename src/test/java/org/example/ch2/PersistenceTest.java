package org.example.ch2;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.example.HibernateUtils.openSession;
import static org.example.HibernateUtils.reinitializeDatabase;
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
        Ch2Message savedMessage = saveMessage("Hello, World!");

        List<Ch2Message> actualMessages;

        try(Session session = openSession()) {
            actualMessages = session
                    .createQuery("from Ch2Message", Ch2Message.class)
                    .list();
        }

        assertEquals(1, actualMessages.size());

        actualMessages.forEach(System.out::println);

        assertEquals(savedMessage, actualMessages.get(0));
    }

    private Ch2Message saveMessage(String text) {
        Ch2Message message = new Ch2Message(text);

        try(Session session = openSession()) {
            Transaction tx = session.beginTransaction();

            session.persist(message);

            tx.commit();
        }

        return message;
    }
}
