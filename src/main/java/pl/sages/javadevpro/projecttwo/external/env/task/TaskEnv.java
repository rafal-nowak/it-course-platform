package pl.sages.javadevpro.projecttwo.external.env.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sages.javadevpro.projecttwo.domain.task.TaskStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskEnv {

    String id;
    String name;
    String description;
    String workspaceUrl;
    TaskStatusEnv status;

    public TaskEnv withStatus(TaskStatusEnv status) {
        return new TaskEnv(id, name, description, workspaceUrl, status);
    }

}

