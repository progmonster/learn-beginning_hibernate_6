package org.example.ch5;

import org.example.ch6.Ch6SplittedEntity;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.HibernateUtils.openSession;
import static org.example.HibernateUtils.reinitializeDatabase;
import static org.example.HibernateUtils.uninitializeDatabase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SplittedEntityTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @AfterEach
    void tearDown() {
        uninitializeDatabase();
    }

    @Test
    void testSplittedEntity() {
        long id;

        try (Session session = openSession()) {
            session.beginTransaction();

            Ch6SplittedEntity entity = new Ch6SplittedEntity(null, "splitted1", "billingPlan1", "address1");

            session.persist(entity);

            session.getTransaction().commit();

            id = entity.getId();
        }

        try (Session session = openSession()) {
            Ch6SplittedEntity loadedEntity = session
                    .byId(Ch6SplittedEntity.class)
                    .load(id);

            assertNotNull(loadedEntity);
            assertEquals("splitted1", loadedEntity.getName());
            assertEquals("billingPlan1", loadedEntity.getBillingPlan());
            assertEquals("address1", loadedEntity.getAddress());
        }
    }
}
