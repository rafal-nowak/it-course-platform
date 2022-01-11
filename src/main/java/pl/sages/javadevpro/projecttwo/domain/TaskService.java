package pl.sages.javadevpro.projecttwo.domain;

import lombok.RequiredArgsConstructor;
import pl.sages.javadevpro.projecttwo.domain.task.Task;
import pl.sages.javadevpro.projecttwo.domain.task.TaskRepository;

@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task saveTask(Task task){
        return taskRepository.save(task);
    }

    public Task getTask(String id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void removeTask(Task task){ taskRepository.remove(task); }

    public Task updateTask(Task task) { return taskRepository.update(task); }

}
