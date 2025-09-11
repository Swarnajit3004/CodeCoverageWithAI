//package com.mcpjava;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.supportmethods.PromptTool;
////import org.glassfish.tyrus.server.Server;
//
//import javax.websocket.*;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@ServerEndpoint("/")
//public class MCPJavaServer {
//
//    private static final ObjectMapper mapper = new ObjectMapper();
//
//    @OnOpen
//    public void onOpen(Session session) {
//        System.out.println("✅ MCP Client connected: " + session.getId());
//    }
//
//    @OnMessage
//    public void onMessage(String message, Session session) throws IOException {
//        System.out.println("📩 Received: " + message);
//
//        Map<String, Object> request = mapper.readValue(message, Map.class);
//        Map<String, Object> response = new HashMap<>();
//
//        String method = (String) request.get("method");
//        Object id = request.get("id");
//        response.put("jsonrpc", "2.0");
//        response.put("id", id);
//
//        try {
//            switch (method) {
//                case "prompts/get": {
//                    Map<String, Object> params = (Map<String, Object>) request.get("params");
//                    String name = params != null ? (String) params.get("name") : null;
//                    String user = params != null && params.get("user") != null
//                            ? params.get("user").toString()
//                            : "developer";
//
//                    response.putAll(PromptTool.getPrompt(name, user));
//                    break;
//                }
//
//                case "tools/call": {
//                    Map<String, Object> params = (Map<String, Object>) request.get("params");
//                    String toolName = params != null ? (String) params.get("tool") : null;
//                    Map<String, Object> args = params != null
//                            ? (Map<String, Object>) params.get("arguments")
//                            : new HashMap<>();
//
//                    Map<String, String> toolResponse = new HashMap<>();
//                    toolResponse.put("result", "🔧 Tool " + toolName + " executed with args " + args);
//
//                    response.putAll(toolResponse);
//                    break;
//                }
//
//                default:
//                    response.put("error", "Unknown method: " + method);
//            }
//        } catch (Exception e) {
//            response.put("error", "Exception: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        String jsonResponse = mapper.writeValueAsString(response);
//        System.out.println("📤 Sending: " + jsonResponse);
//        session.getAsyncRemote().sendText(jsonResponse);
//    }
//
//    @OnClose
//    public void onClose(Session session) {
//        System.out.println("❌ MCP Client disconnected: " + session.getId());
//    }
//
//    @OnError
//    public void onError(Session session, Throwable throwable) {
//        System.err.println("💥 Error: " + throwable.getMessage());
//        throwable.printStackTrace();
//    }
//
//public static void main(String[] args) {
//        Server server = new Server("localhost", 3000, "/", null, MCPJavaServer.class);
//        try {
//            server.start();
//            System.out.println("🚀 Java MCP Server running at ws://localhost:3000");
//            Thread.currentThread().join();
//            new java.util.Scanner(System.in).nextLine();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            server.stop();
//        }
//    }
//}
