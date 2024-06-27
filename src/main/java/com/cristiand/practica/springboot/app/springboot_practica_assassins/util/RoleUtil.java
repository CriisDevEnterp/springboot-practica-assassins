package com.cristiand.practica.springboot.app.springboot_practica_assassins.util;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dao.RoleRepository;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.CreateUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Clase utilitaria para manejar roles de usuario basados en DTOs (Objetos de
 * Transferencia de Datos).
 * Esta clase es un componente de Spring y se utiliza para extraer roles de un
 * CreateUserDto y validarlos contra una base de datos de roles almacenados.
 */
@Component
public class RoleUtil {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Extrae roles de un CreateUserDto y los valida contra la base de datos de
     * roles almacenados.
     *
     * @param theUserDto El CreateUserDto que contiene los roles solicitados.
     * @return Una lista de objetos Role válidos y configurados para el usuario.
     * @throws IllegalAccessException Si ocurre un error al acceder a los datos de
     *                                los roles.
     */
    public List<Role> extractRolesFromDto(CreateUserDto theUserDto) throws IllegalAccessException {
        List<Role> roles = new ArrayList<>();

        // Obtener el rol por defecto "ROLE_USER" de la base de datos.
        Role defaultRole = roleRepository.findByName("ROLE_USER");

        // Verificar si el rol por defecto existe en la base de datos.
        if (defaultRole == null) {
            throw new IllegalStateException("El rol por defecto 'ROLE_USER' no está configurado en la base de datos.");
        }

        roles.add(defaultRole); // Añadir siempre el rol por defecto.

        // Verificar y convertir roles adicionales solicitados desde el DTO.
        if (theUserDto.roles() != null && !theUserDto.roles().isEmpty()) {
            List<Role> requestedRoles = convertRolesFromString(theUserDto.roles());
            for (Role requestedRole : requestedRoles) {
                // Buscar roles existentes en la base de datos.
                Role existingRole = roleRepository.findByName(requestedRole.getName());
                if (existingRole != null && !roles.contains(existingRole)) {
                    roles.add(existingRole); // Añadir roles existentes si no están duplicados.
                }
            }
        }

        return roles;
    }

    /**
     * Convierte una representación de roles en formato JSON a una lista de objetos
     * Role.
     *
     * @param rolesJson Una cadena JSON que representa los roles solicitados.
     * @return Una lista de objetos Role convertidos desde la representación JSON.
     * @throws ValidationException Si hay errores durante el procesamiento del JSON.
     */
    private List<Role> convertRolesFromString(String rolesJson) throws ValidationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Convertir la cadena JSON a una lista de objetos Role usando Jackson
            // ObjectMapper
            return objectMapper.readValue(rolesJson, new TypeReference<List<Role>>() {
            });
        } catch (JsonProcessingException e) {
            // Capturar errores específicos de procesamiento JSON
            throw new ValidationException("Error al convertir roles de JSON: " + e.getMessage());
        } catch (Exception e) {
            // Capturar cualquier otro error inesperado
            throw new ValidationException("Error inesperado al convertir roles de JSON: " + e.getMessage());
        }
    }

}
