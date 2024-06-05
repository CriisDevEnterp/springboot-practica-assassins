package com.cristiand.practica.springboot.app.springboot_practica_assassins.controller;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.CreateUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.FindUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.service.UserService;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.util.ValidationUtils;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDto theUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidationUtils.handleValidationErrors(bindingResult);
        }
        
        User newUser = userService.save(theUserDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<List<User>> searchUsers(@RequestBody(required = false) FindUserDto findUserDto) {
        List<User> theUsers = userService.filterUsers(findUserDto);
        return new ResponseEntity<>(theUsers, HttpStatus.OK);
    }
    
}
