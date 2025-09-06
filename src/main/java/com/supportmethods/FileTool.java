package com.supportmethods;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileTool {

    public static String readFile(String filePath) {
        try {
            Path path = Path.of(filePath);
            return Files.readString(path);
        } catch (IOException e) {
            return "❌ Error reading file: " + e.getMessage();
        }
    }
}
