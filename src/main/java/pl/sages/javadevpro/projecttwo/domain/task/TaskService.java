package pl.sages.javadevpro.projecttwo.domain.task;

import lombok.RequiredArgsConstructor;
import java.util.List;

import static pl.sages.javadevpro.projecttwo.domain.task.TaskStatus.NOT_STARTED;
import static pl.sages.javadevpro.projecttwo.domain.task.TaskStatus.SUBMITTED;

@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final Workspace taskWorkspace;
    private final TaskBlueprintService taskBlueprintService;
    private final TaskExecutor taskExecutor;
    private final String resultFilePath;

    public String execute(String taskId) {
        updateTaskStatus(taskId, SUBMITTED);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        taskExecutor.exec(task);
        return task.getStatus().toString();
    }

    public List<String> getTaskFilesList(String taskId) {
        var workspacePath = getWorkspacePath(taskId);
        return taskWorkspace.getFilesList(workspacePath);
    }

    public void writeTaskFile(String taskId, String filePath, byte[] bytes) {
        var workspacePath = getWorkspacePath(taskId);
        taskWorkspace.writeFile(workspacePath, filePath, bytes);
    }

    public byte[] readTaskFile(String taskId, String filePath) {
        var workspacePath = getWorkspacePath(taskId);
        return taskWorkspace.readFile(workspacePath, filePath);
    }

    public void commitTaskChanges(String taskId) {
        var workspacePath = getWorkspacePath(taskId);
        taskWorkspace.commitChanges(workspacePath);
    }

    public byte[] readTaskResults(String taskId) {
        return readTaskFile(taskId, resultFilePath);
    }

    public void updateTaskStatus(String taskId, TaskStatus newStatus) {
        var task = findTaskById(taskId).withStatus(newStatus);
        taskRepository.update(task);
    }

    public Task createTask(String blueprintId) {
        var blueprint = taskBlueprintService.findBy(blueprintId);
        var workspaceUrl = taskWorkspace.createWorkspace(blueprint.getRepositoryUrl());
        var task = Task.builder()
                .name(blueprint.getName())
                .description(blueprint.getDescription())
                .workspaceUrl(workspaceUrl)
                .status(NOT_STARTED)
                .build();
        return taskRepository.save(task);
    }

    public TaskStatus getTaskStatus(String taskId) {
        Task task = findTaskById(taskId);
        return task.getStatus();
    }

    private Task findTaskById(String taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
    }

    private String getWorkspacePath(String taskId) {
        return findTaskById(taskId).getWorkspaceUrl();
    }

}
