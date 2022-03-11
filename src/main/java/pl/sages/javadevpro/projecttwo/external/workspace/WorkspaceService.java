package pl.sages.javadevpro.projecttwo.external.workspace;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import pl.sages.javadevpro.projecttwo.domain.task.Workspace;
import pl.sages.javadevpro.projecttwo.external.directory.task.FileToBeDeliveredToUser;
import pl.sages.javadevpro.projecttwo.external.directory.task.TaskDefinition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WorkspaceService implements Workspace {

    private final String baseWorkspace;
    private final String taskDefinitionFile;
    private final String taskSummaryResult;

    @Override
    @SneakyThrows(GitAPIException.class)
    public String createWorkspace(String sourceRepositoryUrl) {
        String destinationPath = baseWorkspace + UUID.randomUUID();
        File repo = new File(destinationPath);
        Git git;
        try {
            git = Git.cloneRepository()
                    .setURI(sourceRepositoryUrl)
                    .setDirectory(repo)
                    .call();
        } catch (JGitInternalException e) {
            throw new RepositoryAlreadyResidesInDestinationFolderException("Repository already resides in destination folder.", e);
        }
        unlinkRemotes(git);
        git.close();
        return destinationPath;
    }

    @Override
    public List<String> getFilesList(String rootPathUrl) {
        String path = rootPathUrl + taskDefinitionFile;

        var mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        TaskDefinition taskDefinition;
        try {
            taskDefinition = mapper.readValue(new File(path), TaskDefinition.class);
        } catch (IOException e) {
            throw new TaskWasNotCreatedProperlyException("Task was not created properly");
        }

        return taskDefinition
                .getFilesToBeDeliveredToUser()
                .stream()
                .map(FileToBeDeliveredToUser::getFile)
                .collect(Collectors.toList());
    }

    @Override
    public void writeFile(String rootPathUrl, String path, byte[] bytes) {
        String fullPath = rootPathUrl + "/" + path;
        File file = new File(fullPath);

        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] readFile(String rootPathUrl, String path) {
        String fullPath = rootPathUrl + "/" + path;

        try {
          return Files.readAllBytes(Paths.get(fullPath));
        } catch (IOException e) {
            throw new FileWasNotFoundException("File Was Not Found");
        }
    }

    @SneakyThrows
    @Override
    public void commitChanges(String rootPathUrl) {
        Git git = Git.open(new File(rootPathUrl));

        AddCommand add = git.add();
        add.addFilepattern(rootPathUrl).call();

        CommitCommand commit = git.commit();
        commit.setMessage("update commit").call();
    }

    private void unlinkRemotes(Git git) {
        Set<String> remoteNames = git.getRepository().getRemoteNames();
        remoteNames.forEach(g -> {
            try {
                git.remoteRemove().setRemoteName(g).call();
            } catch (GitAPIException e) {
                e.printStackTrace();
            }
        });
    }
}
