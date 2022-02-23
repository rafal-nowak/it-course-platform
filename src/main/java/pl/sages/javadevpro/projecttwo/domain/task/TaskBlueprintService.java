package pl.sages.javadevpro.projecttwo.domain.task;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class TaskBlueprintService {

    private final TaskBlueprintRepository taskBlueprintRepository;

    public TaskBlueprint save(TaskBlueprint taskBlueprint) {
        Optional<TaskBlueprint> saved = taskBlueprintRepository.save(taskBlueprint);
        if (saved.isEmpty()) {
            throw new TaskBlueprintAlreadyExist("Task blueprint already exist");
        }
        return saved.get();
    }

    public TaskBlueprint findBy(String id) {
        Optional<TaskBlueprint> founded = taskBlueprintRepository.findById(id);
        if (founded.isEmpty()) {
            throw new TaskBlueprintNotFound("Task blueprint not found");
        }
        return founded.get();
    }

    public void remove(TaskBlueprint taskBlueprint) {
        Optional<TaskBlueprint> removed = taskBlueprintRepository.remove(taskBlueprint);
        if (removed.isEmpty()) {
            throw new TaskBlueprintNotFound("Task blueprint not found");
        }
    }

    public TaskBlueprint update(TaskBlueprint taskBlueprint) {
        return taskBlueprintRepository.update(taskBlueprint);
    }

}
