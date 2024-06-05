package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dao.UserRepository;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.LoginRequest;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.LoginResponse;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TokenServiceImplTest {

    @Autowired
    private TokenServiceImpl tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void testLoginSuccess() {
        // Configurar datos de prueba
        User user = new User();
        user.setUsername("username");
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode("password"));
        userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest("username", "password");
        LoginResponse loginResponse = tokenService.login(loginRequest);

        assertNotNull(loginResponse.accessToken());
        assertEquals(300L, loginResponse.expiresIn());
    }

    @Test
    public void testLoginFailureUserNotFound() {
        LoginRequest loginRequest = new LoginRequest("nonexistentuser", "password");
        LoginResponse loginResponse = tokenService.login(loginRequest);

        assertEquals("", loginResponse.accessToken());
        assertEquals(0L, loginResponse.expiresIn());
    }

    @Test
    public void testLoginFailureIncorrectPassword() {
        // Configurar datos de prueba
        User user = new User();
        user.setUsername("existinguser");
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode("correctpassword"));
        userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest("existinguser", "incorrectpassword");
        LoginResponse loginResponse = tokenService.login(loginRequest);

        assertEquals("", loginResponse.accessToken());
        assertEquals(0L, loginResponse.expiresIn());
    }
    
}
