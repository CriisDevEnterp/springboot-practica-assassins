package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dao.RoleRepository;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dao.UserRepository;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.CreateUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.FindUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.Role;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User save(CreateUserDto theUserDto) {
        User theUser = new User();
        theUser.setUsername(theUserDto.username());
        theUser.setPassword(theUserDto.password());
        theUser.setEnabled(theUserDto.enabled());
        theUser.setRoles(theUserDto.roles());

        // Cifrar la contraseña del usuario
        theUser.setPassword(passwordEncoder.encode(theUser.getPassword()));

        // Establecer el valor de enabled si no se proporciona
        theUser.setEnabled(theUser.getEnabled() != null ? theUser.getEnabled() : true);

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

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public List<User> filterUsers(FindUserDto findUserDto) {
        if (findUserDto == null) { // Corregido para evitar la excepción NullPointerException
            return userRepository.findAll();
        } else {
            return userRepository.findByUsernameContaining(findUserDto.username());
        }
    }

}
