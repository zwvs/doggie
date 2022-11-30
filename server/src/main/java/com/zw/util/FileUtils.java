package com.zw.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FileUtils {

    public static boolean fileExists(String path) {
        return Files.exists(Paths.get(path));
    }

    public static void createDir(String dir) {
        boolean res = new File(dir).mkdir();
        if (res) {
            log.info("Successfully created directory {}", dir);
        } else {
            log.info("Directory {} already exists, skip creating", dir);
        }
    }

    public static void writeToFile(String path, String content) throws IOException {
        FileWriter file = new FileWriter(path);
        file.write(content);
        file.close();
    }

    public static String readFile(Path path) throws IOException {
        return Files.readString(path);
    }
}
