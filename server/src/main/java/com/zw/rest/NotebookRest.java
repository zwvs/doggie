package com.zw.rest;

import com.zw.model.Note;
import com.zw.model.RestResponse;
import com.zw.service.AdminService;
import com.zw.service.NoteService;
import com.zw.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.zw.model.RestResponse.forError;
import static com.zw.model.RestResponse.forSuccess;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@RestController
@RequestMapping("/notebook")
@Slf4j
public class NotebookRest {
    @Autowired
    NoteService noteService;

    @GetMapping("/read")
    public RestResponse read(HttpServletRequest request, @RequestParam("userId") String userId) {
//        String userName = request.getUserPrincipal().getName();
        try {
            Note note = noteService.getNote(userId);
            return forSuccess().withPayload(note);
        } catch (Exception e) {
            return forError().withMsg(getStackTrace(e));
        }
    }

    //    @PostMapping("/write") TODO use post when have UI ready
    @GetMapping("/write")
    public RestResponse write(HttpServletRequest request, @RequestParam("noteJson") String noteJson) {
        Note note = JsonUtils.fromJson(noteJson, Note.class);
//        String userName = request.getUserPrincipal().getName();
        try {
            noteService.updateNote(note);
            return forSuccess().withPayload(note);
        } catch (Exception e) {
            return forError().withMsg(getStackTrace(e));
        }
    }
}
