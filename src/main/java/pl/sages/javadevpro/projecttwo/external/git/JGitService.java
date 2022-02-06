package pl.sages.javadevpro.projecttwo.external.git;

import lombok.SneakyThrows;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Component;
import pl.sages.javadevpro.projecttwo.domain.usertask.GitService;

import java.io.File;
import java.util.Set;

@Component
public class JGitService implements GitService {

    @SneakyThrows
    @Override
    public File cloneTask(String repositoryPath, String destinationPath) {
        File repo = new File(destinationPath);
        Git git = Git.cloneRepository()
                .setURI(repositoryPath)
                .setDirectory(repo)
                .call();
        unlinkRemotes(git);
        git.close();
        return git.getRepository().getDirectory();
    }

    @SneakyThrows
    @Override
    public  void commitTask(String path) {
        Git git = Git.open(new File(path));

        AddCommand add = git.add();
        add.addFilepattern(path).call();

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
