package com.zw.dao.note;

import com.zw.model.Note;

import java.util.List;

public interface NoteDao {
    List<Note> getNotes(String userId);

    List<Note> getNotes(String userId, long since);

    void saveNote(Note note);

}
