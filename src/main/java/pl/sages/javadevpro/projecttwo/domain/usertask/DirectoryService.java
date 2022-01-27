package pl.sages.javadevpro.projecttwo.domain.usertask;

import pl.sages.javadevpro.projecttwo.domain.task.Task;
import pl.sages.javadevpro.projecttwo.domain.user.User;

import java.io.File;

public interface DirectoryService {

    String createDirectoryForUserTask(Task task, String userEmail);

    File getResultFile(String userEmail, String taskId);
}
