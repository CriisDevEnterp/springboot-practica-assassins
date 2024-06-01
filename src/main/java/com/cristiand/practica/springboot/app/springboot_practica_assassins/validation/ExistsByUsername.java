package com.cristiand.practica.springboot.app.springboot_practica_assassins.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsByUsernameValidation.class)
public @interface ExistsByUsername {
    String message() default "ya existe en la base de datos!, escoja otro username.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
