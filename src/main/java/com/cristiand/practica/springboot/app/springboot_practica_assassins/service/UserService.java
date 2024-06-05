package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.CreateUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.FindUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;
import java.util.List;

public interface UserService {
    
    User save(CreateUserDto theUserDto);

    boolean existsByUsername(String username);

    List<User> filterUsers(FindUserDto findUserDto);
    
}
