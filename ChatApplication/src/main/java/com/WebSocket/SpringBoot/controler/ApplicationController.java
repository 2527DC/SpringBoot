package com.WebSocket.SpringBoot.controler;


import com.WebSocket.SpringBoot.Entity.ChatMessage;
import com.WebSocket.SpringBoot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@RestController
public class ApplicationController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    private final Set<String> activeUsers = new HashSet<>();

    @GetMapping("/Check")
    public String pri() {
        return "the fetch is working";
    }
//
//    @MessageMapping("/message") // client should use "/app/message"
//    public ChatMessage send( @Payload ChatMessage message){
//        if ( message.getRecipient()!=null){
//
//            System.out.println("privater message");
//            String dis="/user"+message.getRecipient();
//            simpMessagingTemplate.convertAndSend(dis,message);
//        }
//        else {
//
//            System.out.println("public message");
//            simpMessagingTemplate.convertAndSend(message);
//        }
//        return  null;
//    }

@MessageMapping("/send")
@SendTo("/topic/messages")
public String sendMessage(String message) {
    return message;
}

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Handle private messages
        @MessageMapping("/send-private")
      public void sendPrivateMessage( @Payload ChatMessage message) {

        System.out.println(message.toString());
        messagingTemplate.convertAndSendToUser(message.getRecipient(), "/queue/messages", message);
    }
    // Handle WebSocket connection event
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        // Try to get the username from the WebSocket session attributes
        String username = null;

        if (event.getUser() != null) {
            username = event.getUser().getName();
        } else {
            // Fallback: Try to get the username from the session attributes
            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
            username = (String) headerAccessor.getSessionAttributes().get("username");
        }

        if (username != null) {
            activeUsers.add(username);
            // Notify all users about the new connection
            messagingTemplate.convertAndSend("/topic/onlineUsers", activeUsers);
            System.out.println("User connected: " + username);
        } else {
            System.err.println("Username is null during WebSocket connection.");
        }
    }

    // Handle WebSocket disconnection event
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String username = null;

        if (event.getUser() != null) {
            username = event.getUser().getName();
        } else {
            // Fallback: Try to get the username from the session attributes
            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
            username = (String) headerAccessor.getSessionAttributes().get("username");
        }

        if (username != null) {
            activeUsers.remove(username);
            // Notify all users about the disconnection
            messagingTemplate.convertAndSend("/topic/onlineUsers", activeUsers);
            System.out.println("User disconnected: " + username);
        } else {
            System.err.println("Username is null during WebSocket disconnection.");
        }
    }

}