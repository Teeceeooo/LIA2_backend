package com.lia2.lia2_backend.controller;

import com.lia2.lia2_backend.entity.*;
import com.lia2.lia2_backend.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    @GetMapping("/getAll")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getParticipantActivities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        Page<User> users = userService.getUsers(pageable);
        return ResponseEntity.ok().body(users.getContent());
    }

    @Transactional
    @PostMapping("/add")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Användare skapad");
    }

    @Transactional
    @PutMapping("/edit")
    public ResponseEntity<User> editUser(@RequestBody User user) {
        User existingUser = userService.editUser(user.getUsername());
        if(existingUser != null) {
            existingUser.setEnabled(user.getEnabled());
            if(user.getPassword() != null){
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            return ResponseEntity.ok(existingUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
        @GetMapping("/{username}")
        public User getUserByUserName (@PathVariable String username){
            return userService.findByUserName(username);
        }
    }
