package pl.sages.javadevpro.projecttwo.domain.user;

public class UserNotFound extends RuntimeException {

    public UserNotFound(String message) {
        super(message);
    }

}
