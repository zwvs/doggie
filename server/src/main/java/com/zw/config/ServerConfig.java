package com.zw.config;

import com.zw.service.AdminService;
import com.zw.service.NoteService;
import com.zw.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    @Bean
    public AdminService adminService() {
        return new AdminService();
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public NoteService noteService() {
        return new NoteService();
    }
}
