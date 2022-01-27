package pl.sages.javadevpro.projecttwo.external.directory;

import pl.sages.javadevpro.projecttwo.domain.task.Task;
import pl.sages.javadevpro.projecttwo.domain.usertask.DirectoryService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalDirectoryService implements DirectoryService {

    @Override
    public String createDirectoryForUserTask(Task task, String userEmail) {
        String path = getLocalPath(userEmail,task.getId());

        Path folderPath = Path.of(path);
        if (!Files.isDirectory(folderPath)) {
            try {
                Files.createDirectories(folderPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path;
    }

    @Override
    public File getResultFile(String userEmail, String taskId) {
        String path = getLocalPath(userEmail,taskId);
        return new File(path + "/test_summary.txt");
    }

    private String getLocalPath(String userEmail, String taskId) {
        String convertedEmail = userEmail.replace("@","").replace(".","");
        return "userTasks/" + convertedEmail + "/" + taskId;
    }
}
