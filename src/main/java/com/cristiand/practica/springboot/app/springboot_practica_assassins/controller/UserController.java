package com.cristiand.practica.springboot.app.springboot_practica_assassins.controller;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.CreateUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.FindUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain.CustomAssassinException;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.service.UserService;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.util.ValidationUtils;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.*;

/**
 * Permite solicitudes desde el origen http://localhost:4200 para acceder a este
 * controlador.
 */
@CrossOrigin(origins = { "http://localhost:4200" })

/**
 * Marca esta clase como un controlador REST que maneja las operaciones
 * relacionadas con los usuarios.
 */
@RestController
/**
 * Define la raíz del mapeo para todas las solicitudes en este controlador.
 * Todas las rutas definidas en este controlador serán relativas a /api/users.
 */
@RequestMapping("/api/users")
public class UserController {

    /**
     * Inyección del servicio UserService para manejar operaciones relacionadas con
     * usuarios.
     */
    @Autowired
    private UserService userService;

    /**
     * Maneja las solicitudes GET en la ruta /api/users/all para obtener todos los
     * usuarios.
     * Solo los usuarios con el rol de usuario pueden acceder a esta función.
     *
     * @return ResponseEntity que contiene la lista de usuarios y el estado HTTP 200
     *         (OK).
     */
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<List<User>> getAllUsers() throws CustomAssassinException, Exception {
        // Obtiene la lista de todos los usuarios desde el servicio.
        List<User> users = userService.findAll();
        // Retorna una respuesta con la lista de usuarios y código de estado HTTP 200
        // (OK).
        return new ResponseEntity<>(users, OK);
    }

    /**
     * Maneja las solicitudes GET en la ruta /api/users/all/paged para obtener una
     * página de usuarios junto con sus roles.
     *
     * @param page el número de la página a obtener, por defecto es 0.
     * @param size el tamaño de la página, por defecto es 5.
     * @return ResponseEntity que contiene la página de usuarios encontrada y el
     *         código de estado HTTP 200 (OK).
     */
    @GetMapping("/all/paged")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<Page<User>> getAllUsersPaged(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) throws CustomAssassinException, Exception {
        // Crea un objeto Pageable con el número de página y el tamaño de página
        // especificados.
        Pageable pageable = PageRequest.of(page, size);
        // Llama al servicio para obtener una página de usuarios junto con sus roles.
        Page<User> users = userService.findAll(pageable);
        // Retorna una respuesta con página de usuarios y código de estado HTTP 200
        // (OK).
        return new ResponseEntity<>(users, OK);
    }

    /**
     * Controlador para obtener un usuario por su ID.
     *
     * @param id el ID del usuario a buscar
     * @return ResponseEntity que contiene el usuario encontrado y el código de
     *         estado HTTP 200 (OK).
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws CustomAssassinException, Exception {
        // Llama al servicio para obtener el usuario por su ID.
        User user = userService.findById(id);
        // Retorna una respuesta con el usuario encontrado y el código de estado HTTP
        // 200 (OK).
        return new ResponseEntity<>(user, OK);
    }

    /**
     * Controlador para realizar una búsqueda filtrada de usuarios utilizando un DTO
     * de búsqueda.
     * Si no se proporciona un DTO en el cuerpo de la solicitud, se retornarán todos
     * los usuarios.
     *
     * @param findUserDto DTO que contiene los criterios de búsqueda para filtrar
     *                    usuarios (opcional).
     * @return ResponseEntity que contiene la lista de usuarios que cumplen con los
     *         criterios de búsqueda y el código de estado HTTP 200 (OK).
     */
    @PostMapping("/filter")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<List<User>> performFilteredSearchOfUsers(
            @RequestBody(required = false) FindUserDto findUserDto) throws CustomAssassinException, Exception {
        // Llama al servicio userService para realizar la búsqueda filtrada de usuarios.
        List<User> users = userService.performFilteredSearch(findUserDto);
        // Retorna una respuesta con la lista de usuarios encontrados y el código de
        // estado HTTP 200 (OK).
        return new ResponseEntity<>(users, OK);
    }

    /**
     * Controlador para guardar un nuevo usuario utilizando un DTO junto con su
     * proceso de validación.
     *
     * @param theUserDto    El objeto CreateUserDto que contiene los datos del
     *                      usuario a crear.
     * @param bindingResult El resultado del proceso de validación del
     *                      CreateUserDto.
     * @return ResponseEntity con el nuevo usuario creado y el estado HTTP 201
     *         (CREATED) si la creación es exitosa.
     */
    @PostMapping("")
    public ResponseEntity<?> save(@Valid @ModelAttribute CreateUserDto theUserDto, BindingResult bindingResult)
            throws CustomAssassinException, Exception {
        System.out.println("----------------------------------");
        System.out.println(theUserDto);
        System.out.println("----------------------------------");
        // Validar el objeto CreateUserDto usando las anotaciones de validación.
        if (bindingResult.hasErrors()) {
            ValidationUtils.handleValidationErrors(bindingResult);
        }

        // Guardar el nuevo usuario utilizando el servicio UserService.
        User newUser = userService.save(theUserDto);

        // Devolver una respuesta con el nuevo usuario y el código de estado 201
        // (CREATED).
        return new ResponseEntity<>(newUser, CREATED);
    }

}
