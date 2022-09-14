package pl.sages.javadevpro.projecttwo.domain.task;

import org.springframework.data.domain.Pageable;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuiz;

import java.util.Optional;

public interface TaskBlueprintRepository {

    TaskBlueprint save(TaskBlueprint taskBlueprint);

    Optional<TaskBlueprint> findById(String id);

    void remove(TaskBlueprint taskBlueprint);

    void update(TaskBlueprint taskBlueprint);

    PageTaskBlueprint findAll(Pageable pageable);

}
