package com.lia2.lia2_backend.repository;

import com.lia2.lia2_backend.DTO.UserDTO;
import com.lia2.lia2_backend.entity.Activity;
import com.lia2.lia2_backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT new com.lia2.lia2_backend.DTO.UserDTO(u.username, u.password, u.enabled) FROM User u WHERE u.username = ?1")
    Optional<UserDTO> findUserByUsername(String username);


    Page<User> findAll(Pageable pageable);

    User findUserByusername(String username);


    @Query("SELECT a.authority FROM Authority a WHERE a.username = ?1")
    Optional<String> findAuthoritiesByUsername(String username);
}
