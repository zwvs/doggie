package com.zw.dao.note;

import com.zw.model.Note;
import com.zw.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.zw.util.DateUtils.formatDefault;
import static com.zw.util.DateUtils.now;
import static com.zw.util.GitUtils.getRepo;
import static com.zw.util.JsonUtils.toJson;

@Slf4j
public class GitNoteDao implements NoteDao {
    private CredentialsProvider credentialsProvider;
    private String cloneDir;
    private String dbRepoUrl;
    private Git git;

    public GitNoteDao(CredentialsProvider credentialsProvider, String cloneDir, String gitRepoUrl) throws GitAPIException, IOException {
        this.credentialsProvider = credentialsProvider;
        this.cloneDir = cloneDir;
        this.dbRepoUrl = gitRepoUrl;
        // if haven't, lets cone repo
        this.git = cloneDBRepo();
    }

    @Override
    public List<Note> getNotes(String userId) {
        try {
//            Status status = git.status().call();
//            log.info("status: " + toJson(status));
            Iterable<RevCommit> logs = git.log().call();
            log.info("logs: " + toJson(logs));
        } catch (GitAPIException e) {
            log.error("Exception happened in getNotes", e);
        }
        return null;
    }

    @Override
    public List<Note> getNotes(String userId, long since) {
        return null;
    }

    @Override
    public void saveNote(Note note) {
        note = new Note();
        note.setUserId("weiz");
        note.setCreateTime(now());
        note.setCreateTimeReadable(formatDefault(note.getCreateTime()));
        String userNoteFile = Paths.get(cloneDir, note.getUserId() + ".json").toString();
//        FileUtils.createDir(userNoteFolder);
        try {
            FileUtils.writeToFile(userNoteFile, toJson(note));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            git.add().addFilepattern(".").call();
            git.commit().setMessage("Updated by " + note.getUserId()).call();
            git.push().setRemote("origin").setRefSpecs(new RefSpec("main")).setCredentialsProvider(credentialsProvider).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    private Git cloneDBRepo() throws GitAPIException, IOException {
        if (Files.exists(Paths.get(cloneDir, ".git"))) {
            log.info("Repo already cloned to {}", cloneDir);
            Repository repository = new FileRepositoryBuilder().setGitDir(Paths.get(cloneDir, ".git").toFile()).build();
            return new Git(repository);
        } else {
            FileUtils.createDir(cloneDir);
            Git git = Git.cloneRepository().setCredentialsProvider(credentialsProvider).setURI(dbRepoUrl).setDirectory(new File(cloneDir)).setBranch("main").call();
            return git;
        }
    }
}
