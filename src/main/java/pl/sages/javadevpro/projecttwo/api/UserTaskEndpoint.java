package pl.sages.javadevpro.projecttwo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.sages.javadevpro.projecttwo.api.usertask.AssignTaskRequest;
import pl.sages.javadevpro.projecttwo.api.usertask.MessageResponse;
import pl.sages.javadevpro.projecttwo.api.usertask.UserTaskDto;
import pl.sages.javadevpro.projecttwo.api.usertask.UserTaskDtoMapper;
import pl.sages.javadevpro.projecttwo.domain.UserTaskService;
import pl.sages.javadevpro.projecttwo.domain.usertask.UserTask;


@RequiredArgsConstructor
@RestController
public class UserTaskEndpoint {

    private final UserTaskService userTaskService;
    private final UserTaskDtoMapper mapper;


    @PostMapping(
            path = "/usertask/assign",
            produces = "application/json",
            consumes = "application/json"
    )
    @Secured("ROLE_ADMIN")
    public ResponseEntity<MessageResponse> assignTaskToUser(@RequestBody AssignTaskRequest assignTaskRequest) {
        userTaskService.assignTask(assignTaskRequest.getUserEmail(), assignTaskRequest.getTaskId());
        return ResponseEntity.ok(new MessageResponse("OK", "Task assigned to user"));
    }


    @GetMapping(
            path = "/users/{email}/tasks/{id}",
            produces = "application/json",
            consumes = "application/json"
    )
    @Secured("ROLE_USER") //TODO protect user from seeing other users results (or details)
    public ResponseEntity<UserTaskDto> getUserTaskResult(@PathVariable String email, @PathVariable String id){
        UserTask userTask = userTaskService.getUserTask(email,id);
        return ResponseEntity.ok(mapper.toDto(userTask));
    }
}
