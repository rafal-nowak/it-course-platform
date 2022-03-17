package pl.sages.javadevpro.projecttwo.api.task.blueprint;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprint;

@Mapper(componentModel = "spring")
public interface TaskBlueprintDtoMapper {

    TaskBlueprintDto toDto(TaskBlueprint domain);

    TaskBlueprint toDomain(TaskBlueprintDto dto);

}
