package com.zw.rest;

import com.zw.model.Gender;
import com.zw.model.RestResponse;
import com.zw.model.User;
import com.zw.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.zw.model.RestResponse.forError;
import static com.zw.model.RestResponse.forSuccess;
import static java.lang.String.format;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserRest {

    @Autowired
    private UserService userService;

    @GetMapping("/create")
    public RestResponse create(HttpServletRequest request, @RequestParam("userId") String userId, @RequestParam("fullName") String fullName,
                               @RequestParam("email") String email, @RequestParam("gender") String gender) {
//        String userName = request.getUserPrincipal().getName();
        User user = new User(userId, fullName, email, Gender.valueOf(gender));
        try {
            userService.createUserNote(user);
            log.info(format("Successfully created note for", userId));
            return forSuccess().withPayload(user);
        } catch (Exception e) {
            return forError().withMsg(getStackTrace(e)).withPayload(user);
        }
    }
}
