package pl.sages.javadevpro.projecttwo.domain.assigment;

import lombok.AllArgsConstructor;
import pl.sages.javadevpro.projecttwo.domain.task.TaskService;

@AllArgsConstructor
public class AssigmentService {

    private final AssigmentRepository assigmentRepository;
    private final TaskService taskService;

    public Assigment assignNewTask(String userId, String taskBlueprintId) {
        var task = taskService.createTask(taskBlueprintId);
        var assigment = new Assigment(userId, task.getId());
        return assigmentRepository.save(assigment);
    }

}
