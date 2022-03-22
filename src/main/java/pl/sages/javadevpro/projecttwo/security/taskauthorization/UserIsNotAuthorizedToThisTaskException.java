package pl.sages.javadevpro.projecttwo.security.taskauthorization;

public class UserIsNotAuthorizedToThisTaskException extends RuntimeException{

    public UserIsNotAuthorizedToThisTaskException(String message) {
        super(message);
    }

}