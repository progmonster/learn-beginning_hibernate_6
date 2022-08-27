package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public final class DatabaseUtils {
    private static SessionFactory factory;

    private DatabaseUtils() {
        // No-op.
    }

    public static void uninitializeDatabase() {
        if (factory != null) {
            factory.close();

            factory = null;
        }
    }

    public static void reinitializeDatabase() {
        uninitializeDatabase();
        initializeDatabase();
    }

    public static Session openSession() {
        return getFactory().openSession();
    }

    private static SessionFactory getFactory() {
        if (factory == null) {
            initializeDatabase();
        }

        return factory;
    }

    private static void initializeDatabase() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        factory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }
}
