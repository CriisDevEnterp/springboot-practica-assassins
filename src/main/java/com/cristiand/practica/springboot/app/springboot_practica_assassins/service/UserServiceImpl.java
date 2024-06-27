package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dao.UserRepository;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.CreateUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.FindUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.Role;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.enums.ErrorCode;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain.CustomAssassinException;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.util.RoleUtil;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.util.UploadFileUtil;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.util.UserUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Código para simular errores:
 * 
 * Para genéricos:
 * 
 * String simulatedError = null;
 * simulatedError.toString(); // Esto lanzará un NullPointerException
 * 
 * Para JSON mal formado:
 * 
 * String malformedJson = "invalid json";
 * convertRolesFromString(malformedJson);
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private RoleUtil roleUtil;

    @Autowired
    private UploadFileUtil uploadFileUtil;

    /**
     * Retorna una lista de todos los usuarios junto con sus roles desde el
     * repositorio.
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() throws CustomAssassinException, Exception {
        try {
            // Obtiene todos los usuarios junto con sus roles desde el repositorio.
            List<User> users = userRepository.findAllWithRoles();

            // Verifica si la lista de usuarios está vacía o es nula.
            // Si es así, lanza una CustomAssassinException con un mensaje y un código de
            // error específicos.
            if (users == null || users.isEmpty()) {
                throw new CustomAssassinException("La lista de usuarios está vacía.", ErrorCode.LIST_EMPTY);
            }

            // Retorna la lista de usuarios si no está vacía.
            return users;
        } catch (CustomAssassinException e) {
            // Si se lanza una CustomAssassinException, la vuelve a lanzar sin modificarla.
            throw e;
        } catch (Exception e) {
            // Si ocurre cualquier otra excepción, lanza una nueva excepción con un mensaje
            // adicional.
            throw new Exception("Ocurrió un error inesperado. " + e.getMessage());
        }
    }

    /**
     * Retorna una página de usuarios junto con sus roles desde el repositorio,
     * utilizando paginación.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) throws CustomAssassinException, Exception {
        try {
            // Obtiene una página de usuarios junto con sus roles desde el repositorio.
            Page<User> usersPage = userRepository.findAllWithRoles(pageable);

            // Verifica si la página de usuarios no tiene contenido.
            // Si es así, lanza una CustomAssassinException con un mensaje y un código de
            // error específicos.
            if (!usersPage.hasContent()) {
                throw new CustomAssassinException("La página de usuarios está vacía.", ErrorCode.PAGE_EMPTY);
            }

            // Retorna la página de usuarios si no está vacía.
            return usersPage;
        } catch (CustomAssassinException e) {
            // Si se lanza una CustomAssassinException, la vuelve a lanzar sin modificarla.
            throw e;
        } catch (Exception e) {
            // Si ocurre cualquier otra excepción, lanza una nueva excepción con un mensaje
            // adicional.
            throw new Exception("Ocurrió un error inesperado. " + e.getMessage());
        }
    }

    /**
     * Retorna al usuario buscado por su ID desde el repositorio.
     */
    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) throws CustomAssassinException, Exception {
        try {
            // Busca un usuario por su ID en el repositorio. Si no se encuentra, lanza una
            // CustomAssassinException.
            return userRepository.findByIdWithRoles(id).orElseThrow(
                    () -> new CustomAssassinException("Usuario no encontrado con id: " + id, ErrorCode.NOT_FOUND));
        } catch (CustomAssassinException e) {
            // Si se lanza una CustomAssassinException, la vuelve a lanzar sin modificarla.
            throw e;
        } catch (Exception e) {
            // Si ocurre cualquier otra excepción, lanza una nueva excepción con un mensaje
            // adicional.
            throw new Exception("Ocurrió un error inesperado. " + e.getMessage());
        }
    }

    /**
     * Realiza una búsqueda filtrada de usuarios basada en los criterios
     * especificados en {@link FindUserDto}. Si {@code findUserDto} es nulo o su
     * nombre de usuario está vacío, se retornan todos los usuarios con sus roles.
     * Si se encuentra algún usuario que coincida con el nombre de usuario
     * especificado en {@code findUserDto}, se retorna una lista de usuarios que
     * contienen dicho nombre de usuario en sus nombres.
     * 
     * @param findUserDto El objeto FindUserDto que contiene los datos del usuario a
     *                    buscar.
     * @return La lista de usuarios que coinciden con la búsqueda.
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> performFilteredSearch(FindUserDto findUserDto) throws CustomAssassinException, Exception {
        try {
            // Verifica si el DTO o el campo username dentro del DTO son nulos o vacíos.
            if (findUserDto == null || findUserDto.username() == null || findUserDto.username().isEmpty()) {
                // Si no se proporciona un username válido, devuelve todos los usuarios con sus
                // roles.
                return userRepository.findAllWithRoles();
            } else {
                // Obtiene el username del DTO.
                String username = findUserDto.username();
                // Realiza la búsqueda de usuarios cuyo nombre de usuario contenga el valor
                // proporcionado.
                List<User> users = userRepository.findByUsernameContainingWithRoles(username);
                // Si no se encontraron usuarios que cumplan con el criterio de búsqueda, lanza
                // una CustomAssassinException.
                if (users.isEmpty()) {
                    throw new CustomAssassinException(
                            "No se encontraron usuarios con un nombre de usuario que contenga: " + username,
                            ErrorCode.NOT_FOUND);
                }
                // Devuelve la lista de usuarios encontrados.
                return users;
            }
        } catch (CustomAssassinException e) {
            // Si se lanza una CustomAssassinException, la vuelve a lanzar sin modificarla.
            throw e;
        } catch (Exception e) {
            // Si ocurre cualquier otra excepción, lanza una nueva excepción con un mensaje
            // adicional.
            throw new Exception("Ocurrió un error inesperado. " + e.getMessage());
        }
    }

    /**
     * Servicio para guardar un nuevo usuario basado en los datos proporcionados en
     * un CreateUserDto.
     * Este servicio utiliza varios componentes de utilidad para crear el usuario,
     * extraer roles, asignar roles al usuario y manejar la imagen de perfil del
     * usuario.
     *
     * @param theUserDto El objeto CreateUserDto que contiene los datos del usuario
     *                   a crear.
     * @return El usuario guardado en la base de datos después de completar las
     *         operaciones.
     */
    @Override
    @Transactional
    public User save(CreateUserDto theUserDto) throws CustomAssassinException, Exception {
        try {
            // Crear un nuevo usuario a partir de los datos del CreateUserDto.
            User newUser = userUtil.createUserFromDto(theUserDto);

            // Extraer roles del CreateUserDto y asignarlos al usuario.
            List<Role> roles = roleUtil.extractRolesFromDto(theUserDto);
            newUser.setRoles(roles); // Asignar los roles al usuario.

            // Manejar la imagen de perfil del usuario utilizando UploadFileUtil y obtener
            // la ruta de la imagen.
            String profileImagePath = uploadFileUtil.handleProfileImage(theUserDto.profileImage(), newUser);
            newUser.setProfileImage(profileImagePath); // Asignar la ruta de la imagen al usuario.

            // Guardar el nuevo usuario en la base de datos.
            User savedUser = userRepository.save(newUser);

            // Devuelve al usuario registrado.
            return savedUser;
        } catch (CustomAssassinException e) {
            // Si se lanza una CustomAssassinException, la vuelve a lanzar sin modificarla.
            throw e;
        } catch (Exception e) {
            // Si ocurre cualquier otra excepción, lanza una nueva excepción con un mensaje
            // adicional.
            throw new Exception("Ocurrió un error inesperado. " + e.getMessage());
        }
    }

}
