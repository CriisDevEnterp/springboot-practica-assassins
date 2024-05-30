package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import java.util.List;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;

public interface UserService {
    
    List<User> findAll();

    User save(User theUser);

    boolean existsByUsername(String username);
    
}
