package pl.sages.javadevpro.projecttwo.domain.task;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Workspace {

    String createWorkspace(String sourceRepositoryUrl);

    List<String> getFilesList(String rootPathUrl);

    void writeFile(String rootPathUrl, String path, byte[] bytes);

    byte[] readFile(String rootPathUrl, String path);

    void commitChanges(String rootPathUrl);

    void deleteWorkspace(String workspaceUrl);
}
