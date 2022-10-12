package learn.beginning_hibernate_6.ch7;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoGummyBearValidator implements ConstraintValidator<NoGummyBear, Ch7User> {
    @Override
    public void initialize(NoGummyBear constraintAnnotation) {
        // No-op.
    }

    @Override
    public boolean isValid(Ch7User user, ConstraintValidatorContext context) {
        String name = user.getName();

        return name == null || !name.replaceAll("\\s", "").equalsIgnoreCase("gummybear");
    }
}
