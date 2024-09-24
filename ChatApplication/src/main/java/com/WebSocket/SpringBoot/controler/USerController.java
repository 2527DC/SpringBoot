package com.WebSocket.SpringBoot.controler;

import com.WebSocket.SpringBoot.Entity.Users;
import com.WebSocket.SpringBoot.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class USerController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserRepo userRepo;

    @MessageMapping("getOnlineUsers")
    @SendTo("/topic/onlineUsers")
    public List<Users> getUsers(){

        System.out.println(" the online users  method has been iin,voked ");
        return userRepo.findAll();

    }
}
