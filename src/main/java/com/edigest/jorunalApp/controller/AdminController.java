package com.edigest.jorunalApp.controller;


import com.edigest.jorunalApp.api.response.cache.AppCache;
import com.edigest.jorunalApp.entity.User;
import com.edigest.jorunalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private  UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> all = userService.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody User user){
        userService.saveAdmin(user);
    }


    /*//if while app is running annd we change
     apikey value it will be same untill
     we restart it but i dont want to
     restart it so we created a
     controller which refreshes
     our cache*/
    @GetMapping("clear-app-cache")
    public void ClearAppCache(){
        appCache.init();
    }

    
}
