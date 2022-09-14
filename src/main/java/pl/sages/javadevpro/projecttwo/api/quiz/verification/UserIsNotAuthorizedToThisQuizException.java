package pl.sages.javadevpro.projecttwo.api.quiz.verification;

public class UserIsNotAuthorizedToThisQuizException extends RuntimeException{

    public UserIsNotAuthorizedToThisQuizException(String message) {
        super(message);
    }

}