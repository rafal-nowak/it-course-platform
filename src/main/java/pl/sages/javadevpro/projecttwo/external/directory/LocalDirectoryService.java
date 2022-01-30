package pl.sages.javadevpro.projecttwo.external.directory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.web.multipart.MultipartFile;
import pl.sages.javadevpro.projecttwo.domain.exception.ResourceNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.task.Task;
import pl.sages.javadevpro.projecttwo.domain.usertask.DirectoryService;
import pl.sages.javadevpro.projecttwo.external.directory.task.TaskDefinition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<String> readListOfAvailableFilesForUserTask(String userEmail, String taskId) {
        String convertedEmail = removeSymbolsFromEmail(userEmail);
        String path = "userTasks/" + convertedEmail + "/" + taskId + "/task_definition.yml";

        var mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        TaskDefinition taskDefinition = null;
        try {
            taskDefinition = mapper.readValue(new File(path), TaskDefinition.class);
        } catch (IOException e) {
            throw new ResourceNotFoundException("Task " + taskId + " was not assigned to user " + userEmail);
        }

        List<String> strings = taskDefinition
                .getFilesToBeDeliveredToUser()
                .stream()
                .map(o -> o.getFile())
                .collect(Collectors.toList());

        return strings;
    }

    @Override
    public void uploadFileForUserTask(String userEmail, String taskId, String fileId, MultipartFile file) {
        File myFile = takeFileFromUserTask(userEmail, taskId, fileId);

        try {
            myFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(myFile);
            fos.write(file.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public File takeFileFromUserTask(String userEmail, String taskId, String fileId) {
        List<String> listOfAvailableFilesForUserTask = readListOfAvailableFilesForUserTask(userEmail, taskId);
        String convertedEmail = removeSymbolsFromEmail(userEmail);

        if (Integer.parseInt(fileId) > listOfAvailableFilesForUserTask.size()) {
            throw new ResourceNotFoundException("File " + fileId + " was not assigned to " + taskId + " user task " + userEmail);
        }

        String relatedPathToSelectedFile = listOfAvailableFilesForUserTask.get(Integer.parseInt(fileId) - 1);

        String path = "userTasks/" + convertedEmail + "/" + taskId + "/" + relatedPathToSelectedFile;

        return new File(path);
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

    private String removeSymbolsFromEmail(String email) {
        return email.replace("@","").replace(".","");
    }
}
