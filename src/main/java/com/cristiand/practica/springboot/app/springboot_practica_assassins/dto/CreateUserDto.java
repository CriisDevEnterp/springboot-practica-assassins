package com.cristiand.practica.springboot.app.springboot_practica_assassins.dto;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.Role;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.validation.ExistsByUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CreateUserDto( 
    
    @NotBlank
    @Size(min = 4, max = 12)
    @ExistsByUsername
    String username, 

    @NotBlank
    String password, 

    Boolean enabled, 

    List<Role> roles 
    
) { }
