package com.zw.dao.git;

import com.zw.dao.UserDao;
import com.zw.model.Note;
import com.zw.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class GitUserDao implements UserDao {
    private CredentialsProvider credentialsProvider;
    private String notesDir;
    private String dbRepoUrl;
    private Git git;
    private static final String MAIN_BRANCH = "main";

    public GitUserDao(CredentialsProvider credentialsProvider, String cloneDir, String gitRepoUrl) throws GitAPIException, IOException {
        this.credentialsProvider = credentialsProvider;
        this.notesDir = cloneDir + File.separator + "notes";
        this.dbRepoUrl = gitRepoUrl;
        // if haven't, lets clone repo
        this.git = cloneDBRepo();
    }

    private Git cloneDBRepo() throws GitAPIException, IOException {
        if (Files.exists(Paths.get(notesDir, ".git"))) {
            log.info("Repo already cloned to {}", notesDir);
            Repository repository = new FileRepositoryBuilder().setGitDir(Paths.get(notesDir, ".git").toFile()).build();
            return new Git(repository);
        } else {
            FileUtils.createDir(notesDir);
            Git git = Git.cloneRepository().setCredentialsProvider(credentialsProvider).setURI(dbRepoUrl).setDirectory(new File(notesDir)).setBranch(MAIN_BRANCH).call();
            return git;
        }
    }

    private void pull() throws GitAPIException {
        PullResult pr = git.pull().setCredentialsProvider(credentialsProvider).setRemoteBranchName(MAIN_BRANCH).call();
    }

    private void addAll() throws GitAPIException {
        git.add().addFilepattern(".").call();
    }

    private void commitAndPush(Note note) throws GitAPIException {
        git.add().addFilepattern(".").call();
        git.commit().setMessage("Updated by " + note.getUser().getUserId()).call();
        // note that it sometimes connection failed in mainland china
        git.push().setRemote("origin").setRefSpecs(new RefSpec(MAIN_BRANCH)).setCredentialsProvider(credentialsProvider).call();
    }
}
