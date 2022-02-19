package pl.sages.javadevpro.projecttwo.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import pl.sages.javadevpro.projecttwo.domain.exception.RecordNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprint;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintService;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;
import pl.sages.javadevpro.projecttwo.domain.usertask.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log
@RequiredArgsConstructor
public class UserTaskService {

    private final GitService gitService;
    private final DirectoryService directoryService;
    private final UserService userService;
    private final TaskBlueprintService taskBlueprintService;
    private final UserTaskExecutor userTaskExecutor;

    public String exec(String userEmail, String taskId) {
        // FIXME
//        User user = userService.findBy(userEmail);
//        List<UserTask> tasks = user.getTasks();
//        if (tasks == null) {
//            throw new RecordNotFoundException("Task is not assigned to user");
//        }
//        UserTask taskToSend = tasks.stream()
//            .filter(task -> task.getId().equals(taskId))
//            .findFirst()
//            .orElseThrow(() -> new RecordNotFoundException("Task is not assigned to user"));
//            taskToSend.setTaskStatus(TaskStatus.SUBMITTED);
//        updateUserTaskInDB(taskToSend, user);
//        return userTaskExecutor.exec(taskToSend);
        return null;
    }

    public UserTask assignTask(String userEmail, String taskId) {
        User user = userService.findBy(userEmail);
        TaskBlueprint taskBlueprint = taskBlueprintService.findBy(taskId);

        UserTask userTask;
        userTask = createFromTask(taskBlueprint, user.getEmail());

        addUserTaskToDB(userTask, user);
        return userTask;
    }

    public List<String> readListOfAvailableFilesForUserTask (String userEmail, String taskId) {
        return directoryService.readListOfAvailableFilesForUserTask(userEmail, taskId);
    }

    public void uploadFileForUserTask(String userEmail, String taskId, String fileId, byte[] bytes) {
        directoryService.uploadFileForUserTask(userEmail, taskId, fileId, bytes);
    }

    public File takeFileFromUserTask(String userEmail, String taskId, String fileId) {
        return directoryService.takeFileFromUserTask(userEmail, taskId, fileId);
    }

    public void commitTask(String userEmail, String taskId) {
        gitService.commitTask(directoryService.getPathToUserTask(userEmail, taskId));
    }

    public String getUserTaskStatusSummary(String userEmail, String taskId) {
        File resultFile = directoryService.getResultFile(userEmail, taskId);
        try {
            return Files.readAllLines(resultFile.toPath()).stream().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            log.warning(e.getMessage());
            throw new RecordNotFoundException("Not found");
        }
    }

    public void updateUserTaskInDB(UserTask userTask, User user) {
//        if (user.getTasks() == null) {
//            user.setTasks(new ArrayList<>());
//        }
//        List<UserTask> tasks = user.getTasks();
//        int indexOfUserTaskToUpdate = tasks.indexOf(userTask);
//        tasks.set(indexOfUserTaskToUpdate, userTask);
//        userService.update(user);
    }

    private UserTask createFromTask(TaskBlueprint taskBlueprint, String userEmail) {
        UserTask userTask = new UserTask();
        userTask.setUserTaskFolder(copyRepositoryToUserFolder(taskBlueprint, userEmail));
        userTask.setId(taskBlueprint.getId());
        userTask.setName(taskBlueprint.getName());
        userTask.setDescription(taskBlueprint.getDescription());
        userTask.setTaskStatus(TaskStatus.NOT_STARTED);
        userTask.setUserEmail(userEmail);
        return userTask;
    }

    private void addUserTaskToDB(UserTask userTask, User user) {
//        if (user.getTasks() == null) {
//            user.setTasks(new ArrayList<>());
//        }
//        user.getTasks().add(userTask);
//        userService.update(user);
    }

    private String copyRepositoryToUserFolder(TaskBlueprint taskBlueprint, String userEmail) {
        String destinationFolderPath = directoryService.createDirectoryForUserTask(taskBlueprint, userEmail);
        gitService.cloneTask(taskBlueprint.getRepositoryUrl(), destinationFolderPath);
        return destinationFolderPath;
    }

}
