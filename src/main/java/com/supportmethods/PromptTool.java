package com.supportmethods;

import java.util.HashMap;
import java.util.Map;

public class PromptTool {

    public static Map<String, String> getPrompt(String name, String user) {
        Map<String, String> result = new HashMap<>();

        if (name == null || name.isBlank()) {
            result.put("error", "Prompt name is missing or null");
            return result;
        }

        switch (name.toLowerCase()) {
            case "repogreeting":
                result.put("text", "👋 Hello " + user + ", welcome to MCP inside CodeCoverageWithAI!");
                break;

            case "abcd":
                result.put("text", "🔔 Hi " + user + ", you triggered the 'abcd' prompt!");
                break;

            default:
                result.put("error", "Unknown prompt: " + name);
        }

        return result;
    }
}
