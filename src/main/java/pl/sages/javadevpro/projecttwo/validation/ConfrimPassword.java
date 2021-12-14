package pl.sages.javadevpro.projecttwo.validation;

import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Constraint(validatedBy = ConfirmPasswordValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfrimPassword {
    String message() default "{invalid.password.confirm-password}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
