package pl.sages.javadevpro.projecttwo.domain.usertask;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import pl.sages.javadevpro.projecttwo.domain.exception.RecordNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.task.*;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;
import pl.sages.javadevpro.projecttwo.domain.usertask.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Log
@RequiredArgsConstructor
public class UserTaskService {

    private final GitService gitService;
    private final DirectoryService directoryService;
    private final UserService userService;
    private final TaskBlueprintService taskBlueprintService;
    private final TaskExecutor taskExecutor;

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


    public List<String> readListOfAvailableFilesForUserTask (String userId, String taskId) {
        return directoryService.readListOfAvailableFilesForUserTask(userId, taskId);
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



    public void updateUserTaskInDB(Task task, User user) {
//        if (user.getTasks() == null) {
//            user.setTasks(new ArrayList<>());
//        }
//        List<UserTask> tasks = user.getTasks();
//        int indexOfUserTaskToUpdate = tasks.indexOf(userTask);
//        tasks.set(indexOfUserTaskToUpdate, userTask);
//        userService.update(user);
    }

    private Task createFromTask(TaskBlueprint taskBlueprint, String userId) {
//        Task task = new Task(taskBlueprint.getId(), taskBlueprint.getName(), taskBlueprint.getDescription(),
//                copyRepositoryToUserFolder(taskBlueprint, userId),  TaskStatus.NOT_STARTED);
//
//        return task;
        return null;
    }

    public Task assignTask(String userId, String taskId) {
        User user = userService.findById(userId);
        TaskBlueprint taskBlueprint = taskBlueprintService.findBy(taskId);

        Task task;
        task = createFromTask(taskBlueprint, user.getId());

        addUserTaskToDB(task, user);
        return task;
    }

    private void addUserTaskToDB(Task task, User user) {
//        if (user.getTasks() == null) {
//            user.setTasks(new ArrayList<>());
//        }
//        user.getTasks().add(userTask);
//        userService.update(user);
    }

    private String copyRepositoryToUserFolder(TaskBlueprint taskBlueprint, String userId) {
        String destinationFolderPath = directoryService.createDirectoryForUserTask(taskBlueprint, userId);
        gitService.cloneTask(taskBlueprint.getRepositoryUrl(), destinationFolderPath);
        return destinationFolderPath;
    }



}
