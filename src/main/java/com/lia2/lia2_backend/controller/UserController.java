package com.lia2.lia2_backend.controller;

import com.lia2.lia2_backend.entity.Image;
import com.lia2.lia2_backend.entity.Item;
import com.lia2.lia2_backend.entity.Participant;
import com.lia2.lia2_backend.entity.User;
import com.lia2.lia2_backend.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
public class UserController {

    private final UserService userService;
    @GetMapping("/getAll")
    public List<User> getAllUsers() {
        return userService.findAll();
    }
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @PostMapping("/add")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Användare skapad");
    }
}
