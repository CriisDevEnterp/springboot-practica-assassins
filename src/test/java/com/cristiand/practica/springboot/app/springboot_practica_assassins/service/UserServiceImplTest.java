package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dao.UserRepository;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.CreateUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.FindUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;

import jakarta.validation.ValidationException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void testSaveUser_WithValidData() {
        // Arrange
        String rawPassword = "password";
        CreateUserDto createUserDto = new CreateUserDto("testUser", rawPassword, true, Collections.emptyList());

        // Act
        User savedUser = userService.save(createUserDto);

        // Assert
        assertNotNull(savedUser);
        assertEquals("testUser", savedUser.getUsername());
        assertTrue(savedUser.getEnabled());

        // Verificar que la contraseña está cifrada correctamente
        assertTrue(passwordEncoder.matches(rawPassword, savedUser.getPassword()));
    }

    @Test
    public void testSaveUser_WithInvalidUsername() {
        // Arrange
        CreateUserDto createUserDto = new CreateUserDto("", "password", true, Collections.emptyList());

        // Act & Assert
        assertThrows(ValidationException.class, () -> userService.save(createUserDto));
    }

    @Test
    public void testSaveUser_WithInvalidPassword() {
        // Arrange
        CreateUserDto createUserDto = new CreateUserDto("testUser", "", true, Collections.emptyList());

        // Act & Assert
        assertThrows(ValidationException.class, () -> userService.save(createUserDto));
    }

    @Test
    public void testExistsByUsername_WhenUsernameExists() {
        // Arrange
        String existingUsername = "existingUser";
        userRepository.save(new User(existingUsername, "password", true, Collections.emptyList()));

        // Act
        boolean exists = userService.existsByUsername(existingUsername);

        // Assert
        assertTrue(exists);
    }

    @Test
    public void testExistsByUsername_WhenUsernameDoesNotExist() {
        // Arrange
        String nonExistingUsername = "nonExistingUser";

        // Act
        boolean exists = userService.existsByUsername(nonExistingUsername);

        // Assert
        assertFalse(exists);
    }

    @Test
    public void testFilterUsers_WithExistingUsername() {
        // Arrange
        String username = "prueba";
        userRepository.save(new User("prueba", "password", true, Collections.emptyList()));

        // Act
        List<User> filteredUsers = userService.filterUsers(new FindUserDto(username));

        // Assert
        assertEquals(1, filteredUsers.size());
    }

    @Test
    public void testFilterUsers_WithNonExistingUsername() {
        // Act
        List<User> filteredUsers = userService.filterUsers(new FindUserDto("nonExistingUser"));

        // Assert
        assertTrue(filteredUsers.isEmpty());
    }
}
