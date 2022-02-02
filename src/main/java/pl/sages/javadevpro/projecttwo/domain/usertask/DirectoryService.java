package pl.sages.javadevpro.projecttwo.domain.usertask;

import pl.sages.javadevpro.projecttwo.domain.task.Task;

import java.io.File;
import java.util.List;

public interface DirectoryService {

    String createDirectoryForUserTask(Task task, String userEmail);

    List<String> readListOfAvailableFilesForUserTask(String userEmail, String taskId);

    void uploadFileForUserTask(String userEmail, String taskId, String fileId, byte[] bytes);

    File takeFileFromUserTask(String userEmail, String taskId, String fileId);

    String getPathToUserTask(String userEmail, String taskId);

    File getResultFile(String userEmail, String taskId);
}
