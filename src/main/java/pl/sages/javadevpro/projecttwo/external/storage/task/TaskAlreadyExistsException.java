package pl.sages.javadevpro.projecttwo.external.storage.task;

public class TaskAlreadyExistsException extends RuntimeException {

    public TaskAlreadyExistsException(String message) {
        super(message);
    }

}
