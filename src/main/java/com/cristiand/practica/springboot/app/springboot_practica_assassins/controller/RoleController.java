package com.cristiand.practica.springboot.app.springboot_practica_assassins.controller;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.Role;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain.CustomAssassinException;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.service.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.*;

/**
 * Permite solicitudes desde el origen http://localhost:4200 para acceder a este
 * controlador.
 */
@CrossOrigin(origins = { "http://localhost:4200" })
/**
 * Marca esta clase como un controlador REST que maneja las operaciones
 * relacionadas con los roles.
 */
@RestController
/**
 * Define la raíz del mapeo para todas las solicitudes en este controlador.
 * Todas las rutas definidas en este controlador serán relativas a /api/roles.
 */
@RequestMapping("/api/roles")
public class RoleController {

    /**
     * Inyección del servicio RoleService para manejar operaciones relacionadas con
     * roles.
     */
    @Autowired
    private RoleService roleService;

    /**
     * Maneja las solicitudes GET en la ruta /api/roles/all para obtener todos los
     * roles.
     * Solo los usuarios con el rol de usuario pueden acceder a esta función.
     *
     * @return ResponseEntity que contiene la lista de roles y el estado HTTP 200
     *         (OK).
     * @throws CustomAssassinException Si ocurre un error específico relacionado con
     *                                 los roles.
     * @throws Exception               Si ocurre un error inesperado durante la
     *                                 obtención de los roles.
     */
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<List<Role>> getAllRoles() throws CustomAssassinException, Exception {
        // Llama al servicio findAll para obtener el listado de roles.
        List<Role> roles = roleService.findAll();

        // Retorna una respuesta con la lista de roles y código de estado HTTP 200 (OK).
        return new ResponseEntity<>(roles, OK);
    }

}
