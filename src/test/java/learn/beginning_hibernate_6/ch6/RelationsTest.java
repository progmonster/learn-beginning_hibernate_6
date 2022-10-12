package learn.beginning_hibernate_6.ch6;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static learn.beginning_hibernate_6.HibernateUtils.reinitializeDatabase;
import static learn.beginning_hibernate_6.HibernateUtils.uninitializeDatabase;

class RelationsTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @AfterEach
    void tearDown() {
        uninitializeDatabase();
    }

    @Test
    void name() {
    }
}
