package com.beginproject;

import org.apache.commons.httpclient.methods.PostMethod;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PostUserExample {
    public static void main(String[] args) throws Exception {
        WiremockPostUser.User user = new WiremockPostUser.User();
        String jsonBody = user.createUserResponse("congo@africa.com", "manager","Swarnajit", "Adhikary", "abcd@egfh.com");

        String url = "http://localhost:9999/v2/Users/";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("POST", HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("POST");
//        con.setRequestProperty("Content-Type", "application/json; utf-8");
//        con.setDoOutput(true);

        int responseCode = response.statusCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            System.out.println(response.body());
            System.out.println("POST request successful. Response Code: " + responseCode);
        } else {
            System.out.println("POST request failed. Response Code: " + responseCode);
        }
    }
}

