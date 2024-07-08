package com.cristiand.practica.springboot.app.springboot_practica_assassins.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

    @NotBlank(message = "No debe estar en blanco.")
    String username, 

    @NotBlank(message = "No debe estar en blanco.")
    String password
    
) { }
