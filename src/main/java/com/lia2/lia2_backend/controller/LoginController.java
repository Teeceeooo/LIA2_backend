package com.lia2.lia2_backend.controller;

import com.lia2.lia2_backend.DTO.UserDTO;
import com.lia2.lia2_backend.entity.User;
import com.lia2.lia2_backend.repository.UserRepository;
import com.lia2.lia2_backend.service.TokenService;
import com.lia2.lia2_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import com.lia2.lia2_backend.entity.Authority;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class LoginController {
    private final UserService userService;
    private final UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @Autowired
    public LoginController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        Optional<UserDTO> userOpt = userRepository.findUserByUsername(username);
        if (userOpt.isPresent()) {
            UserDTO user = userOpt.get();
            if (BCrypt.checkpw(password, user.getPassword())) {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setUsername(user.getUsername());
                userDTO.setName(user.getName());
                //userDTO.setRoles(user.getRoles().stream().collect(Collectors.toSet()));
                //String token = tokenService.generateTokenUsingSecret(username).getJwtToken();
                return ResponseEntity.ok(userDTO);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
