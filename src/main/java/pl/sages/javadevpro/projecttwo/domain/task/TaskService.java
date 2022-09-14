package pl.sages.javadevpro.projecttwo.domain.task;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.sages.javadevpro.projecttwo.domain.assigment.AssigmentService;
import pl.sages.javadevpro.projecttwo.domain.quiz.exception.QuizNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;

import java.util.List;
import java.util.stream.Collectors;

import static pl.sages.javadevpro.projecttwo.domain.task.TaskStatus.NOT_STARTED;
import static pl.sages.javadevpro.projecttwo.domain.task.TaskStatus.SUBMITTED;

@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final Workspace taskWorkspace;
    private final TaskBlueprintService taskBlueprintService;
    private final TaskExecutor taskExecutor;
    private final String resultFilePath;

    public Task findById(String id) {
        return taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);
    }

    public PageTask findAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public String executeCommand(TaskCommand taskCommand, String taskId) {
        if (taskCommand.equals(TaskCommand.EXECUTE)) {
            return execute(taskId);
        }
        throw new CommandNotSupportedException();
    }

    private String execute(String taskId) {
        Task task = updateTaskStatus(taskId, SUBMITTED);
        taskExecutor.exec(task);
        return task.getStatus().name();
    }

    public List<String> getTaskFilesList(String taskId) {
        return taskWorkspace.getFilesList(getWorkspacePath(taskId));
    }

    public byte[] readTaskFile(String taskId, String filePath) {
        return taskWorkspace.readFile(getWorkspacePath(taskId), filePath);
    }

    public void commitTaskChanges(String taskId) {
        taskWorkspace.commitChanges(getWorkspacePath(taskId));
    }

    public byte[] readTaskResults(String taskId) {
        return readTaskFile(taskId, resultFilePath);
    }

    public Task updateTaskStatus(String taskId, TaskStatus newStatus) {
        var task = findTaskById(taskId).withStatus(newStatus);
        taskRepository.update(task);
        return task;
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

    public void deleteTask(String taskId) {
        var task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        taskWorkspace.deleteWorkspace(task.getWorkspaceUrl());
        taskRepository.remove(taskId);
    }

    public TaskStatus getTaskStatus(String taskId) {
        return findTaskById(taskId).getStatus();
    }

    private Task findTaskById(String taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
    }

    private String getWorkspacePath(String taskId) {
        return findTaskById(taskId).getWorkspaceUrl();
    }

    public void writeAndCommitTask(String taskId, int fileId, byte[] bytes) {
        String filePath = getTaskFilesList(taskId).get(fileId);
        taskWorkspace.writeFile(getWorkspacePath(taskId), filePath, bytes);
        commitTaskChanges(taskId);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

//    public PageTask findAllByUserId(final Pageable pageable, final String userId) {
//        final List<Task> userTasks = taskRepository.findAll()
//                .stream()
//                .filter(task -> assigmentService.isTaskAssignedToUser(userId, task.getId()))
//                .collect(Collectors.toList());
//
//        Page<Task> pages = new PageImpl<Task>(userTasks, pageable, userTasks.size());
//
//        return new PageTask(pages.getContent(), pages.getNumber(), pages.getTotalPages(), pages.getTotalElements());
//    }
}
