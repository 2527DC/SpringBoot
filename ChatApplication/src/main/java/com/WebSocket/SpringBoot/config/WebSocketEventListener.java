package com.WebSocket.SpringBoot.config;

import com.WebSocket.SpringBoot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import org.springframework.web.socket.messaging.SessionDisconnectEvent;


@Component
public class WebSocketEventListener {


    @Autowired
    private UserService UserService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserTrackingService userTrackingService;


    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // Print the headers for debugging
        System.out.println("This is the header in SessionConnectEvent: " + headerAccessor);

        // Access the native headers to get the username
        String username = String.valueOf(headerAccessor.getFirstNativeHeader("username"));

        String Status ="online";

        UserService.insert(username,Status);

        System.out.println(username + " username in the session");

        if (username != null ) {
            userTrackingService.addUser(headerAccessor.getSessionId(),username);
        } else {
            System.out.println("Username header not found in SessionConnectEvent");
        }


    }
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event ) {
//        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
//
//        System.out.println( " disconnectin method invoked " + headerAccessor.getMessageHeaders());
//
//        String username = headerAccessor.getSessionId();
//
//        System.out.println(username);
//        if (username != null) {
//            userTrackingService.removeUser(username);
//            System.out.println("User Disconnected: " + username);
//
//            // Broadcast the updated list of online users to everyone
//            simpMessagingTemplate.convertAndSend("/topic/onlineUsers",userTrackingService.getOnlineUsers());
//        }
//    }


}