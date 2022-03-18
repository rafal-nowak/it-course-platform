package pl.sages.javadevpro.projecttwo.domain.task;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskBlueprintService {

    private final TaskBlueprintRepository taskBlueprintRepository;

    public TaskBlueprint save(TaskBlueprint taskBlueprint) {
        return taskBlueprintRepository.save(taskBlueprint);
    }

    public TaskBlueprint findBy(String id) {
        return  taskBlueprintRepository.findById(id)
                .orElseThrow(TaskBlueprintNotFoundException::new);
    }

    public void remove(TaskBlueprint taskBlueprint) {
        taskBlueprintRepository.remove(taskBlueprint);
    }

    public void update(TaskBlueprint taskBlueprint) {
        taskBlueprintRepository.update(taskBlueprint);
    }

}
