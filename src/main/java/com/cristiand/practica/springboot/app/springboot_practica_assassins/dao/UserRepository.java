package com.cristiand.practica.springboot.app.springboot_practica_assassins.dao;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    boolean existsByUsername(String username);
    
    User findByUsername(String username); 

    List<User> findByUsernameContaining(String username);
    
}
