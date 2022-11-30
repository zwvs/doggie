package com.zw.model;

import lombok.Data;

@Data
public class Story {
    // unique name of this story, there could be several lessons from one story
    private String name;
    // the situation of the story
    private String situation;
    // when did this story happened
    private long timestamp;
    // what was the result this story caused
    private String result;
}
