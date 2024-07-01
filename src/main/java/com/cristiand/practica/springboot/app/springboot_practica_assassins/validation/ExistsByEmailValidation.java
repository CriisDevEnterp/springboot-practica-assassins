package com.cristiand.practica.springboot.app.springboot_practica_assassins.validation;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.service.ValidationService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementación del validador de la anotación @ExistsByEmail.
 * Verifica si el correo ya existe en el sistema utilizando el servicio de
 * validación.
 */
@Component
public class ExistsByEmailValidation implements ConstraintValidator<ExistsByEmail, String> {

    @Autowired
    private ValidationService validationService;

    /**
     * Verifica si el correo es válido (no existe en el sistema).
     *
     * @param email   el correo a validar.
     * @param context el contexto del validador de la restricción.
     * @return true si el correo no existe en el sistema, false en caso contrario.
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (validationService == null) {
            return true; // Manejar con gracia si el servicio no está disponible.
        }

        boolean emailExists = validationService.existsByEmail(email);

        if (emailExists) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    context.getDefaultConstraintMessageTemplate().replace("{email}", email))
                    .addConstraintViolation();
        }

        return !emailExists;
    }

}
