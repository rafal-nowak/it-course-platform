package pl.sages.javadevpro.projecttwo.api.task;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.PageQuizSolutionTemplateDto;
import pl.sages.javadevpro.projecttwo.api.task.blueprint.PageTaskBlueprintDto;
import pl.sages.javadevpro.projecttwo.api.task.blueprint.PageTaskBlueprintDtoMapper;
import pl.sages.javadevpro.projecttwo.api.task.blueprint.TaskBlueprintDto;
import pl.sages.javadevpro.projecttwo.api.task.blueprint.TaskBlueprintDtoMapper;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintService;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprint;

@RequiredArgsConstructor
@RestController
@RequestMapping(
    path = "/api/v1/task-blueprints",
    produces = "application/json",
    consumes = "application/json"
)
public class TaskBlueprintController {

    private final TaskBlueprintService taskBlueprintService;
    private final TaskBlueprintDtoMapper dtoMapper;
    private final PageTaskBlueprintDtoMapper pageTaskBlueprintDtoMapper;

    @GetMapping
    public ResponseEntity<PageTaskBlueprintDto> getTaskBlueprints(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageTaskBlueprintDto pageTaskBlueprintDto = pageTaskBlueprintDtoMapper.toPageDto(taskBlueprintService.findAll(pageable));

        return ResponseEntity.ok(pageTaskBlueprintDto);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TaskBlueprintDto> getTask(@PathVariable(name = "id") String taskId) {
        TaskBlueprint taskBlueprint = taskBlueprintService.findBy(taskId);

        return ResponseEntity
                .ok(dtoMapper.toDto(taskBlueprint));
    }

    @PostMapping
    public ResponseEntity<TaskBlueprintDto> saveTask(@RequestBody TaskBlueprintDto dto) {
        TaskBlueprint taskBlueprint = taskBlueprintService.save(dtoMapper.toDomain(dto));
        return ResponseEntity
                .ok(dtoMapper.toDto(taskBlueprint));
    }

    @DeleteMapping
    public ResponseEntity<Void> removeTask(@RequestBody TaskBlueprintDto dto){
        taskBlueprintService.remove(dtoMapper.toDomain(dto));
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateTask(@RequestBody TaskBlueprintDto dto){
        taskBlueprintService.update(dtoMapper.toDomain(dto));
       return ResponseEntity.ok().build();
    }

}
