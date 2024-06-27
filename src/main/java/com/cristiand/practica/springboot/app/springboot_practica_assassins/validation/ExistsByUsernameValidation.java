package com.cristiand.practica.springboot.app.springboot_practica_assassins.validation;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.service.ValidationService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementación del validador de la anotación @ExistsByUsername.
 * Verifica si el nombre de usuario ya existe en el sistema utilizando el servicio de validación.
 */
@Component
public class ExistsByUsernameValidation implements ConstraintValidator<ExistsByUsername, String> {

    @Autowired
    private ValidationService validationService;

    /**
     * Verifica si el nombre de usuario es válido (no existe en el sistema).
     *
     * @param username el nombre de usuario a validar.
     * @param context el contexto del validador de la restricción.
     * @return true si el nombre de usuario no existe en el sistema, false en caso contrario.
     */
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (validationService == null) {
            return true; // Manejar con gracia si el servicio no está disponible.
        }

        boolean usernameExists = validationService.existsByUsername(username);
        
        if (usernameExists) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    context.getDefaultConstraintMessageTemplate().replace("{username}", username)
            ).addConstraintViolation();
        }
        
        return !usernameExists;
    }
    
}
