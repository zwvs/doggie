package com.zw.config;

import com.zw.dao.git.GitNoteDao;
import com.zw.dao.NoteDao;
import com.zw.service.EmailService;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class DaoConfig {

    @Value("${git.db.username}")
    private String gitUsername;

    @Value("${git.db.password}")
    private String gitPassword;

    @Value("${git.db.cloneDir}")
    private String gitCloneDir;

    @Value("${git.db.repoUrl}")
    private String gitRepoUrl;

    @Value("${mail.username}")
    private String mailUserName;

    @Value("${mail.password}")
    private String mailPassword;

    @Bean
    public NoteDao noteDao() throws GitAPIException, IOException {
        CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(gitUsername, gitPassword);
        return new GitNoteDao(credentialsProvider, gitCloneDir, gitRepoUrl);
    }

    @Bean
    public EmailService emailService() {
        return new EmailService(mailUserName, mailPassword);
    }
}
