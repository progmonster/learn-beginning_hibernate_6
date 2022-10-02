package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public final class JpaUtils {
    private static Map<String, EntityManagerFactory> persistenceUnits = new HashMap<>();

    private JpaUtils() {
        // No-op.
    }

    private static synchronized EntityManager getEntityManager(String persistenceUnitName) {
        persistenceUnits.putIfAbsent(persistenceUnitName, Persistence.createEntityManagerFactory(persistenceUnitName));

        return persistenceUnits
                .get(persistenceUnitName)
                .createEntityManager();
    }

    public static synchronized EntityManager getEntityManager() {
        return getEntityManager("utiljpa");
    }
}
