package pl.sages.javadevpro.projecttwo.api.task;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprint;

@Mapper
public interface TaskBlueprintDtoMapper {

    TaskBlueprintDto toDto(TaskBlueprint domain);

    TaskBlueprint toDomain(TaskBlueprintDto dto);

}
