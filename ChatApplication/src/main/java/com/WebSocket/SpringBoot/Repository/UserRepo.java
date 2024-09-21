package com.WebSocket.SpringBoot.Repository;

import com.WebSocket.SpringBoot.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo  extends JpaRepository<Users,Integer> {

}
