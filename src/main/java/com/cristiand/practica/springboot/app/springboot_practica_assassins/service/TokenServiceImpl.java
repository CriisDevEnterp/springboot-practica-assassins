package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dao.TokenRepository;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.LoginRequest;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.LoginResponse;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.TokenResponse;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private TokenRepository tokenRepository;

    /**
     * Servicio que maneja el proceso de inicio de sesión autenticando al usuario y
     * generando tokens.
     *
     * @param loginRequest DTO que contiene las credenciales del usuario.
     * @return LoginResponse que contiene los tokens de acceso y de refresco.
     * @throws BadCredentialsException si las credenciales son inválidas.
     */
    @Override
    public LoginResponse login(LoginRequest loginRequest) throws BadCredentialsException {
        // Autentica al usuario basado en las credenciales proporcionadas.
        User user = tokenUtil.authenticateUser(loginRequest);
        // Genera el token de acceso.
        String accessToken = tokenUtil.createToken(user, TokenUtil.ACCESS_TOKEN_EXPIRES_IN);
        // Genera el token de refresco.
        String refreshToken = tokenUtil.createToken(user, TokenUtil.REFRESH_TOKEN_EXPIRES_IN);

        // Crea y retorna la respuesta de inicio de sesión.
        return tokenUtil.createLoginResponse(user, accessToken, refreshToken);
    }

    /**
     * Servicio que refresca el token de acceso utilizando el token de refresco.
     *
     * @param refreshToken El token de refresco.
     * @return TokenResponse que contiene el nuevo token de acceso.
     * @throws UsernameNotFoundException si el usuario no se encuentra.
     */
    @Override
    public TokenResponse refreshAccessToken(String refreshToken) throws UsernameNotFoundException {
        // Decodifica el token de refresco.
        Jwt jwt = jwtDecoder.decode(refreshToken);
        // Valida el token de refresco.
        tokenUtil.validateRefreshToken(jwt);

        // Obtiene el ID del usuario del token.
        Long userId = Long.valueOf(jwt.getSubject());
        // Busca al usuario en el repositorio.
        User user = tokenRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado."));

        // Genera un nuevo token de acceso.
        String newAccessToken = tokenUtil.createToken(user, TokenUtil.ACCESS_TOKEN_EXPIRES_IN);
        // Retorna la respuesta con el nuevo token de acceso.
        return new TokenResponse(newAccessToken, TokenUtil.ACCESS_TOKEN_EXPIRES_IN);
    }

}
