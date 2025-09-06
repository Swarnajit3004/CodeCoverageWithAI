package com.supportmethods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandTool {

    public static String execCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            return "❌ Error executing command: " + e.getMessage();
        }
        return output.toString();
    }
}
