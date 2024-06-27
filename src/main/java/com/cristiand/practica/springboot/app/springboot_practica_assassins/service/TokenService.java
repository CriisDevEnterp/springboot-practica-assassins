package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.LoginRequest;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.LoginResponse;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.TokenResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Interfaz que define los métodos para el manejo de operaciones relacionadas
 * con la autenticación y generación de tokens.
 */
public interface TokenService {

    /**
     * Autentica al usuario basado en las credenciales proporcionadas y genera
     * tokens de acceso y de refresco.
     *
     * @param loginRequest DTO que contiene las credenciales del usuario.
     * @return LoginResponse que contiene los tokens generados y la información del
     *         usuario.
     * @throws BadCredentialsException si las credenciales proporcionadas son
     *                                 inválidas.
     */
    LoginResponse login(LoginRequest loginRequest) throws BadCredentialsException;

    /**
     * Refresca el token de acceso utilizando un token de refresco válido.
     *
     * @param refreshToken El token de refresco.
     * @return TokenResponse que contiene el nuevo token de acceso.
     * @throws UsernameNotFoundException si el usuario asociado con el token de
     *                                   refresco no se encuentra.
     */
    TokenResponse refreshAccessToken(String refreshToken) throws UsernameNotFoundException;

}
