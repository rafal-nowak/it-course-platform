package pl.sages.javadevpro.projecttwo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sages.javadevpro.projecttwo.api.task.TaskDto;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/tasks")
public class TaskEndpoint {

    @GetMapping(
            path = "/{id}",
            produces = "application/json",
            consumes = "application/json"
    )
    @Secured({"ROLE_STUDENT", "ROLE_ADMIN"})
    public ResponseEntity<TaskDto> getTask(@PathVariable(name = "id")int taskId) {
        return ResponseEntity.ok(new TaskDto(1, "Task1", "Description first task1"));
    }



}
