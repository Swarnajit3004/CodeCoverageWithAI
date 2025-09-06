package com.mcpjava;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;

@ClientEndpoint
public class MCPJavaClient {

    private static Session session;
    private static ObjectMapper mapper = new ObjectMapper();
    private static int requestId = 1;

    public static void main(String[] args) throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        session = container.connectToServer(MCPJavaClient.class, new URI("ws://localhost:3000"));

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Choose action (prompt/tool/exit): ");
            String action = scanner.nextLine();

            switch (action.toLowerCase()) {
                case "prompt":
                    System.out.print("Enter prompt name: ");
                    String promptName = scanner.nextLine();
                    System.out.print("Enter user name: ");
                    String userName = scanner.nextLine();
                    sendPrompt(promptName, userName);
                    break;

                case "tool":
                    System.out.print("Enter tool name: ");
                    String toolName = scanner.nextLine();
                    System.out.print("Enter arg key: ");
                    String key = scanner.nextLine();
                    System.out.print("Enter arg value: ");
                    String value = scanner.nextLine();
                    sendTool(toolName, key, value);
                    break;

                case "exit":
                    running = false;
                    break;

                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }
        }

        scanner.close();
        session.close();
    }

    private static void sendPrompt(String promptName, String user) throws Exception {
        ObjectNode req = mapper.createObjectNode();
        req.put("jsonrpc", "2.0");
        req.put("id", requestId++);
        req.put("method", "prompts/get");

        ObjectNode params = mapper.createObjectNode();
        params.put("name", promptName);
        params.put("user", user);

        req.set("params", params);

        session.getAsyncRemote().sendText(req.toString());
    }

    private static void sendTool(String toolName, String argKey, String argValue) throws Exception {
        ObjectNode req = mapper.createObjectNode();
        req.put("jsonrpc", "2.0");
        req.put("id", requestId++);
        req.put("method", "tools/call");

        ObjectNode params = mapper.createObjectNode();
        params.put("tool", toolName);

        ObjectNode args = mapper.createObjectNode();
        args.put(argKey, argValue);

        params.set("arguments", args);
        req.set("params", params);

        session.getAsyncRemote().sendText(req.toString());
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("📩 MCP Message: " + message);
    }
}
