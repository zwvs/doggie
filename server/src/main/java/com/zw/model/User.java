package com.zw.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String userId;
    private String fullName;
    private String email;
    private Gender gender;
}
