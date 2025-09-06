package com.mcpjava;

import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@ClientEndpoint
public class MCPJavaClient {
    private Session session;
    private static final ObjectMapper mapper = new ObjectMapper();
    private static CountDownLatch latch = new CountDownLatch(1);
    private static int requestId = 1;

    public MCPJavaClient(URI serverUri) throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(this, serverUri);
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("✅ Connected to MCP server");
        sendInitialize();
    }

    private void sendInitialize() {
        ObjectNode init = mapper.createObjectNode();
        init.put("jsonrpc", "2.0");
        init.put("id", requestId++);
        init.put("method", "initialize");
        ObjectNode params = mapper.createObjectNode();
        ObjectNode clientInfo = mapper.createObjectNode();
        clientInfo.put("name", "JavaClient");
        clientInfo.put("version", "1.0");
        params.set("clientInfo", clientInfo);
        init.set("params", params);
        session.getAsyncRemote().sendText(init.toString());
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("📩 MCP Message: " + message);
    }

    @OnClose
    public void onClose() {
        System.out.println("❌ Disconnected from MCP server");
        latch.countDown();
    }

    private void sendPrompt(String promptName, String user) {
        ObjectNode req = mapper.createObjectNode();
        req.put("jsonrpc", "2.0");
        req.put("id", requestId++);
        req.put("method", "prompts/get");
        ObjectNode params = mapper.createObjectNode();
        params.put("name", promptName);
        ObjectNode args = mapper.createObjectNode();
        args.put("user", user);
        params.set("arguments", args);
        req.set("params", params);
        session.getAsyncRemote().sendText(req.toString());
    }

    private void sendTool(String toolName, String argKey, String argValue) {
        ObjectNode req = mapper.createObjectNode();
        req.put("jsonrpc", "2.0");
        req.put("id", requestId++);
        req.put("method", "tools/call");
        ObjectNode params = mapper.createObjectNode();
        params.put("name", toolName);
        ObjectNode args = mapper.createObjectNode();
        args.put(argKey, argValue);
        params.set("arguments", args);
        req.set("params", params);
        session.getAsyncRemote().sendText(req.toString());
    }

    private void interactiveLoop() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\nChoose action (prompt/tool/exit): ");
            String action = scanner.nextLine().trim();
            if ("exit".equalsIgnoreCase(action)) break;

            if ("prompt".equalsIgnoreCase(action)) {
                System.out.print("Enter prompt name: ");
                String promptName = scanner.nextLine();
                System.out.print("Enter user name: ");
                String user = scanner.nextLine();
                sendPrompt(promptName, user);
            } else if ("tool".equalsIgnoreCase(action)) {
                System.out.print("Enter tool name (readFile/execCommand): ");
                String toolName = scanner.nextLine();
                if ("readFile".equalsIgnoreCase(toolName)) {
                    System.out.print("Enter file path: ");
                    String path = scanner.nextLine();
                    sendTool("readFile", "path", path);
                } else if ("execCommand".equalsIgnoreCase(toolName)) {
                    System.out.print("Enter command: ");
                    String cmd = scanner.nextLine();
                    sendTool("execCommand", "command", cmd);
                }
            }
        }
        scanner.close();
        try { session.close(); } catch (Exception ignored) {}
        latch.countDown();
    }

    public static void main(String[] args) throws Exception {
        MCPJavaClient client = new MCPJavaClient(new URI("ws://localhost:3000/"));
        client.interactiveLoop();
        latch.await();
    }
}
