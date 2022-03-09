package pl.sages.javadevpro.projecttwo.external.workspace;

import pl.sages.javadevpro.projecttwo.domain.task.Workspace;

import java.util.List;

public class WorkspaceService implements Workspace {

    @Override
    public String createWorkspace(String sourceRepositoryUrl) {
        return null;
    }

    @Override
    public List<String> getFilesList(String rootPathUrl) {
        return null;
    }

    @Override
    public void writeFile(String rootPathUrl, String path, byte[] bytes) {

    }

    @Override
    public byte[] readFile(String rootPathUrl, String path) {
        return new byte[0];
    }

    @Override
    public void commitChanges(String rootPathUrl) {

    }
}
