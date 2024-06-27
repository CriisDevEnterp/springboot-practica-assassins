package com.cristiand.practica.springboot.app.springboot_practica_assassins.controller;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.CreateUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.util.UserUtilTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserControllerIntegrationTest {

    @Autowired
    private UserUtilTest userUtilTest;

    @Test
    public void testSaveUser_WithInvalidUsername() {
        // Arrange
        String invalidUsername = null;
        String rolesJson = "[{\"name\": \"ROLE_USER\"}, {\"name\": \"ROLE_ADMIN\"}, {\"name\": \"ROLE_RECEPCIONIST\"}]";
        CreateUserDto createUserDto = new CreateUserDto(
                invalidUsername,
                "123456",
                true,
                "Cristian",
                "de la Rosa",
                "cristian@prueba.com",
                null,
                rolesJson);

        // Act & Assert
        userUtilTest.validateAndAssertError(createUserDto, "username", "No debe estar en blanco.");
    }

    @Test
    public void testSaveUser_WithInvalidPassword() {
        // Arrange
        String invalidPassword = null;
        String rolesJson = "[{\"name\": \"ROLE_USER\"}, {\"name\": \"ROLE_ADMIN\"}, {\"name\": \"ROLE_RECEPCIONIST\"}]";
        CreateUserDto createUserDto = new CreateUserDto(
                "cristian",
                invalidPassword,
                true,
                "Cristian",
                "de la Rosa",
                "cristian@prueba.com",
                null,
                rolesJson);

        // Act & Assert
        userUtilTest.validateAndAssertError(createUserDto, "password", "No debe estar en blanco.");
    }

    @Test
    public void testSaveUser_WithInvalidFirstName() {
        // Arrange
        String invalidFirstName = null;
        String rolesJson = "[{\"name\": \"ROLE_USER\"}, {\"name\": \"ROLE_ADMIN\"}, {\"name\": \"ROLE_RECEPCIONIST\"}]";
        CreateUserDto createUserDto = new CreateUserDto(
                "cristian",
                "123456",
                true,
                invalidFirstName,
                "de la Rosa",
                "cristian@prueba.com",
                null,
                rolesJson);

        // Act & Assert
        userUtilTest.validateAndAssertError(createUserDto, "firstName", "No debe estar en blanco.");
    }

    @Test
    public void testSaveUser_WithInvalidLastName() {
        // Arrange
        String invalidLastName = null;
        String rolesJson = "[{\"name\": \"ROLE_USER\"}, {\"name\": \"ROLE_ADMIN\"}, {\"name\": \"ROLE_RECEPCIONIST\"}]";
        CreateUserDto createUserDto = new CreateUserDto(
                "cristian",
                "123456",
                true,
                "Cristian",
                invalidLastName,
                "cristian@prueba.com",
                null,
                rolesJson);

        // Act & Assert
        userUtilTest.validateAndAssertError(createUserDto, "lastName", "No debe estar en blanco.");
    }

    @Test
    public void testSaveUser_WithInvalidEmail() {
        // Arrange
        String invalidEmail = null;
        String rolesJson = "[{\"name\": \"ROLE_USER\"}, {\"name\": \"ROLE_ADMIN\"}, {\"name\": \"ROLE_RECEPCIONIST\"}]";
        CreateUserDto createUserDto = new CreateUserDto(
                "cristian",
                "123456",
                true,
                "Cristian",
                "de la Rosa",
                invalidEmail,
                null,
                rolesJson);

        // Act & Assert
        userUtilTest.validateAndAssertError(createUserDto, "email", "No debe estar en blanco.");
    }

}
