package org.example;

import org.example.ch4.SimpleObject;
import org.hibernate.Session;

import static org.example.DatabaseUtils.openSession;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ValidateSimpleObject {
    private ValidateSimpleObject() {
        // No-op.
    }

    public static SimpleObject validate(
            Long id,
            String expectedKey,
            Integer expectedValue
    ) {
        try (Session session = openSession()) {
            SimpleObject so = new SimpleObject();

            session.load(so, id);

            assertEquals(expectedKey, so.getKey());
            assertEquals(expectedValue, so.getValue());

            return so;
        }
    }
}
