package pl.sages.javadevpro.projecttwo.api.task;

import org.springframework.stereotype.Component;
import pl.sages.javadevpro.projecttwo.api.user.UserDto;
import pl.sages.javadevpro.projecttwo.domain.task.Task;
import pl.sages.javadevpro.projecttwo.domain.user.User;

@Component
public class TaskDtoMapper {

    public TaskDto toDto(Task task) {
        if (task != null) {
            return new TaskDto(
                    task.getId(),
                    task.getName(),
                    task.getDescription()
            );
        }

        return null;
    }

    public Task toDomain(TaskDto dto) {
        return new Task(
                dto.getId(),
                dto.getName(),
                dto.getDescription()
        );
    }

}
