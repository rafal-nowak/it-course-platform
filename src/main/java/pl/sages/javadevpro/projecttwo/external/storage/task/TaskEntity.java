package pl.sages.javadevpro.projecttwo.external.storage.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import pl.sages.javadevpro.projecttwo.domain.task.TaskStatus;

@AllArgsConstructor
@Setter
@Getter
public class TaskEntity {

    @Id
    String id;
    String name;
    String description;
    String workspaceUrl;
    TaskStatus status;

}
