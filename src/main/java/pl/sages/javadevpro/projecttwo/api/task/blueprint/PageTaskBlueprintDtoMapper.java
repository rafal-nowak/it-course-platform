package pl.sages.javadevpro.projecttwo.api.task.blueprint;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.sages.javadevpro.projecttwo.domain.task.PageTaskBlueprint;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprint;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageTaskBlueprintDtoMapper {

    @Mapping(target = "taskBlueprints", qualifiedByName = "toTaskBlueprintDtoList")
    PageTaskBlueprintDto toPageDto(PageTaskBlueprint domain);

    @Named("toTaskBlueprintDtoList")
    @IterableMapping(qualifiedByName = "taskBlueprintToTaskBlueprintDto")
    List<TaskBlueprintDto> toListDto(List<TaskBlueprint> taskBlueprints);

    @Named("taskBlueprintToTaskBlueprintDto")
    TaskBlueprintDto toDto(TaskBlueprint domain);

}
