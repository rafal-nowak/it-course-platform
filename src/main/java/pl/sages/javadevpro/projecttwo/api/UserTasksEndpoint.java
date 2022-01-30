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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.sages.javadevpro.projecttwo.api.usertask.ListOfFilesResponse;
import pl.sages.javadevpro.projecttwo.api.usertask.MessageResponse;
import pl.sages.javadevpro.projecttwo.domain.UserTaskService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;


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
    public ResponseEntity<MessageResponse>  postFileAssignedToUserTask(
            @RequestParam("file") MultipartFile file,
            @PathVariable String userId,
            @PathVariable String taskId,
            @PathVariable String fileId
            ) {

        userTaskService.uploadFileForUserTask(userId, taskId, fileId, file);

        return ResponseEntity.ok(new MessageResponse(
                "OK",
                "The File Uploaded Successfully"));

    }

}
