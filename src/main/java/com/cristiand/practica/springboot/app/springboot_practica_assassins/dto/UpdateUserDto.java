package com.cristiand.practica.springboot.app.springboot_practica_assassins.dto;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.validation.ValidFormatEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record UpdateUserDto( 
    
    @NotBlank(message = "No debe estar en blanco.")
    @Size(min = 4, max = 12, message = "El tamaño debe estar entre 4 y 12.")
    String username, 

    String password, 

    Boolean enabled, 

    @NotBlank(message = "No debe estar en blanco.")
    String firstName,

    @NotBlank(message = "No debe estar en blanco.")
    String lastName,

    @NotBlank(message = "No debe estar en blanco.")
    @ValidFormatEmail()
    String email,

    MultipartFile profileImage,

    String roles 
    
) { }
