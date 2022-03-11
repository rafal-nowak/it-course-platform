package pl.sages.javadevpro.projecttwo.api.task;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.sages.javadevpro.projecttwo.api.usertask.*;
import pl.sages.javadevpro.projecttwo.domain.assigment.AssigmentService;
import pl.sages.javadevpro.projecttwo.domain.task.TaskService;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;
import pl.sages.javadevpro.projecttwo.external.workspace.WorkspaceService;
import pl.sages.javadevpro.projecttwo.security.UserPrincipal;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/tasks")
public class TaskController {

    private final AssigmentService assigmentService;
    private final WorkspaceService workspaceService;
    private final UserService userService;
    private final TaskService taskService;

    @PostMapping(
            path = "/assign",
            produces = "application/json",
            consumes = "application/json"
    )
    @Secured("ROLE_ADMIN")
    public ResponseEntity<MessageResponse> assignTaskToUser(@RequestBody AssigmentRequest assigmentRequest) {
        assigmentService.assignNewTask(assigmentRequest.getUserId(), assigmentRequest.getTaskId());
        return ResponseEntity.ok(new MessageResponse("OK", "Task assigned to user"));
    }

//    @PostMapping("/run")
//    @Secured("ROLE_STUDENT")
//    public ResponseEntity<String> post(@RequestBody RunSolutionRequest runSolutionRequest) {
//        String taskStatus = userTaskService
//                .exec(runSolutionRequest.getUserEmail(), runSolutionRequest.getTaskId());
//
//        return ResponseEntity.ok(taskStatus);
//    }

    @GetMapping(
            produces = "application/json",
            consumes = "application/json",
            path = "{taskId}/files"
    )
    public ResponseEntity<Object>  getFilesAssignedToUserTask(
            @PathVariable String taskId,
            Authentication authentication) {

        System.out.println(authentication.getPrincipal().getClass());
        User user = userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());
        List<String> listOfFiles = null;
        if (assigmentService.isTaskAssignedToUser(user.getId(), taskId)){
            listOfFiles = taskService.getTaskFilesList(taskId);
            return ResponseEntity.ok(new ListOfFilesResponse(
                    "OK",
                    listOfFiles));
        }
        return new ResponseEntity<>("ERROR", HttpStatus.NOT_FOUND);
    }

//    @GetMapping(
//            path = "{userId}/{taskId}/files/{fileId}"
//    )
//    @Secured("ROLE_STUDENT")
//    public ResponseEntity<Object>  getFileAssignedToUserTask(
//            @PathVariable String userId,
//            @PathVariable String taskId,
//            @PathVariable String fileId) {
//
//
//        InputStreamResource resource;
//        try {
//            File file = userTaskService.takeFileFromUserTask(userId, taskId, fileId);
//
//            resource = new InputStreamResource(new FileInputStream(file));
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
//            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//            headers.add("Pragma", "no-cache");
//            headers.add("Expires", "0");
//
//            return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
//
//        } catch (FileNotFoundException e) {
//            return new ResponseEntity<>("error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }
//
//    @PostMapping(
//            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE},
//            path = "{userId}/{taskId}/files/{fileId}"
//    )
//    @Secured("ROLE_STUDENT")
//    public ResponseEntity<Object>  postFileAssignedToUserTask(
//            @RequestParam("file") MultipartFile file,
//            @PathVariable String userId,
//            @PathVariable String taskId,
//            @PathVariable String fileId
//            ) {
//
//        try {
//            byte[] bytes = file.getBytes();
//
//            userTaskService.uploadFileForUserTask(userId, taskId, fileId, bytes);
//
//            userTaskService.commitTask(userId, taskId);
//
//        } catch (IOException e) {
//            return new ResponseEntity<>("The File Upload Failed", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        return new ResponseEntity<>("The File Uploaded Successfully", HttpStatus.OK);
//    }
//
//    @GetMapping(
//            path = "/results",
//            produces = "application/json",
//            consumes = "application/json"
//    )
//    @Secured("ROLE_STUDENT")
//    public ResponseEntity<String> getUserTaskResult(@RequestBody UserTaskRequest userTaskRequest){
//        String resultSummary = userTaskService.getUserTaskStatusSummary(userTaskRequest.getUserId(), userTaskRequest.getTaskId());
//        return ResponseEntity.ok(resultSummary);
//    }

}
