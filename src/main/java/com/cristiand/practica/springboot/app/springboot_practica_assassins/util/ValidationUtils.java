package com.cristiand.practica.springboot.app.springboot_practica_assassins.util;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.enums.ErrorCode;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain.CustomAssassinException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * Utilidad para manejar errores de validación.
 */
public class ValidationUtils {

    /**
     * Maneja los errores de validación y lanza una CustomAssassinException con los
     * detalles del error.
     *
     * @param bindingResult el resultado del enlace que contiene los errores de
     *                      validación.
     * @throws CustomAssassinException si hay errores de validación.
     */
    public static void handleValidationErrors(BindingResult bindingResult) throws CustomAssassinException {
        if (bindingResult.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();

            // Recorre los errores de campo y los agrega al mapa de errores.
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String fieldName = fieldError.getField();
                String errorMessage = fieldError.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            }

            // Lanza una excepción personalizada con los detalles de los errores.
            throw new CustomAssassinException("Errores de validación.", ErrorCode.INVALID_REQUEST,
                    Collections.unmodifiableMap(errors));
        }
    }

}
