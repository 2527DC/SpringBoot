package com.WebSocket.SpringBoot.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomChannelInterceptor implements ChannelInterceptor {

    // Store session ID to username mapping
    private final Map<String, String> sessionIdUsernameMap = new ConcurrentHashMap<>();

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String username = accessor.getFirstNativeHeader("username");
            String sessionId = accessor.getSessionId(); // Track session ID

            if (username != null) {
                if (accessor.getSessionAttributes() != null) {
                    accessor.getSessionAttributes().put("username", username);
                } else {
                    // Create a new session attributes map if not already present
                    accessor.setSessionAttributes(new HashMap<>());
                    accessor.getSessionAttributes().put("username", username);
                }

                // Store sessionId and username mapping
                sessionIdUsernameMap.put(sessionId, username);

                System.out.println("Username from header in INTERCEPTOR: " + username);
                System.out.println("Session ID in INTERCEPTOR: " + sessionId);
            }
        }

        return message;
    }

    // Method to get the username by session ID
    public String getUsernameBySessionId(String sessionId) {
        return sessionIdUsernameMap.get(sessionId);
    }
}
