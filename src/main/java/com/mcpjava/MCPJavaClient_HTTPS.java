//package com.mcpjava;
//
//import jakarta.websocket.*;
//import java.net.URI;
//import java.util.List;
//import java.util.Map;
//
//@ClientEndpoint(configurator = MCPJavaClient_HTTPS.AuthConfigurator.class)
//public class MCPJavaClient_HTTPS {
//
//    private Session session;
//
//    // Custom Configurator to add Authorization header
//    public static class AuthConfigurator extends ClientEndpointConfig.Configurator {
//        @Override
//        public void beforeRequest(Map<String, List<String>> headers) {
//            String token = System.getenv("GITHUB_TOKEN");
//            if (token == null || token.isEmpty()) {
//                throw new RuntimeException("❌ GITHUB_TOKEN not set in environment!");
//            }
//            headers.put("Authorization", List.of("Bearer " + token));
//        }
//    }
//
//    @OnOpen
//    public void onOpen(Session session) {
//        System.out.println("✅ Connected to Copilot MCP");
//        this.session = session;
//
//        // Example: send a JSON-RPC request
//        String request = """
//        {
//          "jsonrpc": "2.0",
//          "id": 1,
//          "method": "prompts/get",
//          "params": {
//            "name": "explainCode",
//            "arguments": {
//              "code": "public static void main(String[] args) { System.out.println(\\"Hello, World!\\"); }"
//            }
//          }
//        }
//        """;
//        session.getAsyncRemote().sendText(request);
//    }
//
//    @OnMessage
//    public void onMessage(String message) {
//        System.out.println("📥 Response: " + message);
//    }
//
//    @OnError
//    public void onError(Session session, Throwable throwable) {
//        throwable.printStackTrace();
//    }
//
//    @OnClose
//    public void onClose(Session session, CloseReason reason) {
//        System.out.println("❌ Disconnected: " + reason);
//    }
//
//    public static void main(String[] args) throws Exception {
//        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//        container.connectToServer(MCPJavaClient_HTTPS.class, new URI("wss://api.githubcopilot.com/mcp/"));
//    }
//}

package com.mcpjava;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MCPJavaClient_HTTPS {

    private static final String ENDPOINT = "https://api.githubcopilot.com/mcp/";
    private static final String TOKEN = System.getenv("GITHUB_TOKEN"); // must be set in environment

    public static void main(String[] args) throws Exception {
        if (TOKEN == null || TOKEN.isBlank()) {
            throw new IllegalStateException("❌ GITHUB_TOKEN not set in environment!");
        }

        // Example: ask Copilot MCP to explain code
        String jsonRpcRequest = """
        {
          "jsonrpc": "2.0",
          "id": 1,
          "method": "prompts/get",
          "params": {
            "name": "explainCode",
            "arguments": {
              "code": "public static void main(String[] args) { System.out.println(\\"Hello, World!\\"); }"
            }
          }
        }
        """;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT))
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRpcRequest))
                .build();

        System.out.println("📤 Sending request:\n" + jsonRpcRequest);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("📥 Response from Copilot MCP:");
        System.out.println(response.body());
    }
}

