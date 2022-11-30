package com.zw.service;

import com.zw.dao.NoteDao;
import com.zw.model.Note;
import com.zw.model.User;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static com.zw.model.Note.newNote;

public class UserService {

    @Autowired
    private NoteDao noteDao;

    public void createUserNote(User user) throws GitAPIException, IOException {
        Note note = newNote(user);
        noteDao.updateNote(note);
    }
}
