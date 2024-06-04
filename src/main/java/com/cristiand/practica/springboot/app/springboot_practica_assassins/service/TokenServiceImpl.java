package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dao.UserRepository;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.LoginRequest;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.LoginResponse;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.Role;
import java.time.Instant;
import java.util.stream.Collectors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    private final JwtEncoder jwtEncoder;

    private final UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    public TokenServiceImpl(JwtEncoder jwtEncoder, UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        var user = userRepository.findByUsername(loginRequest.username());

        if (user == null || !isLoginCorrect(loginRequest, passwordEncoder)) {
            return new LoginResponse("", 0L);  // Retornar token vacío y expiración cero en caso de falla
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var scopes = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));
               
        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(String.valueOf(user.getId()))
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(jwtValue, expiresIn);
    }


    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        var user = userRepository.findByUsername(loginRequest.username());
        return passwordEncoder.matches(loginRequest.password(), user.getPassword());
    }
    
}
