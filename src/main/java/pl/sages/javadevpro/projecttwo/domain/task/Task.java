package pl.sages.javadevpro.projecttwo.domain.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
