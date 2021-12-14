package pl.sages.javadevpro.projecttwo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AdminPasswordValidator implements ConstraintValidator<AdminPassword, String> {
    private final static String PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*\\-_]).{8,30}";
    private final static String WHITESPACE = "[\\S]{8,20}";

    @Override
    public void initialize(AdminPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password.matches(PATTERN) && password.matches(WHITESPACE);
    }
}