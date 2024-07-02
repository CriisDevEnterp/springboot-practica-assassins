package com.cristiand.practica.springboot.app.springboot_practica_assassins.util;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.CreateUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.UpdateUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Clase utilitaria para la creación de objetos User a partir de DTOs (Objetos
 * de Transferencia de Datos).
 * Esta clase es un componente de Spring y se utiliza para la creación de
 * usuarios con datos encriptados.
 */
@Component
public class UserUtil {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Crea un nuevo objeto User basado en el CreateUserDto proporcionado.
     *
     * @param theUserDto El CreateUserDto que contiene los datos del usuario.
     * @return Un nuevo objeto User poblado con los datos del CreateUserDto.
     */
    public User createUserFromDto(CreateUserDto theUserDto) {
        // Crea un nuevo objeto User.
        User newUser = new User();

        // Configura los atributos del User a partir de los datos del CreateUserDto.
        newUser.setUsername(theUserDto.username());
        // Encripta la contraseña usando BCryptPasswordEncoder.
        newUser.setPassword(passwordEncoder.encode(theUserDto.password()));
        // Establece el estado del usuario (habilitado/deshabilitado).
        newUser.setEnabled(theUserDto.enabled() != null ? theUserDto.enabled() : true);
        newUser.setFirstName(theUserDto.firstName());
        newUser.setLastName(theUserDto.lastName());
        newUser.setEmail(theUserDto.email());
        // Establece la fecha de creación del usuario.
        newUser.setCreatedAt(new Date());
        // Inicializa la lista de roles del usuario como vacía.
        newUser.setRoles(new ArrayList<>());

        // Devuelve el objeto User creado y poblado.
        return newUser;
    }

    /**
     * Actualiza los campos del usuario existente con los datos del UpdateUserDto.
     * 
     * @param existingUser  El usuario existente que se actualizará.
     * @param updateUserDto El objeto UpdateUserDto que contiene los nuevos datos
     *                      del usuario.
     * @return El usuario actualizado.
     */
    public User updateUserFromDto(User existingUser, UpdateUserDto updateUserDto) {
        // Actualiza los campos del usuario existente con los datos del UpdateUserDto

        // Actualiza el username si se proporciona
        if (updateUserDto.username() != null) {
            existingUser.setUsername(updateUserDto.username());
        }

        // Actualiza la contraseña si se proporciona y se encripta con el codificador de
        // contraseñas
        if (updateUserDto.password() != null) {
            String encodedPassword = passwordEncoder.encode(updateUserDto.password());
            existingUser.setPassword(encodedPassword);
        }

        // Actualiza el nombre si se proporciona
        if (updateUserDto.firstName() != null) {
            existingUser.setFirstName(updateUserDto.firstName());
        }

        // Actualiza el apellido si se proporciona
        if (updateUserDto.lastName() != null) {
            existingUser.setLastName(updateUserDto.lastName());
        }

        // Actualiza el correo electrónico si se proporciona
        if (updateUserDto.email() != null) {
            existingUser.setEmail(updateUserDto.email());
        }

        // Actualiza el estado habilitado si se proporciona
        if (updateUserDto.enabled() != null) {
            existingUser.setEnabled(updateUserDto.enabled());
        }

        // Otros campos que puedan necesitar actualización pueden agregarse aquí

        return existingUser;
    }

}
