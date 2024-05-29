package com.cristiand.practica.springboot.app.springboot_practica_assassins.dao;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    
    Role findByName(String theName);
    
}
