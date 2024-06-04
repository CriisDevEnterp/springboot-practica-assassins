package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.LoginRequest;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.LoginResponse;

public interface TokenService {

    LoginResponse login(LoginRequest loginRequest);
    
}
