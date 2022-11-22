package com.zw.rest;

import com.zw.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/notebook")
@Slf4j
public class NotebookRest {
    @Autowired
    AdminService adminService;

    @GetMapping("/read")
    String read(HttpServletRequest request) {
        String userName = request.getUserPrincipal().getName();
        String msg = String.format("%s accessing hello, is admin %s", userName, adminService.isAdmin(userName));
        log.info(msg);
        return msg;
    }
}
