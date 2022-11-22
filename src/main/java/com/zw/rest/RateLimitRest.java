package com.zw.rest;

import com.zw.aop.LimitAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/rate-limit")
@Slf4j
public class RateLimitRest {

    @GetMapping("/hello")
    @LimitAnnotation(key = "hello", perSecond = 0.1, timeout = 100, timeunit = TimeUnit.SECONDS)
    String helloApi(HttpServletRequest request) {
        String userName = request.getUserPrincipal().getName();
        String msg = String.format("%s accessing hello", userName);
        log.info(msg);
        return msg;
    }
}