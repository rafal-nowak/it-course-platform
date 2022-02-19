package pl.sages.javadevpro.projecttwo.api.task;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprint;

@Mapper
public interface TaskDtoMapper {

    TaskDto toDto(TaskBlueprint taskBlueprint);

    TaskBlueprint toDomain(TaskDto dto);

}
