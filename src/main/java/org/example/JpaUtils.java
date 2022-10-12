package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class JpaUtils {
    private static Map<String, EntityManagerFactory> persistenceUnits = new HashMap<>();

    private JpaUtils() {
        // No-op.
    }

    public static synchronized void reinitializeDatabase() {
        uninitializeDatabase();
        getJpaEntityManager("utiljpa");
    }

    public static synchronized void uninitializeDatabase() {
        EntityManagerFactory entityManagerFactory = persistenceUnits.remove("utiljpa");

        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    public static synchronized EntityManager getJpaEntityManager() {
        return getJpaEntityManager("utiljpa");
    }

    public static void withJpaEntityManager(Consumer<EntityManager> consumer) {
        EntityManager entityManager = getJpaEntityManager();

        try {
            consumer.accept(entityManager);
        } finally {
            entityManager.close();
        }
    }

    public static void withJPaTransaction(Consumer<EntityManager> consumer) {
        EntityManager entityManager = getJpaEntityManager();

        try {
            entityManager.getTransaction().begin();

            consumer.accept(entityManager);

            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    private static EntityManager getJpaEntityManager(String persistenceUnitName) {
        var entityManagerFactory = persistenceUnits.computeIfAbsent(
                persistenceUnitName,
                Persistence::createEntityManagerFactory
        );

        return entityManagerFactory.createEntityManager();
    }
}
