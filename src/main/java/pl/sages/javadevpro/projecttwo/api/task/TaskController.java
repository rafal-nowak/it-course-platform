package pl.sages.javadevpro.projecttwo.api.task;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.sages.javadevpro.projecttwo.api.task.dto.PageTaskDto;
import pl.sages.javadevpro.projecttwo.api.task.dto.TaskControllerCommand;
import pl.sages.javadevpro.projecttwo.api.task.dto.TaskDto;
import pl.sages.javadevpro.projecttwo.api.task.mapper.PageTaskDtoMapper;
import pl.sages.javadevpro.projecttwo.api.task.mapper.TaskDtoMapper;
import pl.sages.javadevpro.projecttwo.api.task.verification.AuthVerifyTask;
import pl.sages.javadevpro.projecttwo.api.usertask.ListOfFilesResponse;
import pl.sages.javadevpro.projecttwo.api.usertask.MessageResponse;
import pl.sages.javadevpro.projecttwo.domain.assigment.AssigmentService;
import pl.sages.javadevpro.projecttwo.domain.task.IncorrectTaskStatusException;
import pl.sages.javadevpro.projecttwo.domain.task.Task;
import pl.sages.javadevpro.projecttwo.domain.task.TaskCommand;
import pl.sages.javadevpro.projecttwo.domain.task.TaskService;
import pl.sages.javadevpro.projecttwo.domain.task.TaskStatus;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;
import pl.sages.javadevpro.projecttwo.domain.user.model.User;
import pl.sages.javadevpro.projecttwo.security.UserPrincipal;

import java.io.IOException;
import java.util.List;

import static pl.sages.javadevpro.projecttwo.domain.user.model.UserRole.ADMIN;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskDtoMapper taskMapper;
    private final PageTaskDtoMapper pageTaskDtoMapper;
    private final UserService userService;

    private final AssigmentService assigmentService;

    @GetMapping
    public ResponseEntity<PageTaskDto> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());

        Pageable pageable = PageRequest.of(page, size);

        PageTaskDto pageTasks;

        if (user.getRoles().contains(ADMIN)) {
            pageTasks = pageTaskDtoMapper.toPageDto(taskService.findAll(pageable));
        } else {
            pageTasks = pageTaskDtoMapper.toPageDto(assigmentService.findAllTasksByUserId(pageable, user.getId()));
        }

        return ResponseEntity.ok(pageTasks);
    }

    @GetMapping(path = "/{taskId}")
    @AuthVerifyTask
    public ResponseEntity<TaskDto> getTask(@PathVariable String taskId) {
        Task task = taskService.findById(taskId);
        return ResponseEntity
                .ok(taskMapper.toDto(task));
    }

    @PostMapping(path = "{taskId}/commands")
    @AuthVerifyTask
    public ResponseEntity<MessageResponse> taskCommand(
            @PathVariable String taskId,
            @RequestBody TaskControllerCommand taskControllerCommand
    ) {
        // TODO ta logika powinna być niżej - done
        //TODO do zrobienia test integracyjny - done
        String taskStatus = taskService.executeCommand(
                TaskCommand.valueOf(taskControllerCommand.getCommandName().name()),
                taskId
        );
        return ResponseEntity.ok(
                new MessageResponse("Task " + taskId + " executed, status: " + taskStatus)
        );
    }

    @GetMapping(
            produces = "application/json",
            consumes = "application/json",
            path = "{taskId}/files"
    )
    @AuthVerifyTask
    public ResponseEntity<ListOfFilesResponse> getFilesAssignedToUserTask(
            @PathVariable String taskId
    ) {
        List<String> listOfFiles = taskService.getTaskFilesList(taskId);
        return ResponseEntity.ok(new ListOfFilesResponse(
                "OK",
                listOfFiles));
    }

    @GetMapping(
            path = "{taskId}/files/{fileId}"
    )
    @AuthVerifyTask
    public ResponseEntity<Object> getFileAssignedToUserTask(
            @PathVariable String taskId,
            @PathVariable int fileId
    ) {
        return createResponseEntityForFileAssignedToUserTask(taskId, fileId);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE},
            path = "{taskId}/files/{fileId}"
    )
    @AuthVerifyTask
    public ResponseEntity<MessageResponse> postFileAssignedToUserTask(
            @PathVariable String taskId,
            @PathVariable int fileId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (taskService.getTaskStatus(taskId).equals(TaskStatus.SUBMITTED)) {
            throw new IncorrectTaskStatusException();
        }

        // TODO przenieść do handlera - done
        taskService.writeAndCommitTask(taskId, fileId, file.getBytes());

        return ResponseEntity.ok(new MessageResponse("The File Uploaded Successfully"));
    }

    private ResponseEntity<Object> createResponseEntityForFileAssignedToUserTask(String taskId, int fileId) {
        String filePath = taskService.getTaskFilesList(taskId).get(fileId);
        String fileName = filePath.substring(filePath.lastIndexOf('/') + 1);
        byte[] file = taskService.readTaskFile(taskId, filePath);
        HttpHeaders headers = prepareHttpHeadersForFileResponse(fileName);
        return ResponseEntity.ok().headers(headers).contentLength(file.length).contentType(MediaType.parseMediaType("application/txt")).body(file);
    }

    @GetMapping(
            path = "{taskId}/results"
    )
    @AuthVerifyTask
    public ResponseEntity<Object> getUserTaskResult(
            @PathVariable String taskId,
            @Value("${message.testResultFileName}") String fileName
    ) {
        byte[] file = taskService.readTaskResults(taskId);
        HttpHeaders headers = prepareHttpHeadersForFileResponse(fileName);
        return ResponseEntity.ok().headers(headers).contentLength(file.length).contentType(MediaType.parseMediaType("application/txt")).body(file);
    }

    private HttpHeaders prepareHttpHeadersForFileResponse (String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return headers;
    }
}
