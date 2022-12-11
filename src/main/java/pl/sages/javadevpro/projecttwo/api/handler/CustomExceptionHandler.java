package pl.sages.javadevpro.projecttwo.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.sages.javadevpro.projecttwo.api.quiz.verification.UserIsNotAuthorizedToThisQuizException;
import pl.sages.javadevpro.projecttwo.api.quiz.verification.UserIsNotAuthorizedToThisQuizTemplateException;
import pl.sages.javadevpro.projecttwo.api.task.verification.UserIsNotAuthorizedToThisTaskException;
import pl.sages.javadevpro.projecttwo.api.usertask.ErrorResponse;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.exception.GradingTableNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.quiz.exception.QuizNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.quiz.exception.QuizSolutionTemplateNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.quiz.exception.QuizTemplateNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.task.CommandNotSupportedException;
import pl.sages.javadevpro.projecttwo.domain.task.CommitTaskException;
import pl.sages.javadevpro.projecttwo.domain.task.IncorrectTaskStatusException;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.user.exception.UserNotFoundException;
import pl.sages.javadevpro.projecttwo.external.storage.gradingtable.GradingTableAlreadyExistsException;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.QuizAlreadyExistsException;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.QuizSolutionTemplateAlreadyExistsException;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.QuizTemplateAlreadyExistsException;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskBlueprintAlreadyExistsException;
import pl.sages.javadevpro.projecttwo.external.storage.user.UserAlreadyExistsException;
import pl.sages.javadevpro.projecttwo.external.workspace.RepositoryWasNotFoundException;

import java.io.IOException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TaskBlueprintNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleTaskBlueprintNotFoundException(TaskBlueprintNotFoundException ex) {
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskBlueprintAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleTaskBlueprintAlreadyExistsException(TaskBlueprintAlreadyExistsException ex) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GradingTableNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleGradingTableNotFoundException(GradingTableNotFoundException ex) {
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GradingTableAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleGradingTableAlreadyExistsException(GradingTableAlreadyExistsException ex) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(QuizNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleQuizNotFoundException(QuizNotFoundException ex) {
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(QuizAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleQuizAlreadyExistsException(QuizAlreadyExistsException ex) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(QuizTemplateNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleQuizTemplateNotFoundException(QuizTemplateNotFoundException ex) {
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(QuizTemplateAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleQuizTemplateAlreadyExistsException(QuizTemplateAlreadyExistsException ex) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(QuizSolutionTemplateNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleQuizSolutionTemplateNotFoundException(QuizSolutionTemplateNotFoundException ex) {
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(QuizSolutionTemplateAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleQuizSolutionTemplateAlreadyExistsException(QuizSolutionTemplateAlreadyExistsException ex) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return buildResponse(ex,  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserIsNotAuthorizedToThisTaskException.class)
    public final ResponseEntity<ErrorResponse> handleUserIsNotAuthorizedToThisTaskException(UserIsNotAuthorizedToThisTaskException ex) {
        return buildResponse(ex, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(UserIsNotAuthorizedToThisQuizException.class)
    public final ResponseEntity<ErrorResponse> handleUserIsNotAuthorizedToThisQuizException(UserIsNotAuthorizedToThisQuizException ex) {
        return buildResponse(ex, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(UserIsNotAuthorizedToThisQuizTemplateException.class)
    public final ResponseEntity<ErrorResponse> handleUserIsNotAuthorizedToThisQuizTemplateException(UserIsNotAuthorizedToThisQuizTemplateException ex) {
        return buildResponse(ex, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(RepositoryWasNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleRepositoryWasNotFoundException(RepositoryWasNotFoundException ex) {
        return buildResponse(ex, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(CommitTaskException.class)
    public final ResponseEntity<ErrorResponse> handleCommitTaskException(CommitTaskException ex) {
        return buildResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IncorrectTaskStatusException.class)
    public final ResponseEntity<ErrorResponse> handleIncorrectTaskStatusException(IncorrectTaskStatusException ex) {
        return buildResponse(ex, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(CommandNotSupportedException.class)
    public final ResponseEntity<ErrorResponse> handleCommandNotSupportedException(CommandNotSupportedException ex) {
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    public final ResponseEntity<ErrorResponse> handleCommandNotSupportedException(IOException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ex.getMessage()));
    }

    private <E extends RuntimeException> ResponseEntity<ErrorResponse> buildResponse(E exception, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(exception.getMessage()));
    }


}