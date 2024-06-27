package com.cristiand.practica.springboot.app.springboot_practica_assassins.util;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.controller.UserController;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.CreateUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.Role;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain.CustomAssassinException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
public class UserUtilTest {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleUtil roleUtil;

    @Autowired
    private Validator validator;

    @Autowired
    UserController userController;

    public CreateUserDto createDefaultCreateUserDto(Boolean enabled, String profileImagePath, String roles)
            throws IOException {
        profileImagePath = (profileImagePath == null) ? "static/images/no-user.jpg" : profileImagePath;
        roles = (roles == null)
                ? "[{\"name\": \"ROLE_USER\"}, {\"name\": \"ROLE_ADMIN\"}, {\"name\": \"ROLE_RECEPCIONIST\"}]"
                : roles;
        MockMultipartFile profileImage = createMockMultipartFileFromResource(profileImagePath);

        return new CreateUserDto(
                "cristian",
                "123456",
                enabled,
                "Cristian",
                "de la Rosa",
                "cristian@prueba.com",
                profileImage,
                roles);
    }

    public void assertSavedUser(User savedUser, Boolean expectedEnabled, String expectedProfileImage,
            CreateUserDto createUserDto) throws IllegalAccessException {
        assertNotNull(savedUser, "El usuario guardado no debe ser nulo");
        assertEquals("cristian", savedUser.getUsername(), "El nombre de usuario debe ser 'cristian'");
        assertTrue(passwordEncoder.matches("123456", savedUser.getPassword()),
                "La contraseña debe estar cifrada correctamente");

        assertProfileImage(savedUser.getProfileImage(), expectedProfileImage);
        assertEnabledStatus(savedUser.getEnabled(), expectedEnabled);
        assertRoles(savedUser, createUserDto);
    }

    private void assertProfileImage(String actualProfileImage, String expectedProfileImage) {
        assertTrue(actualProfileImage.startsWith("uploads/"),
                "La ruta de la imagen de perfil debe comenzar con 'uploads/'");

        if (expectedProfileImage != null) {
            assertTrue(actualProfileImage.endsWith(expectedProfileImage),
                    "La ruta de la imagen de perfil debe terminar con '" + expectedProfileImage + "'");
        } else {
            assertTrue(actualProfileImage.endsWith("no-user.jpg"),
                    "La ruta de la imagen de perfil debe terminar con 'no-user.jpg'");
        }
    }

    private void assertEnabledStatus(Boolean actualEnabled, Boolean expectedEnabled) {
        assertTrue(actualEnabled != null ? actualEnabled : false, "El usuario debe estar habilitado");

        if (expectedEnabled != null) {
            assertEquals(expectedEnabled, actualEnabled, "El estado de habilitación debe ser el esperado");
        }
    }

    private void assertRoles(User savedUser, CreateUserDto createUserDto) throws IllegalAccessException {
        List<Role> expectedRoles = roleUtil.extractRolesFromDto(createUserDto);
        Set<String> savedUserRoles = savedUser.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        Set<String> expectedRoleNames = expectedRoles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        assertEquals(expectedRoleNames, savedUserRoles, "Los roles deben ser los esperados");
    }

    private MockMultipartFile createMockMultipartFileFromResource(String resourcePath) throws IOException {
        Resource resource = new ClassPathResource(resourcePath);
        String originalFileName = resource.getFilename();
        String contentType = "image/jpeg";
        byte[] content = StreamUtils.copyToByteArray(resource.getInputStream());
        return new MockMultipartFile("profileImage", originalFileName, contentType, content);
    }


    public void validateAndAssertError(CreateUserDto createUserDto, String fieldName, String expectedError) {
        // Arrange
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(createUserDto, "createUserDto");

        // Act
        validator.validate(createUserDto, bindingResult);
        CustomAssassinException exception = assertThrows(CustomAssassinException.class, () -> {
            userController.save(createUserDto, bindingResult);
        });

        // Assert
        assertNotNull(exception);
        assertEquals("E003", exception.getCode().getCode());
        assertEquals("INVALID_REQUEST", exception.getCode().toString());
        assertEquals("Parámetros de solicitud inválidos.", exception.getCode().getDescription());
        assertEquals("Errores de validación.", exception.getErrorMessage());

        Map<String, Object> errorDetails = exception.getErrorsDetails();
        assertNotNull(errorDetails);
        assertEquals(expectedError, errorDetails.get(fieldName));
    }

}
