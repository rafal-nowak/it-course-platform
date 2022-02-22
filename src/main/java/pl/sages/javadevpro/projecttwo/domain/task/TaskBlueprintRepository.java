package pl.sages.javadevpro.projecttwo.domain.task;

import java.util.Optional;

public interface TaskBlueprintRepository {

    Optional<TaskBlueprint> save(TaskBlueprint taskBlueprint);

    Optional<TaskBlueprint> findById(String id);

    void remove(TaskBlueprint taskBlueprint);

    TaskBlueprint update(TaskBlueprint taskBlueprint);
}
