package pl.sages.javadevpro.projecttwo.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.sages.javadevpro.projecttwo.domain.exception.DuplicateRecordException;
import pl.sages.javadevpro.projecttwo.domain.exception.RecordNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintAlreadyExist;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintNotFound;
import pl.sages.javadevpro.projecttwo.domain.user.UserAlreadyExist;
import pl.sages.javadevpro.projecttwo.domain.user.UserNotFound;

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

    @ExceptionHandler(TaskBlueprintNotFound.class)
    public final ResponseEntity<Void> handleTaskBlueprintNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(TaskBlueprintAlreadyExist.class)
    public final ResponseEntity<Void> handleTaskBlueprintAlreadyExistException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(UserNotFound.class)
    public final ResponseEntity<Void> handleUserNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(UserAlreadyExist.class)
    public final ResponseEntity<Void> handleUserAlreadyExistException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}