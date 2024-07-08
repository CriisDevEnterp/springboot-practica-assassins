package com.cristiand.practica.springboot.app.springboot_practica_assassins.controller;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.LoginRequest;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.LoginResponse;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.TokenRefreshRequest;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.TokenResponse;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.service.TokenService;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.util.ValidationUtils;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
 * relacionadas con la autenticación del usuario.
 */
@RestController
/**
 * Define la raíz del mapeo para todas las solicitudes en este controlador.
 * Todas las rutas definidas en este controlador serán relativas a /auth.
 */
@RequestMapping("/auth")
public class TokenController {

    /**
     * Inyección del servicio TokenService para manejar operaciones relacionadas con
     * la autenticación.
     */
    @Autowired
    private TokenService tokenService;

    /**
     * Controlador para iniciar sesión utilizando un DTO con campos específicos a
     * validar junto con su proceso de validación.
     *
     * @param loginRequest  DTO que contiene los criterios para un inicio de sesión.
     * @param bindingResult El resultado del proceso de validación del LoginRequest.
     * @return ResponseEntity que contiene el token construido para el usuario y el
     *         código de estado HTTP 200 (OK).
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest,
            BindingResult bindingResult) {

        // Validar el objeto CreateUserDto usando las anotaciones de validación.
        if (bindingResult.hasErrors()) {
            ValidationUtils.handleValidationErrors(bindingResult);
        }

        // Llama al servicio login para realizar el inicio de sesión del usuario.
        LoginResponse loginResponse = tokenService.login(loginRequest);

        // Retorna una respuesta con el token del usuario y el código de estado HTTP 200
        // (OK).
        return new ResponseEntity<>(loginResponse, OK);
    }

    /**
     * Controlador para refrescar el token de acceso utilizando un DTO con el token
     * de refresco.
     *
     * @param tokenRefreshRequest DTO que contiene el token de refresco.
     * @return ResponseEntity que contiene el nuevo token de acceso y el código de
     *         estado HTTP 200 (OK).
     */
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshAccessToken(@RequestBody TokenRefreshRequest tokenRefreshRequest) {
        // Llama al servicio refreshAccessToken para obtener un nuevo token una vez que
        // expire el ciclo de vida del anterior.
        TokenResponse tokenResponse = tokenService.refreshAccessToken(tokenRefreshRequest.refreshToken());
        // Retorna una respuesta con el token nuevo para el usuario y el código de
        // estado HTTP 200 (OK).
        return new ResponseEntity<>(tokenResponse, OK);
    }

}
