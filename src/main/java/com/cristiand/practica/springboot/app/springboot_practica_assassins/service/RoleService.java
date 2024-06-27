package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.Role;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain.CustomAssassinException;
import java.util.List;

/**
 * Interfaz que define métodos para operaciones relacionadas con roles.
 */
public interface RoleService {

    /**
     * Busca todos los roles disponibles.
     *
     * @return Lista de roles disponibles.
     * @throws CustomAssassinException Si ocurre un error específico relacionado con
     *                                 la falta de roles.
     * @throws Exception               Si ocurre un error inesperado durante la
     *                                 búsqueda de roles.
     */
    public List<Role> findAll() throws CustomAssassinException, Exception;

}
