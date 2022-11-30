package com.zw.model;

import lombok.Data;

import java.util.List;

@Data
public class Chapter {
    // The background and context of the situation
    private String summary;
    // What have been learned
    private String lesson;
    // The lessons consist of this chapter
    private List<Story> lessons;
    // The success story thanks to the lessons
    private List<Story> successStories;
}
