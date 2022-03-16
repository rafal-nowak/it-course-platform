package pl.sages.javadevpro.projecttwo.api.task;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.sages.javadevpro.projecttwo.api.task.dto.CommandName;
import pl.sages.javadevpro.projecttwo.api.task.dto.TaskControllerCommand;
import pl.sages.javadevpro.projecttwo.api.usertask.ListOfFilesResponse;
import pl.sages.javadevpro.projecttwo.api.usertask.MessageResponse;
import pl.sages.javadevpro.projecttwo.domain.assigment.AssigmentService;
import pl.sages.javadevpro.projecttwo.domain.task.TaskService;
import pl.sages.javadevpro.projecttwo.domain.task.TaskStatus;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;
import pl.sages.javadevpro.projecttwo.security.UserPrincipal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static java.lang.Integer.parseInt;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/tasks")
public class TaskController {

    private final AssigmentService assigmentService;
    private final UserService userService;
    private final TaskService taskService;


    @PostMapping(
            path = "{taskId}/commands"
    )
    public ResponseEntity<Object> taskCommand(
            @RequestBody TaskControllerCommand taskControllerCommand,
            @PathVariable String taskId,
            Authentication authentication) {

        System.out.println(authentication.getPrincipal().getClass());
        User user = userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());
        if (assigmentService.isTaskAssignedToUser(user.getId(), taskId)){
            if(taskControllerCommand.getCommandName().equals(CommandName.EXECUTE)){
                String taskStatus = taskService.execute(taskId);
                return new ResponseEntity<>("Task "  + taskId + " executed, status: " + taskStatus, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("ERROR", HttpStatus.NOT_FOUND);

    }


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

    @GetMapping(
            path = "{taskId}/files/{fileId}"
    )
    public ResponseEntity<Object>  getFileAssignedToUserTask(
            @PathVariable String taskId,
            @PathVariable String fileId,
            Authentication authentication) {

        System.out.println(authentication.getPrincipal().getClass());
        User user = userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());

        if (assigmentService.isTaskAssignedToUser(user.getId(), taskId)){
            String filePath = taskService.getTaskFilesList(taskId).get(parseInt(fileId));
            String fileName = filePath.substring(filePath.lastIndexOf('/') + 1);
            byte[] file = taskService.readTaskFile(taskId, filePath);
            InputStreamResource resource;
            resource = new InputStreamResource(new ByteArrayInputStream(file));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            return ResponseEntity.ok().headers(headers).contentLength(file.length).contentType(MediaType.parseMediaType("application/txt")).body(resource);

        }

        return new ResponseEntity<>("ERROR", HttpStatus.NOT_FOUND);

    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE},
            path = "{taskId}/files/{fileId}"
    )
    public ResponseEntity<Object>  postFileAssignedToUserTask(
            @RequestParam("file") MultipartFile file,
            @PathVariable String taskId,
            @PathVariable int fileId,
            Authentication authentication
            ) {

        System.out.println(authentication.getPrincipal().getClass());
        User user = userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());

        if (assigmentService.isTaskAssignedToUser(user.getId(), taskId)){

            if (taskService.getTaskStatus(taskId).equals(TaskStatus.SUBMITTED)) {
                return new ResponseEntity<>("The File Upload Failed. The Task was sent to ENV.", HttpStatus.METHOD_NOT_ALLOWED);
            }

            try {
                byte[] bytes = file.getBytes();
                String filePath = taskService.getTaskFilesList(taskId).get(fileId);

                taskService.writeTaskFile(taskId, filePath, bytes);

                taskService.commitTaskChanges(taskId);

            } catch (IOException e) {
                return new ResponseEntity<>("The File Upload Failed", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>("The File Uploaded Successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("ERROR", HttpStatus.NOT_FOUND);

    }

    @GetMapping(
            path = "{taskId}/results"
    )
    public ResponseEntity<Object>  getUserTaskResult(
            @PathVariable String taskId,
            Authentication authentication) {

        System.out.println(authentication.getPrincipal().getClass());
        User user = userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());

        if (assigmentService.isTaskAssignedToUser(user.getId(), taskId)){
            String resultSummary = new String(taskService.readTaskResults(taskId));
            return ResponseEntity.ok(resultSummary);
        }

        return new ResponseEntity<>("ERROR", HttpStatus.NOT_FOUND);
    }
}
