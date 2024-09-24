package com.WebSocket.SpringBoot.config;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserTrackingService {

    // Set to store online users
    private final Set<String> onlineUsers = Collections.newSetFromMap(new ConcurrentHashMap<>());

    // Map to track session ID and username
    private final Map<String, String> sessionUsernameMap = new ConcurrentHashMap<>();

    // Add a user to the online users set
    public void addUser(String sessionId, String username) {
        onlineUsers.add(username);
        sessionUsernameMap.put(sessionId, username); // Track session ID and username
    }

    // Remove a user from the online users set and session tracking map
    public void removeUser(String sessionId) {
        String username = sessionUsernameMap.remove(sessionId);
        if (username != null) {
            onlineUsers.remove(username); // Remove from online users if found
        }
    }

    // Get all online users
    public Set<String> getOnlineUsers() {
        return Collections.unmodifiableSet(onlineUsers);
    }

    // Retrieve username by session ID
    public String getUsernameBySessionId(String sessionId) {
        return sessionUsernameMap.get(sessionId);
    }
}
