package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dao.ValidationRepository;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de validación.
 * Proporciona métodos para realizar validaciones utilizando el repositorio de
 * validación.
 */
@Service
public class ValidationServiceImpl implements ValidationService {

    @Autowired
    private ValidationRepository<User, Long> validationRepositoryUsers;

    /**
     * Verifica si existe una entidad con un nombre de usuario dado.
     *
     * @param username el nombre de usuario a verificar.
     * @return true si existe una entidad con el nombre de usuario dado, false en
     *         caso contrario.
     */
    @Override
    public boolean existsByUsername(String username) {
        return validationRepositoryUsers.existsByUsername(username);
    }

    /**
     * Verifica si existe una entidad con un correo dado.
     *
     * @param email el correo a verificar.
     * @return true si existe una entidad con el correo dado, false en caso
     *         contrario.
     */
    @Override
    public boolean existsByEmail(String email) {
        return validationRepositoryUsers.existsByEmail(email);
    }

}
