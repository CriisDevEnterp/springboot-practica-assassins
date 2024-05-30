package com.cristiand.practica.springboot.app.springboot_practica_assassins.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.service.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExistsByUsernameValidation implements ConstraintValidator<ExistsByUsername, String> {

    @Autowired
    private UserService userService;
    
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return (userService == null ? true : !userService.existsByUsername(username));
    }
    
}
