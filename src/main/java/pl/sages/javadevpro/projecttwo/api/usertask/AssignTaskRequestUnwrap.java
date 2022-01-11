package pl.sages.javadevpro.projecttwo.api.usertask;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sages.javadevpro.projecttwo.domain.TaskService;
import pl.sages.javadevpro.projecttwo.domain.UserService;
import pl.sages.javadevpro.projecttwo.domain.task.Task;
import pl.sages.javadevpro.projecttwo.domain.user.User;

@Service
@RequiredArgsConstructor
public class AssignTaskRequestUnwrap {


    private final TaskService taskService;
    private final UserService userService;

    public User unwrapUser(AssignTaskRequest assignTaskRequest) {
        return userService.getUser(assignTaskRequest.getUserEmail());
    }

    public Task unwrapTask(AssignTaskRequest assignTaskRequest) {
        return taskService.getTask(assignTaskRequest.getTaskId());
    }
}
