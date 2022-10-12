package org.example.ch7;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoGummyBearValidator.class)
public @interface NoGummyBear {
    String message() default "Gummy Bears can not be users";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
