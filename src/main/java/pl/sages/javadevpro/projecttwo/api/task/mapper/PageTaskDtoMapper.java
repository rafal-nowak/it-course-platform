package pl.sages.javadevpro.projecttwo.api.task.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.sages.javadevpro.projecttwo.api.task.dto.PageTaskDto;
import pl.sages.javadevpro.projecttwo.api.task.dto.TaskDto;
import pl.sages.javadevpro.projecttwo.domain.task.PageTask;
import pl.sages.javadevpro.projecttwo.domain.task.Task;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageTaskDtoMapper {

    @Mapping(target = "tasks", qualifiedByName = "toTaskDtoList")
    PageTaskDto toPageDto(PageTask domain);

    @Named("toTaskDtoList")
    @IterableMapping(qualifiedByName = "taskToTaskDto")
    List<TaskDto> toListDto(List<Task> tasks);

    @Named("taskToTaskDto")
    TaskDto toDto(Task domain);
}
