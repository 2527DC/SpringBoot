package com.WebSocket.SpringBoot.controller;

import com.WebSocket.SpringBoot.Entity.ChatMessage;
import com.WebSocket.SpringBoot.config.UserTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserTrackingService userTrackingService;

    @GetMapping("/Check")
    public String pri() {
        return "the fetch is working";
    }

    // Broadcast public messages to /topic/messages
    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public String sendMessage(String message) {
        return message;
    }

    // Handle private messages
    @MessageMapping("/send-private")
    public void sendPrivateMessage(@Payload ChatMessage message) {
        System.out.println(message.toString());
        simpMessagingTemplate.convertAndSendToUser(message.getRecipient(), "/queue/messages", message);
    }

    // Handle fetching online users and broadcast to all subscribers of /topic/onlineUsers
    @MessageMapping("getOnlineUsers")
    @SendTo("/topic/onlineUsers")
    public Object[] getOnlineUsers() {
        // Assuming the UserTrackingService has a method to fetch online users
        return userTrackingService.getOnlineUsers().stream().toArray();
    }

    @MessageMapping("/{username}/remove")
    public void removeUser(@PathVariable String username) {

        System.out.println(" removing  off the  user has been invoked ");
        userTrackingService.removeUser(username);
        System.out.println("User " + username + " has been removed from the online users.");

        // Optionally broadcast the updated list of online users to all clients
        simpMessagingTemplate.convertAndSend("/topic/onlineUsers",userTrackingService);
    }
}
