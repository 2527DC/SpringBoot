package com.WebSocket.SpringBoot.Entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;


@Entity
@Table(name = "users")
public class Users{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username" ,nullable = false ,unique = true)
    private String username;
    @Column(name = "status",nullable = false)
    private String status;
    @Column(name = "image" ,nullable = false)
    private String image;
    @Column(name = "sessionId" ,nullable = false ,unique = true)
    private String  sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    // Default constructor (required by JPA)
    public Users() {
    }

    // Parameterized constructor
    public Users(String username, String status, String image) {
        this.username = username;
        this.status = status;
        this.image = image;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", status='" + status + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
