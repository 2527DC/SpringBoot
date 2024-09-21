package com.WebSocket.SpringBoot.controler;

import com.WebSocket.SpringBoot.Entity.Users;
import com.WebSocket.SpringBoot.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class USerController {


    @Autowired
    private UserRepo userRepo;

    @GetMapping("/getUsers")
    public List<Users> getUsers(){

        return userRepo.findAll();

    }
}
