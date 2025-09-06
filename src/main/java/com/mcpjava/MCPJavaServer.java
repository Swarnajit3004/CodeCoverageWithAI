package com.mcpjava;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import org.glassfish.tyrus.server.Server;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@ServerEndpoint(value = "/")
public class MCPJavaServer {
    private static final ObjectMapper mapper = new ObjectMapper();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("✅ Client connected");
    }

    @OnMessage
    public String onMessage(String message, Session session) throws Exception {
        System.out.println("📩 Received: " + message);

        ObjectNode json = (ObjectNode) mapper.readTree(message);

        if (json.has("method") && "initialize".equals(json.get("method").asText())) {
            // Respond to initialize
            ObjectNode resp = mapper.createObjectNode();
            resp.put("jsonrpc", "2.0");
            resp.put("id", json.get("id").asInt());
            ObjectNode result = mapper.createObjectNode();
            result.put("serverName", "Java MCP Server");
            result.put("version", "1.0.0");
            resp.set("result", result);
            return resp.toString();
        }

        if (json.has("method") && "prompts/get".equals(json.get("method").asText())) {
            // Handle custom prompt (repoGreeting)
            ObjectNode resp = mapper.createObjectNode();
            resp.put("jsonrpc", "2.0");
            resp.put("id", json.get("id").asInt());
            ObjectNode result = mapper.createObjectNode();
            result.put("text", "👋 Hello from Java MCP inside your repo!");
            resp.set("result", result);
            return resp.toString();
        }

        return null; // no response
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("❌ Client disconnected");
    }

    public static void main(String[] args) {
        Server server = new Server("localhost", 3000, "/", null, MCPJavaServer.class);
        try {
            server.start();
            System.out.println("🚀 Java MCP Server running at ws://localhost:3000");
            Thread.currentThread().join();
            new java.util.Scanner(System.in).nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}
