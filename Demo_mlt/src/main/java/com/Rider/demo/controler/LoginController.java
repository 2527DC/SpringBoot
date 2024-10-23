package com.Rider.demo.controler;

import com.Rider.demo.entity.Login;
import com.Rider.demo.repository.LoginRepo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {

    @Autowired
    private LoginRepo repo;

    @PostMapping("register")
    public ResponseEntity<ApiResponse> register(@RequestBody Login login) {
        System.out.println("register method invoked");

        // Check if login object is null
        if (login == null) {
            System.out.println("No data received from the app.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("No data received", false));
        }

        try {
            // Additional validation can be added here (e.g., check for empty fields)
            if (login.getGender() == null || login.getGender().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse("Gender must not be empty", false));
            }

            System.out.println("Gender received: " + login.getGender());
            repo.save(login); // Assuming repo is correctly initialized and can save the Login object
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Registration successful!", true));
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // Use INTERNAL_SERVER_ERROR for unexpected failures
                    .body(new ApiResponse("Registration failed: " + e.getMessage(), false));
        }
    }


    @PostMapping("login")
    public ResponseEntity<ApiResponse> login(@RequestBody String json) {
        System.out.println("Login method invoked");

        // Parse JSON object
        JSONObject jsonObject = new JSONObject(json);
        String email = jsonObject.optString("email");
        String password = jsonObject.optString("password");

        // Validate inputs
        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("Email or password is empty.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Email or password cannot be empty", false));
        }

        System.out.println(email + "  this is the email from backend");

        try {
            // Fetch user by email
            Login found = repo.findByEmail(email);

            // Validate user credentials
            if (found != null && password.equals(found.getPassword())) {
                return ResponseEntity.status(HttpStatus.OK) // Use 200 OK for successful login
                        .body(new ApiResponse("Login successful!", true));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED) // Use 401 for unauthorized access
                        .body(new ApiResponse("Login failed: Invalid email or password", false));
            }
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // Use 500 for unexpected errors
                    .body(new ApiResponse("Login failed: " + e.getMessage(), false));
        }
    }



}
