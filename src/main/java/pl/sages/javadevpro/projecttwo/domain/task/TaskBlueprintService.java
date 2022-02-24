package pl.sages.javadevpro.projecttwo.domain.task;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class TaskBlueprintService {

    private final TaskBlueprintRepository taskBlueprintRepository;

    public TaskBlueprint save(TaskBlueprint taskBlueprint) {
        Optional<TaskBlueprint> saved = taskBlueprintRepository.save(taskBlueprint);
        if (saved.isEmpty()) {
            throw new TaskBlueprintAlreadyExistsException("Task blueprint already exists");
        }
        return saved.get();
    }

    public TaskBlueprint findBy(String id) {
        Optional<TaskBlueprint> founded = taskBlueprintRepository.findById(id);
        if (founded.isEmpty()) {
            throw new TaskBlueprintNotFoundException("Task blueprint not found");
        }
        return founded.get();
    }

    public TaskBlueprint remove(TaskBlueprint taskBlueprint) {
        Optional<TaskBlueprint> removed = taskBlueprintRepository.remove(taskBlueprint);
        if (removed.isEmpty()) {
            throw new TaskBlueprintNotFoundException("Task blueprint not found");
        }
        return removed.get();
    }

    public TaskBlueprint update(TaskBlueprint taskBlueprint) {
        return taskBlueprintRepository.update(taskBlueprint);
    }

}
