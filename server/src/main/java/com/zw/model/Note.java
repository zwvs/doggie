package com.zw.model;

import com.zw.util.DateUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static com.zw.util.DateUtils.now;

@Data
public class Note {
    private User user;
    private long createTime;
    private String createTimeReadable;
    private long lastReadTime;
    private List<Chapter> chapters;

    public static Note newNote(User user) {
        Note ret = new Note();
        ret.setUser(user);
        long currentTime = now();
        ret.setCreateTime(currentTime);
        ret.setCreateTimeReadable(DateUtils.formatDefault(currentTime));
        ret.setLastReadTime(currentTime);
        ret.setChapters(new ArrayList<>());
        return ret;
    }
}
