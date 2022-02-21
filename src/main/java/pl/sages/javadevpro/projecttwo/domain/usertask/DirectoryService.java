package pl.sages.javadevpro.projecttwo.domain.usertask;

import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprint;

import java.io.File;
import java.util.List;

public interface DirectoryService {

    String createDirectoryForUserTask(TaskBlueprint taskBlueprint, String userId);

    List<String> readListOfAvailableFilesForUserTask(String userId, String taskId);

    void uploadFileForUserTask(String userId, String taskId, String fileId, byte[] bytes);

    File takeFileFromUserTask(String userId, String taskId, String fileId);

    String getPathToUserTask(String userId, String taskId);

    File getResultFile(String userId, String taskId);
}
