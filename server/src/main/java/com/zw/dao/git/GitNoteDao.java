package com.zw.dao.git;

import com.zw.dao.NoteDao;
import com.zw.model.Note;
import com.zw.util.FileUtils;
import com.zw.util.JsonUtils;
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
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.zw.util.FileUtils.readFile;
import static com.zw.util.JsonUtils.toJson;

@Slf4j
public class GitNoteDao implements NoteDao {
    private CredentialsProvider credentialsProvider;
    private String notesDir;
    private String dbRepoUrl;
    private Git git;
    private static final String MAIN_BRANCH = "main";

    public GitNoteDao(CredentialsProvider credentialsProvider, String cloneDir, String gitRepoUrl) throws GitAPIException, IOException {
        this.credentialsProvider = credentialsProvider;
        this.notesDir = cloneDir + File.separator + "notes";
        this.dbRepoUrl = gitRepoUrl;
        this.git = cloneDBRepo();
    }

    @Override
    public Note getNote(String userId) {
//        try {
////            Status status = git.status().call();
////            log.info("status: " + toJson(status));
////            Iterable<RevCommit> logs = git.log().call();
////            log.info("logs: " + toJson(logs));
//        } catch (GitAPIException e) {
//            log.error("Exception happened in getNotes", e);
//        }
        try {
            pull();
            Path path = Paths.get(notesDir, userId.trim() + ".json");
            String fileContent = readFile(path);
            return JsonUtils.fromJson(fileContent, Note.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateNote(Note note) {
        String userNoteFile = Paths.get(notesDir, note.getUser().getUserId() + ".json").toString();
//        FileUtils.createDir(userNoteFolder);
        try {
            FileUtils.writeToFile(userNoteFile, toJson(note));
            pull();
            addAll();
            commitAndPush(note);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
