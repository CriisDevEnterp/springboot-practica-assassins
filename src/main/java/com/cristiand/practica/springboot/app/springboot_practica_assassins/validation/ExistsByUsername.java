package com.cristiand.practica.springboot.app.springboot_practica_assassins.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación de validación personalizada para verificar si un nombre de usuario
 * ya existe en el sistema.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsByUsernameValidation.class)
public @interface ExistsByUsername {

    /**
     * El mensaje de error que se mostrará si la validación falla.
     *
     * @return el mensaje de error.
     */
    String message() default "El username '{username}' ya existe, escoja otro username.";

    /**
     * Permite especificar grupos de validación.
     *
     * @return los grupos de validación.
     */
    Class<?>[] groups() default {};

    /**
     * Permite especificar la carga útil de metadatos del validador.
     *
     * @return la carga útil de metadatos.
     */
    Class<? extends Payload>[] payload() default {};

}
