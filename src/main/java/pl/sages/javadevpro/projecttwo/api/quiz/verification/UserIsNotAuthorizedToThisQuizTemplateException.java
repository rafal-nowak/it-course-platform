package pl.sages.javadevpro.projecttwo.api.quiz.verification;

public class UserIsNotAuthorizedToThisQuizTemplateException extends RuntimeException{

    public UserIsNotAuthorizedToThisQuizTemplateException(String message) {
        super(message);
    }

}