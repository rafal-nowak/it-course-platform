package pl.sages.javadevpro.projecttwo.api.task.dto;

import lombok.Value;

import java.util.List;

@Value
public class PageTaskDto {
    List<TaskDto> tasks;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}
