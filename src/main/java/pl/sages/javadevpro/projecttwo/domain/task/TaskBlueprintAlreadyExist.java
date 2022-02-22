package pl.sages.javadevpro.projecttwo.domain.task;

public class TaskBlueprintAlreadyExist extends RuntimeException {

    public TaskBlueprintAlreadyExist(String message) {
        super(message);
    }

}
