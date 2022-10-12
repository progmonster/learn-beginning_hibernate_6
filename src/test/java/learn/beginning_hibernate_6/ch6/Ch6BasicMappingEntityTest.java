package learn.beginning_hibernate_6.ch6;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static learn.beginning_hibernate_6.HibernateUtils.openSession;
import static learn.beginning_hibernate_6.HibernateUtils.reinitializeDatabase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class Ch6BasicMappingEntityTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @AfterEach
    void tearDown() {
//        uninitializeDatabase();
    }

    @Test
    void testPrimitiveTypeMapping_nullableValues() {
        long id;

        try (Session session = openSession()) {
            session.beginTransaction();

            Ch6BasicMappingEntity entity = new Ch6BasicMappingEntity(
                    null, 123, 456, "abc", new SimpleValue("def"), "secret123"
            );

            session.persist(entity);

            session.getTransaction().commit();

            id = entity.getId();
        }

        try (Session session = openSession()) {
            Ch6BasicMappingEntity loadedEntity = session
                    .byId(Ch6BasicMappingEntity.class)
                    .load(id);

            assertNotNull(loadedEntity);
            assertEquals(123, loadedEntity.getNullableIntValue());
            assertEquals(456, loadedEntity.getIntValue());
            assertEquals("abc", loadedEntity.getStringValue());
            assertEquals(new SimpleValue("def"), loadedEntity.getSimpleValue());
            assertNull(loadedEntity.getPrivateKey());
        }
    }
}
