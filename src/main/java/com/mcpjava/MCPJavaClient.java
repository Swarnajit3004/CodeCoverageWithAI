package com.mcpjava;

import jakarta.websocket.*;
import java.net.URI;
import java.util.Collections;

public class MCPJavaClient extends Endpoint {

    private Session session;

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        System.out.println("✅ Connected to GitHub Copilot MCP");

        // Example: send initialize request
        String initRequest = """
        {
          "jsonrpc": "2.0",
          "id": 1,
          "method": "initialize",
          "params": {
            "clientInfo": {
              "name": "mcp-java-client",
              "version": "1.0.0"
            }
          }
        }
        """;
        session.getAsyncRemote().sendText(initRequest);
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("❌ Connection closed: " + closeReason);
    }

    @Override
    public void onError(Session session, Throwable thr) {
        System.err.println("💥 Error: " + thr.getMessage());
        thr.printStackTrace();
    }

    // Message handler
    @OnMessage
    public void onMessage(String message) {
        System.out.println("📩 Received: " + message);
    }

    public static void main(String[] args) throws Exception {
        String token = System.getenv("GITHUB_TOKEN");
        if (token == null || token.isBlank()) {
            System.err.println("⚠️ Please set the GITHUB_TOKEN environment variable.");
            return;
        }

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        // Add required headers
        ClientEndpointConfig config = ClientEndpointConfig.Builder.create()
                .configurator(new ClientEndpointConfig.Configurator() {
                    @Override
                    public void beforeRequest(java.util.Map<String, java.util.List<String>> headers) {
                        headers.put("Authorization", Collections.singletonList("Bearer " + token));
                        headers.put("Sec-WebSocket-Protocol", Collections.singletonList("jsonrpc")); // 🔑 required
                    }
                }).build();

        // Connect with headers + protocol
        container.connectToServer(
                new MCPJavaClient(),
                config,
                URI.create("wss://api.githubcopilot.com/mcp/")
        );
    }
}
