package com.zw.model;

import lombok.Data;

@Data
public class User {
    private String userId;
    private String fullName;
    private String email;
    private Gender gender;
}
