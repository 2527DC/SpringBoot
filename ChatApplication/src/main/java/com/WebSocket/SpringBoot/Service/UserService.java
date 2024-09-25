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



    public Users insert( String username ,String status ,String sessionid){

        Users k=  new Users();
        String image=" src/assets/DSC00921.JPG";
        Users  found = userRepo.findByUsername(username);
        if(found!=null){

            found.setSessionId(sessionid);
            found.setStatus(status);
            return  userRepo.save(found);
        }
        else {
            k.setUsername(username);
            k.setImage(image);
            k.setStatus(status);
            k.setSessionId(sessionid);
        }


        return  userRepo.save(k);
    }



    public String remove (String sessionid){

        System.out.println(" the remove method  is gettin invoked ");
        Users s= userRepo.findBySessionId(sessionid);
        if (s!=null){
           s.setStatus("offline");
           userRepo.save(s);
        }
        return  s!=null ?"rmoved":" not removed ";
    }


    public List<Users> getUsers(){

        System.out.println(" the online users  method has been iin,voked ");
        return userRepo.findAll();

    }

}
