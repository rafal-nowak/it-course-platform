package pl.sages.javadevpro.projecttwo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.sages.javadevpro.projecttwo.api.usertask.AssignTaskRequest;
import pl.sages.javadevpro.projecttwo.api.usertask.AssignTaskRequestUnwrap;
import pl.sages.javadevpro.projecttwo.api.usertask.UserTaskDto;
import pl.sages.javadevpro.projecttwo.api.usertask.UserTaskDtoMapper;
import pl.sages.javadevpro.projecttwo.domain.UserTaskService;
import pl.sages.javadevpro.projecttwo.domain.task.Task;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.usertask.UserTask;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/usertask/assign")
public class UserTaskEndpoint {

    private final UserTaskService userTaskService;
    private final UserTaskDtoMapper userTaskDtoMapper;
    private final AssignTaskRequestUnwrap unwrap;

    @PostMapping(
            produces = "application/json",
            consumes = "application/json"
    )
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserTaskDto> assignTaskToUser(@RequestBody AssignTaskRequest assignTaskRequest) {
        Task task = unwrap.unwrapTask(assignTaskRequest);
        User user = unwrap.unwrapUser(assignTaskRequest);
        UserTask userTask = userTaskService.assignTask( user, task);
        return ResponseEntity
                .ok(userTaskDtoMapper.toDto(userTask));
    }
}
