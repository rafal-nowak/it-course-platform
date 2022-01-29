package pl.sages.javadevpro.projecttwo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sages.javadevpro.projecttwo.api.usertask.AssignTaskRequest;
import pl.sages.javadevpro.projecttwo.api.usertask.ListOfFilesResponse;
import pl.sages.javadevpro.projecttwo.api.usertask.MessageResponse;
import pl.sages.javadevpro.projecttwo.domain.UserTaskService;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/usertasks")
public class UserTasksEndpoint {

    private final UserTaskService userTaskService;

    @GetMapping(
            produces = "application/json",
            consumes = "application/json",
            path = "{userId}/{taskId}/files"
    )
    @Secured("ROLE_STUDENT")
    public ResponseEntity<ListOfFilesResponse>  getFilesAssignedToUserTask(
            @PathVariable String userId,
            @PathVariable String taskId) {

        List<String> listOfFiles = userTaskService.readListOfAvailableFilesForUserTask(userId, taskId);

        return ResponseEntity.ok(new ListOfFilesResponse(
                "OK",
                listOfFiles));
    }

    @GetMapping(
            produces = "application/json",
            consumes = "application/json",
            path = "{userId}/{taskId}/files/{fileId}"
    )
    @Secured("ROLE_STUDENT")
    public ResponseEntity<MessageResponse>  getFileAssignedToUserTask(
            @PathVariable String userId,
            @PathVariable String taskId,
            @PathVariable String fileId) {


        return ResponseEntity.ok(new MessageResponse(
                "OK",
                "Info: GET /usertasks/" + userId + "/" + taskId + "/files/" + fileId));
    }


    @PostMapping(
            produces = "application/json",
            consumes = "application/json",
            path = "{userId}/{taskId}/files/{fileId}"
    )
    @Secured("ROLE_STUDENT")
    public ResponseEntity<MessageResponse>  postFilesAssignedToUserTask(
            @PathVariable String userId,
            @PathVariable String taskId,
            @PathVariable String fileId) {

        return ResponseEntity.ok(new MessageResponse(
                "OK",
                "Info: POST /usertasks/" + userId + "/" + taskId + "/files/" + fileId));

    }


}
