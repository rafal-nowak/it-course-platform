package pl.sages.javadevpro.projecttwo.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.sages.javadevpro.projecttwo.api.task.verification.UserIsNotAuthorizedToThisTaskException;
import pl.sages.javadevpro.projecttwo.api.usertask.MessageResponse;
import pl.sages.javadevpro.projecttwo.domain.exception.DuplicateRecordException;
import pl.sages.javadevpro.projecttwo.domain.exception.RecordNotFoundException;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskBlueprintAlreadyExistsException;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintNotFoundException;
import pl.sages.javadevpro.projecttwo.external.storage.user.UserAlreadyExistsException;
import pl.sages.javadevpro.projecttwo.domain.user.UserNotFoundException;
import pl.sages.javadevpro.projecttwo.external.workspace.RepositoryWasNotFoundException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Void> handleUserNotFoundException_() {
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(DuplicateRecordException.class)
    public final ResponseEntity<Void> handleDuplicateRecordException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(TaskBlueprintNotFoundException.class)
    public final ResponseEntity<MessageResponse> handleTaskBlueprintNotFoundException(TaskBlueprintNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse("ERROR", ex.getMessage()));
    }

    @ExceptionHandler(TaskBlueprintAlreadyExistsException.class)
    public final ResponseEntity<MessageResponse> handleTaskBlueprintAlreadyExistsException(TaskBlueprintAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new MessageResponse("ERROR", ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<MessageResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse("ERROR", ex.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public final ResponseEntity<MessageResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new MessageResponse("ERROR", ex.getMessage()));
    }

    @ExceptionHandler(UserIsNotAuthorizedToThisTaskException.class)
    public final ResponseEntity<MessageResponse> handleUserIsNotAuthorizedToThisTaskException(UserIsNotAuthorizedToThisTaskException ex) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new MessageResponse("ERROR", ex.getMessage()));
    }

    @ExceptionHandler(RepositoryWasNotFoundException.class)
    public final ResponseEntity<MessageResponse> handleRepositoryWasNotFoundException(RepositoryWasNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new MessageResponse("ERROR", ex.getMessage()));
    }
}