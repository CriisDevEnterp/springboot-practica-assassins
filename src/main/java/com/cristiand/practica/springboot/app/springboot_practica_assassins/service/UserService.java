package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.CreateUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.FindUserDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain.CustomAssassinException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interfaz que define operaciones relacionadas con usuarios.
 * Estos métodos se implementan para gestionar la recuperación y manipulación de
 * datos de usuarios, manejando excepciones específicas.
 */
public interface UserService {

    /**
     * Retorna una lista de todos los usuarios junto con sus roles desde el
     * repositorio.
     *
     * @return Lista de usuarios encontrados
     * @throws CustomAssassinException si no se encuentran usuarios (lanzada como
     *                                 excepción personalizada).
     * @throws Exception               para errores inesperados durante la
     *                                 recuperación de usuarios.
     */
    List<User> findAll() throws CustomAssassinException, Exception;

    /**
     * Retorna una página de usuarios junto con sus roles desde el repositorio,
     * utizando paginación.
     *
     * @param pageable Información de paginación y ordenamiento para la consulta.
     * @return La página de usuarios encontrados.
     * @throws CustomAssassinException si la página de usuarios está vacía.
     * @throws Exception               si ocurre un error inesperado durante la
     *                                 búsqueda de usuarios paginados.
     */
    Page<User> findAll(Pageable pageable) throws CustomAssassinException, Exception;

    /**
     * Retorna al usuario buscado por su ID.
     *
     * @param id ID del usuario a buscar.
     * @return el usuario encontrado.
     * @throws CustomAssassinException si no se encuentra el usuario con el ID
     *                                 especificado.
     * @throws Exception               si ocurre un error inesperado durante la
     *                                 búsqueda del usuario.
     */
    User findById(Long id) throws CustomAssassinException, Exception;

    /**
     * Realiza una búsqueda filtrada de usuarios basada en los criterios
     * especificados en {@link FindUserDto}. Si {@code findUserDto} es nulo o su
     * nombre de usuario está vacío, se retornan todos los usuarios con sus roles.
     * Si se encuentra algún usuario que coincida con el nombre de usuario
     * especificado en {@code findUserDto}, se retorna una lista de usuarios que
     * contienen dicho nombre de usuario en sus nombres.
     * 
     * @param findUserDto objeto que contiene los criterios de búsqueda de usuarios.
     * @return una lista de usuarios que coinciden con los criterios de búsqueda.
     * @throws CustomAssassinException si no se encuentran usuarios que coincidan
     *                                 con el nombre de usuario especificado.
     * @throws Exception               Si ocurre un error inesperado durante la
     *                                 búsqueda filtrada.
     */
    List<User> performFilteredSearch(FindUserDto findUserDto) throws CustomAssassinException, Exception;

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
     * @throws CustomAssassinException Si ocurre un error específico manejado por la
     *                                 aplicación durante el proceso.
     * @throws Exception               Si ocurre cualquier otro error inesperado
     *                                 durante la ejecución del servicio.
     */
    User save(CreateUserDto theUserDto) throws CustomAssassinException, Exception;

}
