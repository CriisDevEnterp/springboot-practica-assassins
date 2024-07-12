package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dao.UserRepository;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.LoginRequest;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.LoginResponse;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;
import io.jsonwebtoken.lang.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
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
        user.setPassword(passwordEncoder.encode("password"));
        user.setEnabled(true);
        user.setFirstName("testFirstname");
        user.setLastName("testLastname");
        user.setEmail("test@prueba.com");
        user.setRoles(Collections.emptyList());
        userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest("username", "password");
        LoginResponse loginResponse = tokenService.login(loginRequest);

        assertNotNull(loginResponse.accessToken());
        assertEquals(300L, loginResponse.accessTokenExpiresIn());
    }

    @Test
    public void testLoginFailureUserNotFound() {
        LoginRequest loginRequest = new LoginRequest("nonexistentuser", "password");

        // Verificar que se lanza la excepción correcta
        BadCredentialsException thrown = assertThrows(
                BadCredentialsException.class,
                () -> tokenService.login(loginRequest),
                "Expected login() to throw, but it didn't");

        assertEquals("Credenciales no válidas.", thrown.getMessage());
    }

    @Test
    public void testLoginFailureIncorrectPassword() {
        // Configurar datos de prueba
        User user = new User();
        user.setUsername("existinguser");
        user.setPassword(passwordEncoder.encode("correctpassword"));
        user.setEnabled(true);
        user.setFirstName("testFirstname");
        user.setLastName("testLastname");
        user.setEmail("test@prueba.com");
        userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest("existinguser", "incorrectpassword");

        // Verificar que se lanza la excepción correcta
        BadCredentialsException thrown = assertThrows(
                BadCredentialsException.class,
                () -> tokenService.login(loginRequest),
                "Expected login() to throw, but it didn't");

        assertEquals("Credenciales no válidas.", thrown.getMessage());
    }

}
