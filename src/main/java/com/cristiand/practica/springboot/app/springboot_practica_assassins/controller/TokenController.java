package com.cristiand.practica.springboot.app.springboot_practica_assassins.controller;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.LoginRequest;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.LoginResponse;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class TokenController {

    @Autowired
    TokenService tokenService;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = tokenService.login(loginRequest);
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
