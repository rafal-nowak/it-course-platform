package pl.sages.javadevpro.projecttwo.external.workspace;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.RequiredArgsConstructor;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.springframework.web.multipart.MultipartFile;
import pl.sages.javadevpro.projecttwo.domain.task.Workspace;
import pl.sages.javadevpro.projecttwo.external.directory.task.FileToBeDeliveredToUser;
import pl.sages.javadevpro.projecttwo.external.directory.task.TaskDefinition;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WorkspaceService implements Workspace {

    private final String baseWorkspace;
    private final String taskDefinitionFile;
    private final String resultFile;

    @Override
    public String createWorkspace(String sourceRepositoryUrl) {
        String absoluteDestinationPath = crateDirectoryPath();
        cloneRepository(sourceRepositoryUrl, absoluteDestinationPath);
        return absoluteDestinationPath;
    }

    @Override
    public List<String> getFilesList(String rootPathUrl) {
        String path = java.nio.file.Paths.get(rootPathUrl, taskDefinitionFile).toString();

        var mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        TaskDefinition taskDefinition;
        try {
            taskDefinition = mapper.readValue(new File(path), TaskDefinition.class);
        } catch (IOException e) {
            throw new TaskWasNotCreatedProperlyException();
        }

        return taskDefinition
                .getFilesToBeDeliveredToUser()
                .stream()
                .map(FileToBeDeliveredToUser::getFile)
                .collect(Collectors.toList());
    }

    @Override
    public void writeFile(String rootPathUrl, String path, byte[] bytes) {
        String fullPath = java.nio.file.Paths.get(rootPathUrl, path).toString();

        try {
            Files.write(Paths.get(fullPath), bytes);
        } catch (IOException e) {
            throw new FileWasNotFoundException();
        }
    }

    @Override
    public byte[] readFile(String rootPathUrl, String path) {
        String fullPath = java.nio.file.Paths.get(rootPathUrl, path).toString();

        try {
            return Files.readAllBytes(Paths.get(fullPath));
        } catch (IOException e) {
            throw new FileWasNotFoundException();
        }
    }

    @Override
    public void commitChanges(String rootPathUrl) {
        CommitCommand commit;
        try (Git git = Git.open(new File(rootPathUrl))) {

            AddCommand add = git.add();
            add.addFilepattern(".").call();

            commit = git.commit();
            commit.setMessage("update commit").call();
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
            throw new RepositoryWasNotFoundException();
        }
    }

    @Override
    public void deleteWorkspace(final String workspaceUrl) {
        deleteDirectory(workspaceUrl);
    }

    private void deleteDirectory(String path) {
        var pathToBeDeleted = java.nio.file.Path.of(path);
        try {
            Files.walk(pathToBeDeleted)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            throw new RepositoryWasNotFoundException();
        }
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

    private String crateDirectoryPath() {
        Path generatedDirectoryPath = java.nio.file.Paths.get(".", baseWorkspace, UUID.randomUUID().toString());
        return generatedDirectoryPath.toAbsolutePath().toString();
    }

    private void cloneRepository(String sourceRepositoryUrl, String destinationPath){
        File repo = new File(destinationPath);
        Git git;
        try {
            git = Git.cloneRepository()
                    .setURI(sourceRepositoryUrl)
                    .setDirectory(repo)
                    .call();
        } catch (JGitInternalException | GitAPIException e) {
            throw new RepositoryAlreadyResidesInDestinationFolderException(e);
        }
        unlinkRemotes(git);
        git.close();
    }
}
