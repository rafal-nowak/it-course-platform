package pl.sages.javadevpro.projecttwo.domain.usertask;

import org.springframework.web.multipart.MultipartFile;
import pl.sages.javadevpro.projecttwo.domain.task.Task;

import java.io.File;
import java.util.List;

public interface DirectoryService {

    String createDirectoryForUserTask(Task task, String userEmail);

    List<String> readListOfAvailableFilesForUserTask(String userEmail, String taskId);

    void uploadFileForUserTask(String userEmail, String taskId, String fileId, MultipartFile file);

    File takeFileFromUserTask(String userEmail, String taskId, String fileId);

    File getResultFile(String userEmail, String taskId);
}
