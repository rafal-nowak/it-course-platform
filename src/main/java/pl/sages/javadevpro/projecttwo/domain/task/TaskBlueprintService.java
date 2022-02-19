package pl.sages.javadevpro.projecttwo.domain.task;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskBlueprintService {

    private final TaskBlueprintRepository taskBlueprintRepository;

    public TaskBlueprint save(TaskBlueprint taskBlueprint){
        return taskBlueprintRepository.save(taskBlueprint);
    }

    public TaskBlueprint findBy(String id) {
        return taskBlueprintRepository.findById(id).orElse(null);
        //TODO - jeśli nie istnieje rzuć TaskBlueprintNotFound - do obsługi w exception handler
    }

    public void remove(TaskBlueprint taskBlueprint){ taskBlueprintRepository.remove(taskBlueprint); } //TODO dodac exception na poziomie domeny

    public TaskBlueprint update(TaskBlueprint taskBlueprint) { return taskBlueprintRepository.update(taskBlueprint); }

}
