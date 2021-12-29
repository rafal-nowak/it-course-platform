package pl.sages.javadevpro.projecttwo.api.task;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.domain.task.Task;

@Mapper
public interface TaskDtoMapper {

    TaskDto toDto(Task task);

    Task toDomain(TaskDto dto);

}
