package com.zw.rest;

import com.zw.dao.NoteDao;
import com.zw.service.AdminService;
import com.zw.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminRest {

    @Autowired
    AdminService adminService;
    @Autowired
    EmailService emailService;
    @Autowired
    NoteDao noteDao;

    @GetMapping("/hello")
    String helloApi(HttpServletRequest request) {
        String userName = request.getUserPrincipal().getName();
        String msg = String.format("%s accessing hello, is admin %s", userName, adminService.isAdmin(userName));
        log.info(msg);

//        noteDao.getNotes("weiz");
//        noteDao.updateNote(null);
        return msg;
    }

    @GetMapping("/testmail")
    String helloApi2(HttpServletRequest request) {
        String userName = request.getUserPrincipal().getName();
        String msg = String.format("%s accessing hello, is admin %s", userName, adminService.isAdmin(userName));
        log.info(msg);

//        noteDao.getNotes("weiz");
//        noteDao.saveNote(null);
        emailService.sendMailTest();
        return msg;
    }
}