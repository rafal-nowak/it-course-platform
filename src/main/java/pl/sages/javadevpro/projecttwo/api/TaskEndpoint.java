package pl.sages.javadevpro.projecttwo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.sages.javadevpro.projecttwo.api.task.TaskDto;
import pl.sages.javadevpro.projecttwo.api.task.TaskDtoMapper;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintService;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprint;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/tasks")
public class TaskEndpoint {

    private final TaskBlueprintService taskBlueprintService;
    private final TaskDtoMapper dtoMapper;

    @GetMapping(
            path = "/{id}",
            produces = "application/json",
            consumes = "application/json"
    )
    @Secured({"ROLE_STUDENT", "ROLE_ADMIN"})
    public ResponseEntity<TaskDto> getTask(@PathVariable(name = "id")String taskId) {
        TaskBlueprint taskBlueprint = taskBlueprintService.findBy(taskId);

        return ResponseEntity
                .ok(dtoMapper.toDto(taskBlueprint));
    }

    @PostMapping(
            produces = "application/json",
            consumes = "application/json"
    )
    @Secured("ROLE_ADMIN")
    public ResponseEntity<TaskDto> saveTask(@RequestBody TaskDto dto) {
        TaskBlueprint taskBlueprint = taskBlueprintService.save(dtoMapper.toDomain(dto));
        return ResponseEntity
                .ok(dtoMapper.toDto(taskBlueprint));
    }

    @DeleteMapping(
            produces = "application/json",
            consumes = "application/json"
    )
    @Secured("ROLE_ADMIN")
    public ResponseEntity removeTask(@RequestBody TaskDto dto){
        taskBlueprintService.remove(dtoMapper.toDomain(dto));
        return ResponseEntity.ok(dto);
    }

    @PutMapping(
            produces = "application/json",
            consumes = "application/json"
    )
    @Secured("ROLE_ADMIN")
    public ResponseEntity updateTask(@RequestBody TaskDto dto){
       TaskBlueprint taskBlueprint = taskBlueprintService.update(dtoMapper.toDomain(dto));
       return ResponseEntity.ok(dtoMapper.toDto(taskBlueprint));
    }

}
