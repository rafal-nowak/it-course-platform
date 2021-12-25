package pl.sages.javadevpro.projecttwo.external.storage.task;

import org.springframework.stereotype.Component;
import pl.sages.javadevpro.projecttwo.domain.task.Task;

@Component
public class TaskEntityMapper {

    public TaskEntity toEntity(Task task) {
        return TaskEntity.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .build();
    }

    public Task toDomain(TaskEntity entity) {
        return new Task(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }

}
