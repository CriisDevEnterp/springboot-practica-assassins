package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.CreateUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain.CustomAssassinException;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.util.UserUtilTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserUtilTest userUtilTest;

    @Test
    @DisplayName("Save User with Valid Data")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testSaveUser_WithValidData() throws CustomAssassinException, Exception {
        // Arrange
        CreateUserDto createUserDto = userUtilTest.createDefaultCreateUserDto(
                true,
                "static/images/no-user.jpg",
                "[{\"name\": \"ROLE_USER\"}, {\"name\": \"ROLE_ADMIN\"}, {\"name\": \"ROLE_RECEPCIONIST\"}]");

        // Act
        User savedUser = userService.save(createUserDto);

        // Assert
        userUtilTest.assertSavedUser(savedUser, true, "no-user.jpg", createUserDto);

    }

    @Test
    @DisplayName("Save User without Field Enabled")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testSaveUser_WithoutFieldEnabled() throws CustomAssassinException, Exception {
        // Arrange
        CreateUserDto createUserDto = userUtilTest.createDefaultCreateUserDto(
                null,
                "static/images/no-user.jpg",
                "[{\"name\": \"ROLE_USER\"}, {\"name\": \"ROLE_ADMIN\"}, {\"name\": \"ROLE_RECEPCIONIST\"}]");

        // Act
        User savedUser = userService.save(createUserDto);

        // Assert
        userUtilTest.assertSavedUser(savedUser, null, "no-user.jpg", createUserDto);
    }

    @Test
    @DisplayName("Save User without Field Profile Image")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testSaveUser_WithoutFieldProfileImage() throws CustomAssassinException, Exception {
        // Arrange
        CreateUserDto createUserDto = userUtilTest.createDefaultCreateUserDto(
                true,
                null,
                "[{\"name\": \"ROLE_USER\"}, {\"name\": \"ROLE_ADMIN\"}, {\"name\": \"ROLE_RECEPCIONIST\"}]");

        // Act
        User savedUser = userService.save(createUserDto);

        // Assert
        userUtilTest.assertSavedUser(savedUser, true, null, createUserDto);
    }

    @Test
    @DisplayName("Save User without Field Roles")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testSaveUser_WithoutFieldRoles() throws CustomAssassinException, Exception {
        // Arrange
        CreateUserDto createUserDto = userUtilTest.createDefaultCreateUserDto(
                true,
                "static/images/no-user.jpg",
                null);

        // Act
        User savedUser = userService.save(createUserDto);

        // Assert
        userUtilTest.assertSavedUser(savedUser, true, "no-user.jpg", createUserDto);
    }

    @Test
    @DisplayName("Save User without Many Fields")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testSaveUser_WithoutManyFields() throws CustomAssassinException, Exception {
        // Arrange
        CreateUserDto createUserDto = userUtilTest.createDefaultCreateUserDto(
                null,
                null,
                null);

        // Act
        User savedUser = userService.save(createUserDto);

        // Assert
        userUtilTest.assertSavedUser(savedUser, null, null, createUserDto);
    }

}
