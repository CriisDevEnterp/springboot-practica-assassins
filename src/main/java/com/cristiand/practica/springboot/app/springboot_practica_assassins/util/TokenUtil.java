package com.cristiand.practica.springboot.app.springboot_practica_assassins.util;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dao.TokenRepository;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.LoginRequest;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.LoginResponse;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.Role;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;
import java.time.Instant;
import java.util.stream.Collectors;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

/**
 * Componente para la gestión de tokens JWT en la aplicación.
 */
@Component
public class TokenUtil {

    /**
     * Tiempo de expiración del token de acceso en segundos (5 minutos).
     */
    public static final long ACCESS_TOKEN_EXPIRES_IN = 300L;
    /**
     * Tiempo de expiración del token de refresco en segundos (7 días).
     */
    public static final long REFRESH_TOKEN_EXPIRES_IN = 3600L * 24L * 7L;
    /**
     * Emisor de los tokens JWT.
     */
    public static final String ISSUER = "http://backend-assassins.com";

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Autentica al usuario basado en las credenciales proporcionadas.
     *
     * @param loginRequest DTO que contiene las credenciales del usuario.
     * @return El usuario autenticado.
     * @throws BadCredentialsException si las credenciales son inválidas.
     */
    public User authenticateUser(LoginRequest loginRequest) throws BadCredentialsException {
        // Busca al usuario por nombre de usuario y roles.
        User user = tokenRepository.findByUsernameWithRoles(loginRequest.username());
        // Verifica las credenciales del usuario.
        if (user == null || !passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new BadCredentialsException("Credenciales no válidas.");
        }
        return user;
    }

    /**
     * Crea un token para el usuario proporcionado con un tiempo de expiración
     * específico.
     *
     * @param user      El usuario para el que se crea el token.
     * @param expiresIn Tiempo de expiración en segundos.
     * @return El token generado en formato String.
     */
    public String createToken(User user, long expiresIn) {
        Instant now = Instant.now();
        // Obtiene los roles del usuario y los convierte a una cadena.
        String scopes = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        // Construye el conjunto de reclamaciones del token.
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .subject(String.valueOf(user.getId()))
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .claim("username", user.getUsername())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("email", user.getEmail())
                .claim("jti", UUID.randomUUID().toString())
                .claim("profileImage", "http://localhost:8080/"+user.getProfileImage())
                .build();

        // Codifica y retorna el token.
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Valida el token de refresco.
     *
     * @param jwt El token JWT a validar.
     * @throws BadCredentialsException si el token es inválido.
     */
    public void validateRefreshToken(Jwt jwt) throws BadCredentialsException {
        // Verifica si el token o su emisor son nulos o si el emisor no coincide con el
        // esperado.
        if (jwt == null || jwt.getIssuer() == null || !ISSUER.equals(jwt.getIssuer().toString())) {
            throw new BadCredentialsException("Token de actualización no válido.");
        }
    }

    /**
     * Crea la respuesta de inicio de sesión.
     *
     * @param user         El usuario autenticado.
     * @param accessToken  El token de acceso generado.
     * @param refreshToken El token de refresco generado.
     * @return LoginResponse que contiene los tokens y la información del usuario.
     */
    public LoginResponse createLoginResponse(User user, String accessToken, String refreshToken) {
        return new LoginResponse(
                accessToken,
                ACCESS_TOKEN_EXPIRES_IN,
                refreshToken,
                REFRESH_TOKEN_EXPIRES_IN,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.joining(" ")),
                "Bearer");
    }

}
