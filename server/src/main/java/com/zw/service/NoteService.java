package com.zw.service;

import com.zw.dao.NoteDao;
import com.zw.model.Note;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class NoteService {

    @Autowired
    NoteDao noteDao;

    public Note getNote(String userId) throws GitAPIException {
        return noteDao.getNote(userId);
    }

    public void updateNote(Note note) throws GitAPIException, IOException {
        noteDao.updateNote(note);
    }
}
