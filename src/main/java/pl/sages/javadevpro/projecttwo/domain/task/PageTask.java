package pl.sages.javadevpro.projecttwo.domain.task;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class PageTask implements Serializable {
    List<Task> tasks;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}
