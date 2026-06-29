package com.edigest.jorunalApp.controller;


import com.edigest.jorunalApp.entity.User;
import com.edigest.jorunalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "okay";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user){
        boolean success = userService.saveNewUser(user);
        if (success) {
            return new ResponseEntity<>(HttpStatus.CREATED); // 201 Created
        }
        return new ResponseEntity<>("Failed to write to database", HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }
}
