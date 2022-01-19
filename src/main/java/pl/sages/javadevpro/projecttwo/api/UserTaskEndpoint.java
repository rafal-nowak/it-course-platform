package pl.sages.javadevpro.projecttwo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.sages.javadevpro.projecttwo.api.usertask.UserTaskRequest;
import pl.sages.javadevpro.projecttwo.api.usertask.ListOfFilesResponse;
import pl.sages.javadevpro.projecttwo.api.usertask.MessageResponse;
import pl.sages.javadevpro.projecttwo.api.usertask.UserTaskDto;
import pl.sages.javadevpro.projecttwo.api.usertask.UserTaskDtoMapper;
import pl.sages.javadevpro.projecttwo.domain.UserTaskService;
import pl.sages.javadevpro.projecttwo.domain.usertask.TaskStatus;
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
    private final UserTaskDtoMapper dtoMapper;


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

    @GetMapping("/sendtask/{taskId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<String> post(@PathVariable("taskId") final String taskId) {


        UserTaskDto userTaskDto = new UserTaskDto(
                "12",
                "Task2",
                "super fajny task",
                TaskStatus.STARTED
        );

        String taskStatus = userTaskService
                .exec(dtoMapper.toDomain(userTaskDto));
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


        InputStreamResource resource = null;
        try {
            File file = userTaskService.takeFileFromUserTask(userId, taskId, fileId);

            resource = new InputStreamResource(new FileInputStream(file));

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);

            return responseEntity;
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
        String resultSummary = userTaskService.getUserTaskStatusSummary(userTaskRequest.getUserEmail(), userTaskRequest.getTaskId());
        return ResponseEntity.ok(resultSummary);
    }

}
