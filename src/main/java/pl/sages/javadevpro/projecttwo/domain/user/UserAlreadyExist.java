package pl.sages.javadevpro.projecttwo.domain.user;

public class UserAlreadyExist extends RuntimeException {

    public UserAlreadyExist(String message) {
        super(message);
    }

}
