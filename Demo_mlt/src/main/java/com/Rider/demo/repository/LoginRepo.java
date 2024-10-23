package com.Rider.demo.repository;

import com.Rider.demo.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoginRepo   extends JpaRepository<Login, Integer> {

    Login findByEmail(String email);
}
