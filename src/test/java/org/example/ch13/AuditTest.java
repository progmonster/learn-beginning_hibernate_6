package org.example.ch13;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.example.JpaUtils.reinitializeDatabase;
import static org.example.JpaUtils.withJPaTransaction;
import static org.example.JpaUtils.withJpaEntityManager;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AuditTest {

    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    void testAuditWholeEntity() {
        Ch13Account acc = new Ch13Account("acc", 100);

        withJPaTransaction(em -> {
            em.persist(acc);
        });

        withJPaTransaction(em -> {
            Ch13Account accV2 = em.merge(acc);

            accV2.setAmount(200);
        });

        withJpaEntityManager(em -> {
            AuditReader auditReader = AuditReaderFactory.get(em);

            List<Number> revisions = auditReader.getRevisions(Ch13Account.class, acc.getId());

            assertEquals(2, revisions.size());

            Ch13Account accV1 = auditReader.find(Ch13Account.class, acc.getId(), revisions.get(0).intValue());

            Ch13Account accV2 = auditReader.find(Ch13Account.class, acc.getId(), revisions.get(1).intValue());

            assertEquals(100, accV1.getAmount());
            assertEquals(200, accV2.getAmount());
        });
    }

    @Test
    void testAuditCertainProperty() {
        Ch13User user = new Ch13User("John", 20);

        withJPaTransaction(em -> {
            em.persist(user);
        });

        withJPaTransaction(em -> {
            Ch13User userV2 = em.merge(user);

            userV2.setName("Joanna");
            userV2.setAge(30);
        });

        withJpaEntityManager(em -> {
            AuditReader auditReader = AuditReaderFactory.get(em);

            List<Number> revisions = auditReader.getRevisions(Ch13User.class, user.getId());

            assertEquals(2, revisions.size());

            Ch13User userV1 = auditReader.find(Ch13User.class, user.getId(), revisions.get(0).intValue());

            Ch13User userV2 = auditReader.find(Ch13User.class, user.getId(), revisions.get(1).intValue());

            assertEquals("John", userV1.getName());
            // AuditReader didn't set an age value because it isn't under an audit.
            assertEquals(0, userV1.getAge());

            assertEquals("Joanna", userV2.getName());
            // AuditReader didn't set an age value because it isn't under an audit.
            assertEquals(0, userV2.getAge());
        });

    }
}
