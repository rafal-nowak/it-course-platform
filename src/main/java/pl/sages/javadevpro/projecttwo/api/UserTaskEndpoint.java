package pl.sages.javadevpro.projecttwo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.sages.javadevpro.projecttwo.api.usertask.UserTaskRequest;
import pl.sages.javadevpro.projecttwo.api.usertask.MessageResponse;
import pl.sages.javadevpro.projecttwo.domain.UserTaskService;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/usertask")
public class UserTaskEndpoint {

    private final UserTaskService userTaskService;


    @PostMapping(
            path = "/assign",
            produces = "application/json",
            consumes = "application/json"
    )
    @Secured("ROLE_ADMIN")
    public ResponseEntity<MessageResponse> assignTaskToUser(@RequestBody UserTaskRequest userTaskRequest) {
        userTaskService.assignTask(userTaskRequest.getUserEmail(), userTaskRequest.getTaskId());
        return ResponseEntity.ok(new MessageResponse("OK", "Task assigned to user"));
    }


    @GetMapping(
            path = "/results",
            produces = "application/json",
            consumes = "application/json"
    )
    @Secured("ROLE_STUDENT")
    public ResponseEntity<String> getUserTaskResult(@RequestBody UserTaskRequest userTaskRequest){
        String resultSummary = userTaskService.getUserTaskStatusSummary(userTaskRequest.getUserEmail(), userTaskRequest.getTaskId());
        return ResponseEntity.ok(resultSummary);
    }
}
