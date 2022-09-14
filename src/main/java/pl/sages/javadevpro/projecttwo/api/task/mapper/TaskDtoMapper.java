package pl.sages.javadevpro.projecttwo.api.task.mapper;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.api.task.dto.TaskDto;
import pl.sages.javadevpro.projecttwo.domain.task.Task;

@Mapper(componentModel = "spring")
public interface TaskDtoMapper {
    TaskDto toDto(Task task);

    Task toDomain(TaskDto dto);
}
