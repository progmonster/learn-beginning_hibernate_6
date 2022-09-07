package org.example.ch5;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.DatabaseUtils.reinitializeDatabase;

public class LazyTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    void lazyTest() throws Exception {
        // TODO: 07/09/22 check how simple fields, collection fields and embedded fields change lazy/eager loading strategy.
    }

    @AfterEach
    void tearDown() {
        //uninitializeDatabase();
    }
}
