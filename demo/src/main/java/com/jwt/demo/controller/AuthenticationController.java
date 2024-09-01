package com.jwt.demo.controller;


import com.jwt.demo.config.JwtUtil;
import com.jwt.demo.entity.User;
import com.jwt.demo.repo.UserRepo;
import com.jwt.demo.server.AuthenticationRequest;
import com.jwt.demo.server.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsServicer;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo rep;
    @PostMapping("/authenticate")
    public String createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsServicer.loadUserByUsername(authenticationRequest.getUserName());
        
        System.out.println(userDetails + " this the is detail in controler");

        return jwtUtil.generateToken(userDetails);
    }

    @GetMapping("/message")
    
    public String dip() {
    	
    	return " lowda fucker";
    }
    
    
    @PostMapping("/register")
    public User register(@RequestBody User request) {
          System.out.println(request.toString());
    	  request.setPassword(passwordEncoder.encode(request.getPassword()));
    	  
        return rep.save(request);
    }
}
