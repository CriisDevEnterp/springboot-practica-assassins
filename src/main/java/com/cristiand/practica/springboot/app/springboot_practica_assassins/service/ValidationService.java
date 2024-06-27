package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

/**
 * Interfaz que define operaciones relacionadas con validaciones.
 */
public interface ValidationService {

    /**
     * Valida si existe el username en el sistema.
     *
     * @param username el nombre de usuario a verificar.
     * @return true si el username ya se encuentra ocupado.
     */
    boolean existsByUsername(String username);

}
