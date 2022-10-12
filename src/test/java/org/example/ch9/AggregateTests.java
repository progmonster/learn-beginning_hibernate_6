package org.example.ch9;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.example.HibernateUtils.reinitializeDatabase;
import static org.example.HibernateUtils.withSession;
import static org.example.HibernateUtils.withTransaction;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class AggregateTests {

    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    void testCount() {
        Ch9Supplier s1 = new Ch9Supplier("supplier1");

        Ch9Supplier s2 = new Ch9Supplier("supplier2");

        Ch9Supplier s3 = new Ch9Supplier("supplier3");

        Ch9Product p1 = new Ch9Product("product1", 1000);

        Ch9Product p2 = new Ch9Product("product2", 2000);

        Ch9Product p3 = new Ch9Product("product3", 3000);

        Ch9Product p4 = new Ch9Product("product4", 4000);

        Ch9Product p5 = new Ch9Product("product5", 5000);

        withTransaction(session -> {
            s1.getProducts().add(p1);
            p1.setSupplier(s1);

            s1.getProducts().add(p2);
            p2.setSupplier(s1);

            s2.getProducts().add(p3);
            p3.setSupplier(s2);

            s2.getProducts().add(p4);
            p4.setSupplier(s2);

            s3.getProducts().add(p5);
            p5.setSupplier(s3);

            session.persist(s1);
            session.persist(s2);
            session.persist(s3);
        });

        withSession(session -> {
            long actualCount = session
                    .createQuery("select count(*) from Ch9Product", long.class)
                    .getSingleResult();

            assertEquals(5, actualCount);
        });
    }

    @Test
    void testCountCertainProperty() {
        Ch9Supplier s1 = new Ch9Supplier("supplier1");

        Ch9Supplier s2 = new Ch9Supplier("supplier2");

        Ch9Supplier s3 = new Ch9Supplier("supplier3");

        Ch9Product p1 = new Ch9Product("product1", 1000);

        Ch9Product p2 = new Ch9Product("product2", 2000);

        Ch9Product p3 = new Ch9Product("product3", 3000);

        Ch9Product p4 = new Ch9Product("product4", 4000);

        Ch9Product p5 = new Ch9Product("product5", 5000);

        p5.setRating(null);

        withTransaction(session -> {
            s1.getProducts().add(p1);
            p1.setSupplier(s1);

            s1.getProducts().add(p2);
            p2.setSupplier(s1);

            s2.getProducts().add(p3);
            p3.setSupplier(s2);

            s2.getProducts().add(p4);
            p4.setSupplier(s2);

            // product #5 is stored without being linked to any supplier.
            session.persist(p5);

            session.persist(s1);
            session.persist(s2);
            session.persist(s3);
        });

        withSession(session -> {
            long actualProductTitlesCount = session
                    .createQuery("select count(p.title) from Ch9Product p", long.class)
                    .getSingleResult();

            long actualProductTimestampsCount = session
                    .createQuery("select count(p.rating) from Ch9Product p", long.class)
                    .getSingleResult();

            long actualRelatedSuppliersCount = session
                    .createQuery("select count(p.supplier) from Ch9Product p", long.class)
                    .getSingleResult();

            long actualRelatedSupplierTitlesCount = session
                    .createQuery("select count(p.supplier.title) from Ch9Product p", long.class)
                    .getSingleResult();

            assertEquals(5, actualProductTitlesCount);
            assertEquals(4, actualProductTimestampsCount);
            assertEquals(4, actualRelatedSuppliersCount);
            assertEquals(4, actualRelatedSupplierTitlesCount);
        });
    }

    @Test
    void testDistinctCount() {
        Ch9Supplier s1 = new Ch9Supplier("supplier1");

        Ch9Supplier s2 = new Ch9Supplier("supplier2");

        Ch9Supplier s3 = new Ch9Supplier("supplier3");

        Ch9Product p1 = new Ch9Product("product1", 1000);

        Ch9Product p2 = new Ch9Product("product2", 2000);

        Ch9Product p3 = new Ch9Product("product3", 3000);

        Ch9Product p4 = new Ch9Product("product4", 4000);

        Ch9Product p5 = new Ch9Product("product5", 5000);

        withTransaction(session -> {
            s1.getProducts().add(p1);
            p1.setSupplier(s1);

            s1.getProducts().add(p2);
            p2.setSupplier(s1);

            s2.getProducts().add(p3);
            p3.setSupplier(s2);

            s2.getProducts().add(p4);
            p4.setSupplier(s2);

            s3.getProducts().add(p5);
            p5.setSupplier(s3);

            session.persist(s1);
            session.persist(s2);
            session.persist(s3);
        });

        withSession(session -> {
            long actualUniqueRelatedSupplierTitlesCount = session
                    .createQuery("select count(distinct p.supplier.title) from Ch9Product p", long.class)
                    .getSingleResult();

            assertEquals(3, actualUniqueRelatedSupplierTitlesCount);
        });
    }

    @Test
    void testAvg() {
        Ch9Product p1 = new Ch9Product("product1", 1000);

        Ch9Product p2 = new Ch9Product("product2", 2000);

        Ch9Product p3 = new Ch9Product("product3", 3000);

        Ch9Product p4 = new Ch9Product("product4", 4000);

        Ch9Product p5 = new Ch9Product("product5", 5000);

        withTransaction(session -> {
            session.persist(p1);
            session.persist(p2);
            session.persist(p3);
            session.persist(p4);
            session.persist(p5);
        });

        withSession(session -> {
            double actualAverage = session
                    .createQuery("select avg(price) from Ch9Product", double.class)
                    .getSingleResult();

            assertEquals(3000.0, actualAverage);
        });
    }

    @Test
    void testMinMaxIntoRelatedTable() {
        Ch9Supplier s1 = new Ch9Supplier("supplier1", 3);

        Ch9Supplier s2 = new Ch9Supplier("supplier2", 11);

        Ch9Supplier s3 = new Ch9Supplier("supplier3", 7);

        Ch9Product p1 = new Ch9Product("product1", 1000);

        Ch9Product p2 = new Ch9Product("product2", 2000);

        Ch9Product p3 = new Ch9Product("product3", 3000);

        Ch9Product p4 = new Ch9Product("product4", 4000);

        Ch9Product p5 = new Ch9Product("product5", 5000);

        withTransaction(session -> {
            s1.getProducts().add(p1);
            p1.setSupplier(s1);

            s1.getProducts().add(p2);
            p2.setSupplier(s1);

            s2.getProducts().add(p3);
            p3.setSupplier(s2);

            s2.getProducts().add(p4);
            p4.setSupplier(s2);

            s3.getProducts().add(p5);
            p5.setSupplier(s3);

            session.persist(s1);
            session.persist(s2);
            session.persist(s3);
        });

        withSession(session -> {
            Object[] actualValues = session
                    .createQuery("select min(p.supplier.rank), max(p.supplier.rank) from Ch9Product p", Object[].class)
                    .getSingleResult();

            assertEquals(3, actualValues[0]);
            assertEquals(11, actualValues[1]);
        });
    }

    @Test
    void testGroupBy() {
        Ch9Supplier s1 = new Ch9Supplier("supplier1");

        Ch9Supplier s2 = new Ch9Supplier("supplier2");

        Ch9Supplier s3 = new Ch9Supplier("supplier3");

        Ch9Product p1 = new Ch9Product("product1", 1000);

        Ch9Product p2 = new Ch9Product("product2", 2000);

        Ch9Product p3 = new Ch9Product("product3", 3000);

        Ch9Product p4 = new Ch9Product("product4", 4000);

        Ch9Product p5 = new Ch9Product("product5", 5000);

        withTransaction(session -> {
            s1.getProducts().add(p1);
            p1.setSupplier(s1);

            s1.getProducts().add(p2);
            p2.setSupplier(s1);

            s2.getProducts().add(p3);
            p3.setSupplier(s2);

            s2.getProducts().add(p4);
            p4.setSupplier(s2);

            s3.getProducts().add(p5);
            p5.setSupplier(s3);

            session.persist(s1);
            session.persist(s2);
            session.persist(s3);
        });

        withSession(session -> {
            List<Object[]> cheapestProductsBySupplier = session
                    .createQuery("select min(p.price) as cheapestPrice, p.supplier as supplierTitle" +
                            " from Ch9Product p group by p.supplier order by p.supplier.title", Object[].class)
                    .list();

            assertEquals(3, cheapestProductsBySupplier.size());
            assertArrayEquals(new Object[]{1000, s1}, cheapestProductsBySupplier.get(0));
            assertArrayEquals(new Object[]{3000, s2}, cheapestProductsBySupplier.get(1));
            assertArrayEquals(new Object[]{5000, s3}, cheapestProductsBySupplier.get(2));
        });
    }
}
