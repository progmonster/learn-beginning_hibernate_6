package learn.beginning_hibernate_6.ch7;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static learn.beginning_hibernate_6.HibernateUtils.reinitializeDatabase;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidationTests {
    private ValidatorFactory validatorFactory;

    private Validator validator;

    @BeforeEach
    void setUp() {
        reinitializeDatabase();

        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterEach
    void tearDown() {
        validatorFactory.close();
    }

    @Test
    void testNotEmptyName() {
        Ch7User user = new Ch7User(null, "john@test.com", "user");

        Set<ConstraintViolation<Ch7User>> violationSet = validator.validate(user);

        assertEquals(1, violationSet.size());
        assertEquals("must not be empty", violationSet.toArray(new ConstraintViolation[]{})[0].getMessage());
    }

    @Test
    void testInvalidEmail() {
        Ch7User user = new Ch7User("John", "invalid email", "user");

        Set<ConstraintViolation<Ch7User>> violationSet = validator.validate(user);

        assertEquals(1, violationSet.size());

        assertEquals(
                "must be a well-formed email address",
                violationSet.toArray(new ConstraintViolation[]{})[0].getMessage()
        );
    }

    @Test
    void testEmptyRoleSet() {
        Ch7User user = new Ch7User("John", "john@test.com");

        Set<ConstraintViolation<Ch7User>> violationSet = validator.validate(user);

        assertEquals(1, violationSet.size());

        assertEquals(
                "size must be between 1 and 2147483647",
                violationSet.toArray(new ConstraintViolation[]{})[0].getMessage()
        );
    }

    @Test
    void testCustomValidator() {
        Ch7User user = new Ch7User("Gummy Bear", "john@test.com", "user");

        Set<ConstraintViolation<Ch7User>> violationSet = validator.validate(user);

        assertEquals(1, violationSet.size());

        assertEquals(
                "Gummy Bears can not be users",
                violationSet.toArray(new ConstraintViolation[]{})[0].getMessage()
        );
    }

}
