package com.zw.dao;

import com.zw.model.Note;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

public interface NoteDao {
    Note getNote(String userId) throws GitAPIException;

    void updateNote(Note note) throws IOException, GitAPIException;

}
