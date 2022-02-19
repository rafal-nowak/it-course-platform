package pl.sages.javadevpro.projecttwo.api.task;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintService;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprint;

@RequiredArgsConstructor
@RestController
@RequestMapping(
    path = "/task-blueprints",
    produces = "application/json",
    consumes = "application/json"
)
public class TaskBlueprintController {

    private final TaskBlueprintService taskBlueprintService;
    private final TaskBlueprintDtoMapper dtoMapper;

    @GetMapping(path = "/{id}")
    @Secured({"ROLE_STUDENT", "ROLE_ADMIN"})
    public ResponseEntity<TaskBlueprintDto> getTask(@PathVariable(name = "id") String taskId) {
        TaskBlueprint taskBlueprint = taskBlueprintService.findBy(taskId);

        return ResponseEntity
                .ok(dtoMapper.toDto(taskBlueprint));
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<TaskBlueprintDto> saveTask(@RequestBody TaskBlueprintDto dto) {
        TaskBlueprint taskBlueprint = taskBlueprintService.save(dtoMapper.toDomain(dto));
        return ResponseEntity
                .ok(dtoMapper.toDto(taskBlueprint));
    }

    @DeleteMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> removeTask(@RequestBody TaskBlueprintDto dto){
        taskBlueprintService.remove(dtoMapper.toDomain(dto));
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<TaskBlueprintDto> updateTask(@RequestBody TaskBlueprintDto dto){
       TaskBlueprint taskBlueprint = taskBlueprintService.update(dtoMapper.toDomain(dto));
       return ResponseEntity.ok(dtoMapper.toDto(taskBlueprint));
    }

}
