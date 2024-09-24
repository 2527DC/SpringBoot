package com.WebSocket.SpringBoot.Service;

import com.WebSocket.SpringBoot.Entity.Users;
import com.WebSocket.SpringBoot.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private  Users us;

    public Users insert( String username ,String status ){

        String image=" src/assets/DSC00921.JPG";
        Users k=us;
        k.setUsername(username);
        k.setImage(image);
        k.setStatus(status);

        return  userRepo.save(k);
    }


//
//    public String remove (String sessionid){
//
//
//        System.out.println(" the remove method  is gettin invoked ");
//        Users s= userRepo.findBySessionId(sessionid);
//
//        userRepo.delete(s);
//
//        return  s!=null ?"rmoved":" not removed ";
//    }


    public List<Users> getUsers(){

        System.out.println(" the online users  method has been iin,voked ");
        return userRepo.findAll();

    }

}
