package com.lia2.lia2_backend.service;

import com.lia2.lia2_backend.entity.Authority;
import com.lia2.lia2_backend.entity.User;
import com.lia2.lia2_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    @Autowired
    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Authority> authorities = new HashSet<>();
        for (Authority role : user.getRoles()) {
            role.setUsername(user.getUsername());
            authorities.add(role);
        }
        user.setRoles(authorities);
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findByUserName(String username) {
        return userRepository.findUserByusername(username);
    }

    public User editUser(String username) {
      return userRepository.findUserByusername(username);
    }

    public List<User> searchModerators(String accountName, String name) {
        return userRepository.searchModerators(accountName, name);

    }
}
