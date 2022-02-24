package pl.sages.javadevpro.projecttwo.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.sages.javadevpro.projecttwo.api.usertask.MessageResponse;
import pl.sages.javadevpro.projecttwo.domain.exception.DuplicateRecordException;
import pl.sages.javadevpro.projecttwo.domain.exception.RecordNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintAlreadyExistsException;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.user.UserAlreadyExistsException;
import pl.sages.javadevpro.projecttwo.domain.user.UserNotFoundException;

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
}