package org.example;

import org.example.ch4.Ch4SimpleObject;
import org.hibernate.Session;

import static org.example.DatabaseUtils.openSession;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ValidateSimpleObject {
    private ValidateSimpleObject() {
        // No-op.
    }

    public static Ch4SimpleObject validate(
            Long id,
            String expectedKey,
            Integer expectedValue
    ) {
        try (Session session = openSession()) {
            Ch4SimpleObject so = new Ch4SimpleObject();

            session.load(so, id);

            assertEquals(expectedKey, so.getKey());
            assertEquals(expectedValue, so.getValue());

            return so;
        }
    }
}
