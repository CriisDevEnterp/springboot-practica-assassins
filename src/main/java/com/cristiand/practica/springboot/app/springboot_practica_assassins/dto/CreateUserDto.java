package com.cristiand.practica.springboot.app.springboot_practica_assassins.dto;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.validation.ExistsByUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record CreateUserDto( 
    
    @NotBlank(message = "No debe estar en blanco.")
    @Size(min = 4, max = 12, message = "El tamaño debe estar entre 4 y 12.")
    @ExistsByUsername()
    String username, 

    @NotBlank(message = "No debe estar en blanco.")
    String password, 

    Boolean enabled, 

    @NotBlank(message = "No debe estar en blanco.")
    String firstName,

    @NotBlank(message = "No debe estar en blanco.")
    String lastName,

    @NotBlank(message = "No debe estar en blanco.")
    String email,

    MultipartFile profileImage,

    String roles 
    
) { }
