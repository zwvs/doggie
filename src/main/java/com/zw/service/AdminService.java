package com.zw.service;

import com.zw.aop.SampleAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminService {

    @SampleAnnotation
    public boolean isAdmin(String userName) {
        return "weiz".equals(userName);
    }

}
