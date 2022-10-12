package learn.beginning_hibernate_6;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.function.Consumer;

public final class HibernateUtils {
    private static SessionFactory factory;

    private HibernateUtils() {
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

    public static void withSession(Consumer<Session> consumer) {
        try (Session session = openSession()) {
            consumer.accept(session);
        }
    }

    public static void withTransaction(Consumer<Session> consumer) {
        try (Session session = openSession()) {
            session.beginTransaction();

            consumer.accept(session);

            session.getTransaction().commit();
        }
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
