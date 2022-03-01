package pl.sages.javadevpro.projecttwo.domain.task;

import lombok.RequiredArgsConstructor;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskBlueprintAlreadyExistsException;

import java.util.Optional;

@RequiredArgsConstructor
public class TaskBlueprintService {

    private final TaskBlueprintRepository taskBlueprintRepository;

    public TaskBlueprint save(TaskBlueprint taskBlueprint) {
        return taskBlueprintRepository.save(taskBlueprint);
    }

    public TaskBlueprint findBy(String id) {
        Optional<TaskBlueprint> founded = taskBlueprintRepository.findById(id);
        return founded.orElseThrow(() -> new TaskBlueprintNotFoundException("Task blueprint not found"));
    }

    public TaskBlueprint remove(TaskBlueprint taskBlueprint) {
        Optional<TaskBlueprint> removed = taskBlueprintRepository.remove(taskBlueprint);
        return removed.orElseThrow(() -> new TaskBlueprintNotFoundException("Task blueprint not found"));
    }

    public TaskBlueprint update(TaskBlueprint taskBlueprint) {
        return taskBlueprintRepository.update(taskBlueprint);
    }

}
