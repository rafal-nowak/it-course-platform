package pl.sages.javadevpro.projecttwo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AdminPasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminPassword {
    String message() default "{invalid.password.admin-password}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
