package com.cristiand.practica.springboot.app.springboot_practica_assassins.dao;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    
}
