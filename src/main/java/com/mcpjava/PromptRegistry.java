package com.mcpjava;

import java.util.*;

public class PromptRegistry {
    private static final Map<String, String> prompts = new HashMap<>();

    static {
        prompts.put("repoGreeting", "Write a greeting message for user {user} working in this repo.");
        prompts.put("explainCode", "Explain the following Java code in simple terms:\n{code}");
    }

    public static Set<String> listPrompts() {
        return prompts.keySet();
    }

    public static String getPrompt(String name) {
        return prompts.get(name);
    }
}
