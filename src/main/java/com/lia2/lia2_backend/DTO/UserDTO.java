package com.lia2.lia2_backend.DTO;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String name;
    private String password;
    private boolean enabled;
    private Set<String> roles;

    public UserDTO() {
    }

    public UserDTO(String username, String password, Boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

}
