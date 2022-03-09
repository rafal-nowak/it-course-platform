package pl.sages.javadevpro.projecttwo.domain.task;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Task {

    String id;
    String name;
    String description;
    String workspaceUrl;
    TaskStatus status;

    public Task withStatus(TaskStatus status) {
        return new Task(id, name, description, workspaceUrl, status);
    }

}
