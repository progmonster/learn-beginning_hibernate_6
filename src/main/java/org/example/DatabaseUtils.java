package org.example;

import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@UtilityClass
public class DatabaseUtils {
    private static SessionFactory factory;

    public void uninitializeDatabase() {
        if (factory != null) {
            factory.close();

            factory = null;
        }
    }

    public void reinitializeDatabase() {
        uninitializeDatabase();
        initializeDatabase();
    }

    public Session openSession() {
        return getFactory().openSession();
    }

    private SessionFactory getFactory() {
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
