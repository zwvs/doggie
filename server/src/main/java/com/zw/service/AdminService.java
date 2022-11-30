package com.zw.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminService {

    public boolean isAdmin(String userName) {
        return "weiz".equals(userName);
    }

}
