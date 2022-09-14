package pl.sages.javadevpro.projecttwo.api.task.blueprint;

import lombok.Value;

import java.util.List;

@Value
public class PageTaskBlueprintDto {
    List<TaskBlueprintDto> taskBlueprints;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}
