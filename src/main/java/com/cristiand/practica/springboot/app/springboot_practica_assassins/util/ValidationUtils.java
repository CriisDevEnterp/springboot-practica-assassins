package com.cristiand.practica.springboot.app.springboot_practica_assassins.util;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public class ValidationUtils {
    
    /**
    * Maneja los errores de validación y devuelve un ResponseEntity con los detalles del error.
    * 
    * @param bindingResult el resultado del enlace que contiene los errores de validación
    * @return ResponseEntity con los detalles del error
    */
    public static ResponseEntity<Map<String, String>> handleValidationErrors(BindingResult bindingResult) {
        // Mapa para almacenar los errores de campo
        Map<String, String> errors = new HashMap<>();

        // Itera a través de los errores de campo y los agrega al mapa de errores
        bindingResult.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        // Devuelve un ResponseEntity con el estado de error de solicitud incorrecta y los detalles del error
        return ResponseEntity.badRequest().body(errors);
    }

}
