package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dao.RoleRepository;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.Role;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.enums.ErrorCode;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain.CustomAssassinException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Retorna una lista de todos los roles desde el repositorio.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() throws CustomAssassinException, Exception {
        try {
            // Obtiene todos los roles desde el repositorio.
            List<Role> roles = roleRepository.findAllWithUsers();

            // Verifica si la lista de roles está vacía o es nula.
            // Si es así, lanza una CustomAssassinException con un mensaje y un código de
            // error específicos.
            if (roles == null || roles.isEmpty()) {
                throw new CustomAssassinException("La lista de roles está vacía.", ErrorCode.LIST_EMPTY);
            }

            // Retorna la lista de roles si no está vacía.
            return roles;
        } catch (CustomAssassinException e) {
            // Si se lanza una CustomAssassinException, la vuelve a lanzar sin modificarla.
            throw e;
        } catch (Exception e) {
            // Si ocurre cualquier otra excepción, lanza una nueva excepción con un mensaje
            // adicional.
            throw new Exception("Ocurrió un error inesperado. " + e.getMessage());
        }
    }

}
