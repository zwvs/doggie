package com.zw.util;

public class GitUtils {

    public static String getRepo(String repoUrl) {
        return repoUrl.substring(repoUrl.lastIndexOf("/") + 1, repoUrl.lastIndexOf(".git"));
    }
}
