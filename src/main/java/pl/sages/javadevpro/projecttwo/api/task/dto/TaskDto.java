package pl.sages.javadevpro.projecttwo.api.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class TaskDto {
    String id;
    String name;
    String description;
    String workspaceUrl;
    TaskStatusDto status;
}
