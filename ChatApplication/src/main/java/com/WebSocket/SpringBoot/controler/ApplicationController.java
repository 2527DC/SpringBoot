package com.WebSocket.SpringBoot.controler;


import com.WebSocket.SpringBoot.Entity.ChatMessage;
import com.WebSocket.SpringBoot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class ApplicationController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


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


}


