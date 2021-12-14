package pl.sages.javadevpro.projecttwo.validation;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.sages.javadevpro.projecttwo.user.User;
import pl.sages.javadevpro.projecttwo.user.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Setter
@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, User> {
    private final UserService userService;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {

    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        User existUser = userService.findByUserName(user.getUsername());
        return existUser == null || existUser.getId().equals(user.getId());
    }
}