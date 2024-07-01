package com.cristiand.practica.springboot.app.springboot_practica_assassins.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/**
 * Validador personalizado para verificar si un correo electrónico tiene un formato válido.
 * Este validador utiliza una expresión regular para asegurarse de que el correo electrónico
 * sigue un formato estándar (por ejemplo, nombre@dominio.com).
 */
@Component
public class ValidFormatEmailValidation implements ConstraintValidator<ValidFormatEmail, String> {

    // Expresión regular que define el formato aceptable para los correos electrónicos
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    // Compila la expresión regular en un patrón para realizar las validaciones
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    /**
     * Valida si el correo electrónico proporcionado cumple con el formato definido.
     *
     * @param email   el correo electrónico a validar.
     * @param context el contexto del validador de la restricción.
     * @return true si el correo electrónico es válido, false en caso contrario.
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        // Si el correo electrónico es nulo o está vacío, se considera inválido
        if (email == null || email.isEmpty()) {
            return false;
        }
        // Compara el correo electrónico con el patrón definido y devuelve el resultado
        return pattern.matcher(email).matches();
    }
    
}
