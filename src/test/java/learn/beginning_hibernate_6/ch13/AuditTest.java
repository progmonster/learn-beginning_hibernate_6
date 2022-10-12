package learn.beginning_hibernate_6.ch13;

import learn.beginning_hibernate_6.JpaUtils;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuditTest {

    @BeforeEach
    void setUp() {
        JpaUtils.reinitializeDatabase();
    }

    @Test
    void testAuditWholeEntity() {
        Ch13Account acc = new Ch13Account("acc", 100);

        JpaUtils.withJPaTransaction(em -> {
            em.persist(acc);
        });

        JpaUtils.withJPaTransaction(em -> {
            Ch13Account accV2 = em.merge(acc);

            accV2.setAmount(200);
        });

        JpaUtils.withJpaEntityManager(em -> {
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

        JpaUtils.withJPaTransaction(em -> {
            em.persist(user);
        });

        JpaUtils.withJPaTransaction(em -> {
            Ch13User userV2 = em.merge(user);

            userV2.setName("Joanna");
            userV2.setAge(30);
        });

        JpaUtils.withJpaEntityManager(em -> {
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
