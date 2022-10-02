package org.example.ch6;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.HibernateUtils.openSession;
import static org.example.HibernateUtils.reinitializeDatabase;

public class OrderColumnTest {

    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @AfterEach
    void tearDown() {
        //uninitializeDatabase();
    }

    @Test
    void testOrderColumn() throws Exception {
        Ch6Customer customer;

        Ch6Payment payment2;

        try(Session session = openSession()) {
            session.getTransaction().begin();

            customer = new Ch6Customer();

            var payment1 = new Ch6Payment("goal1");

            payment1.setCustomer(customer);

            payment2 = new Ch6Payment("goal2");

            payment2.setCustomer(customer);

            var payment3 = new Ch6Payment("goal3");

            payment3.setCustomer(customer);

            customer.getPayments().add(payment1);
            customer.getPayments().add(payment2);
            customer.getPayments().add(payment3);

            session.persist(customer);

            session.getTransaction().commit();
        }

        try(Session session = openSession()) {
            session.getTransaction().begin();

            customer = session.get(Ch6Customer.class, customer.getId());

            payment2 = session.get(Ch6Payment.class, payment2.getId());

            customer.getPayments().remove(payment2);

            session.getTransaction().commit();
        }

        try(Session session = openSession()) {
            session.getTransaction().begin();

            customer = session.get(Ch6Customer.class, customer.getId());

            var payment4 = new Ch6Payment("goal4");

            payment4.setCustomer(customer);

            customer.getPayments().add(payment4);

            session.getTransaction().commit();
        }
    }
}
