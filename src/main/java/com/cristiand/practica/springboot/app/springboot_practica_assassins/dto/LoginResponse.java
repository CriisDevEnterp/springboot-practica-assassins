package com.cristiand.practica.springboot.app.springboot_practica_assassins.dto;

public record LoginResponse(
    String accessToken,
    Long accessTokenExpiresIn,
    String refreshToken,
    Long refreshTokenExpiresIn,
    String firstName,
    String lastName,
    String email,
    String scope,
    String tokenType
) { }
