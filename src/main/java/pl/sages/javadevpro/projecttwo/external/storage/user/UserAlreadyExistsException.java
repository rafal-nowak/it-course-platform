package pl.sages.javadevpro.projecttwo.external.storage.user;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
