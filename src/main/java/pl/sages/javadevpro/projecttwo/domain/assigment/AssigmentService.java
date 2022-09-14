package pl.sages.javadevpro.projecttwo.domain.assigment;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.sages.javadevpro.projecttwo.domain.task.PageTask;
import pl.sages.javadevpro.projecttwo.domain.task.Task;
import pl.sages.javadevpro.projecttwo.domain.task.TaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


// TODO final + required args constructor - done
@RequiredArgsConstructor
public class AssigmentService {

    private final AssigmentRepository assigmentRepository;
    private final TaskService taskService;

    public Assigment assignNewTask(String userId, String taskBlueprintId) {
        var task = taskService.createTask(taskBlueprintId);
        var assigment = new Assigment(userId, task.getId());
        return assigmentRepository.save(assigment);
    }

    public void deleteTask(String taskId) {
        taskService.deleteTask(taskId);
        assigmentRepository.remove(taskId);
    }

    public boolean isTaskAssignedToUser(String userId, String taskId){
       return assigmentRepository.find(userId, taskId).isPresent();
    }

    public PageTask findAllTasksByUserId(final Pageable pageable, final String userId) {
        final List<Task> userTasks = taskService.findAll()
                .stream()
                .filter(task -> this.isTaskAssignedToUser(userId, task.getId()))
                .collect(Collectors.toList());


        Page<Task> pages;

        int startOfPage = pageable.getPageNumber() * pageable.getPageSize();
        if (startOfPage > userTasks.size()) {
            pages = new PageImpl<>(new ArrayList<>(), pageable, 0);
        } else {
            int endOfPage = Math.min(startOfPage + pageable.getPageSize(), userTasks.size());
            pages = new PageImpl<>(userTasks.subList(startOfPage, endOfPage), pageable, userTasks.size());
        }


        return new PageTask(pages.getContent(), pages.getNumber(), pages.getTotalPages(), pages.getTotalElements());
    }
}
