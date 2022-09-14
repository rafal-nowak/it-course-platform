package pl.sages.javadevpro.projecttwo.domain.task;

import lombok.Value;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;

import java.io.Serializable;
import java.util.List;

@Value
public class PageTaskBlueprint implements Serializable {

    List<TaskBlueprint> taskBlueprints;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;

}
