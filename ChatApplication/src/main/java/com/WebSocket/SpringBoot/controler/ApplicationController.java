package com.WebSocket.SpringBoot.controler;

import com.WebSocket.SpringBoot.Entity.ChatMessage;
import com.WebSocket.SpringBoot.config.UserTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;

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
    @MessageMapping("/private-message")
    public void sendPrivateMessage(@Payload ChatMessage message) {

        System.out.println(" the private message method has be invoked ");
        System.out.println(message.toString());
        simpMessagingTemplate.convertAndSendToUser(message.getRecipient(), "/queue/messages", message);
    }

}
