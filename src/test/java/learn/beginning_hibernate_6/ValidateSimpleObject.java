package learn.beginning_hibernate_6;

import learn.beginning_hibernate_6.ch4.Ch4SimpleObject;
import org.hibernate.Session;

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
        try (Session session = HibernateUtils.openSession()) {
            Ch4SimpleObject so = new Ch4SimpleObject();

            session.load(so, id);

            assertEquals(expectedKey, so.getKey());
            assertEquals(expectedValue, so.getValue());

            return so;
        }
    }
}
