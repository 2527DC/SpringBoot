package com.WebSocket.SpringBoot.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public class CustomHandshakeInterceptor implements HandshakeInterceptor {

    private final UserTrackingService userTrackingService;

    public CustomHandshakeInterceptor(UserTrackingService userTrackingService) {
        this.userTrackingService = userTrackingService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        // Extract the query parameters from the URL
        String query = request.getURI().getQuery();
        Map<String, String> queryParams = UriComponentsBuilder.fromUriString("?" + query).build().getQueryParams().toSingleValueMap();

        // Get the username from query params
        String username = queryParams.get("username");

        if (username != null) {
            // Set username in session attributes
            attributes.put("username", username);
            userTrackingService.addUser(username); // Add user to tracking service
            System.out.println(username + " has joined.");
        } else {
            System.out.println("Username is missing in the request.");
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        // Post-handshake logic if needed
    }
}
