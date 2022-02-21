package pl.sages.javadevpro.projecttwo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.sages.javadevpro.projecttwo.api.usertask.*;
import pl.sages.javadevpro.projecttwo.domain.UserTaskService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/usertasks")
public class UserTaskEndpoint {

    private final UserTaskService userTaskService;


    @PostMapping(
            path = "/assign",
            produces = "application/json",
            consumes = "application/json"
    )
    @Secured("ROLE_ADMIN")
    public ResponseEntity<MessageResponse> assignTaskToUser(@RequestBody UserTaskRequest userTaskRequest) {
        userTaskService.assignTask(userTaskRequest.getUserId(), userTaskRequest.getTaskId());
        return ResponseEntity.ok(new MessageResponse("OK", "Task assigned to user"));
    }

    @PostMapping("/run")
    @Secured("ROLE_STUDENT")
    public ResponseEntity<String> post(@RequestBody RunSolutionRequest runSolutionRequest) {
        String taskStatus = userTaskService
                .exec(runSolutionRequest.getUserEmail(), runSolutionRequest.getTaskId());

        return ResponseEntity.ok(taskStatus);
    }

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
            path = "{userId}/{taskId}/files/{fileId}"
    )
    @Secured("ROLE_STUDENT")
    public ResponseEntity<Object>  getFileAssignedToUserTask(
            @PathVariable String userId,
            @PathVariable String taskId,
            @PathVariable String fileId) {


        InputStreamResource resource;
        try {
            File file = userTaskService.takeFileFromUserTask(userId, taskId, fileId);

            resource = new InputStreamResource(new FileInputStream(file));

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);

        } catch (FileNotFoundException e) {
            return new ResponseEntity<>("error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE},
            path = "{userId}/{taskId}/files/{fileId}"
    )
    @Secured("ROLE_STUDENT")
    public ResponseEntity<Object>  postFileAssignedToUserTask(
            @RequestParam("file") MultipartFile file,
            @PathVariable String userId,
            @PathVariable String taskId,
            @PathVariable String fileId
            ) {

        try {
            byte[] bytes = file.getBytes();

            userTaskService.uploadFileForUserTask(userId, taskId, fileId, bytes);

            userTaskService.commitTask(userId, taskId);

        } catch (IOException e) {
            return new ResponseEntity<>("The File Upload Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("The File Uploaded Successfully", HttpStatus.OK);
    }

    @GetMapping(
            path = "/results",
            produces = "application/json",
            consumes = "application/json"
    )
    @Secured("ROLE_STUDENT")
    public ResponseEntity<String> getUserTaskResult(@RequestBody UserTaskRequest userTaskRequest){
        String resultSummary = userTaskService.getUserTaskStatusSummary(userTaskRequest.getUserId(), userTaskRequest.getTaskId());
        return ResponseEntity.ok(resultSummary);
    }

}
