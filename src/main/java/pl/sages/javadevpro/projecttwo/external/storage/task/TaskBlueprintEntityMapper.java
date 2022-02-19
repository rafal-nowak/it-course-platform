package pl.sages.javadevpro.projecttwo.external.storage.task;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprint;

@Mapper
public interface TaskBlueprintEntityMapper {

    TaskBlueprintEntity toEntity(TaskBlueprint domain);

    TaskBlueprint toDomain(TaskBlueprintEntity entity);

}
