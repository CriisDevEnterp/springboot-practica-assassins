package com.cristiand.practica.springboot.app.springboot_practica_assassins.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.service.UserService;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.util.ValidationUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    // System.out.println("---------------------------------------------------------------------------------------------------------------");
    // System.out.println("UserController.createUser()");
    // System.out.println(theUser.toString());
    // System.out.println("---------------------------------------------------------------------------------------------------------------");
    

    @Autowired
    private UserService userService;

    // Endpoint para obtener todos los usuarios
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> theUsers = userService.findAll();
        return new ResponseEntity<>(theUsers, HttpStatus.OK);
    }

    // Endpoint para crear un nuevo usuario
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody User theUser, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return ValidationUtils.handleValidationErrors(bindingResult);
        }
        
        User newUser = userService.save(theUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    
}
