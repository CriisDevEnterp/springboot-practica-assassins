package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dao.RoleRepository;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dao.UserRepository;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.Role;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        // TODO: Función para retornar lista de usuarios
        List<User> users = userRepository.findAll();
        // Inicializar la colección de roles para cada usuario antes de devolverlos
        users.forEach(user -> user.getRoles().size());
        return users;
    }

    @Override
    @Transactional
    public User save(User theUser) {
        // Cifrar la contraseña del usuario
        theUser.setPassword(passwordEncoder.encode(theUser.getPassword()));

        // Establecer el valor de enabled si no se proporciona
        theUser.setEnabled(theUser.isEnabled() != null ? theUser.isEnabled() : true);

        // Obtener el rol por defecto "ROLE_USER"
        Role defaultRole = roleRepository.findByName("ROLE_USER");

        // Obtener los roles válidos para los nuevos usuarios
        Set<Role> theRoles = new HashSet<>();
        if (theUser.getRoles() != null) {
            theRoles = theUser.getRoles().stream()
                .map(Role::getName)
                .map(roleRepository::findByName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        }

        // Asegurarse de que "ROLE_USER" esté siempre presente
        theRoles.add(defaultRole);

        // Asignar roles al usuario
        theUser.setRoles(new ArrayList<>(theRoles));

        // Guardar el usuario en la base de datos dentro de una transacción
        return userRepository.save(theUser);
    }

}
