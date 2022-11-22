package com.zw.model;

import lombok.Data;

@Data
public class Note {
    private String userId;
    private long createTime;
    private long lastReadTime;
    private Lesson lesson;
}
