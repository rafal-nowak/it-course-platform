package pl.sages.javadevpro.projecttwo.domain.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
