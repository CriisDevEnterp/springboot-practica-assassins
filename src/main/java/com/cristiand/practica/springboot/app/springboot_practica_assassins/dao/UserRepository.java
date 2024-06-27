package com.cristiand.practica.springboot.app.springboot_practica_assassins.dao;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repositorio para la entidad {@link User}.
 * Extiende de {@link ValidationRepository} que contiene validaciones.
 * Proporciona métodos para realizar operaciones CRUD y consultas personalizadas
 * sobre los usuarios en la base de datos.
 */
@Repository
public interface UserRepository extends ValidationRepository<User, Long> {

    /**
     * Busca un usuario por su nombre de usuario, cargando también sus roles
     * asociados.
     * Esta operación es de solo lectura y está envuelta en una transacción.
     *
     * @param username el nombre de usuario del usuario a buscar.
     * @return el usuario encontrado con sus roles asociados, o null si no se
     *         encuentra.
     * @throws DataAccessException si ocurre un error en el acceso a datos.
     */
    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = :username")
    public User findByUsernameWithRoles(@Param("username") String username) throws DataAccessException;

    /**
     * Obtiene una lista de todos los usuarios, cargando también sus roles
     * asociados.
     * Esta operación es de solo lectura y está envuelta en una transacción.
     *
     * @return una lista de todos los usuarios con sus roles asociados.
     * @throws DataAccessException si ocurre un error en el acceso a datos.
     */
    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles")
    public List<User> findAllWithRoles() throws DataAccessException;

    /**
     * Obtiene una página de usuarios, cargando también sus roles asociados.
     * Esta operación es de solo lectura y está envuelta en una transacción.
     *
     * @param pageable información de paginación.
     * @return una página de usuarios con sus roles asociados.
     * @throws DataAccessException si ocurre un error en el acceso a datos.
     */
    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles")
    public Page<User> findAllWithRoles(Pageable pageable) throws DataAccessException;

    /**
     * Busca usuarios cuyo nombre de usuario contenga una cadena dada, cargando
     * también sus roles asociados.
     * Esta operación es de solo lectura y está envuelta en una transacción.
     *
     * @param username la cadena que debe contener el nombre de usuario de los
     *                 usuarios a buscar.
     * @return una lista de usuarios cuyo nombre de usuario contiene la cadena dada,
     *         con sus roles asociados.
     * @throws DataAccessException si ocurre un error en el acceso a datos.
     */
    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username LIKE %:username%")
    public List<User> findByUsernameContainingWithRoles(@Param("username") String username) throws DataAccessException;

    /**
     * Busca un usuario por su ID, cargando también sus roles asociados.
     * Esta operación es de solo lectura y está envuelta en una transacción.
     *
     * @param id el ID del usuario a buscar.
     * @return el usuario encontrado con sus roles asociados, o null si no se
     *         encuentra.
     * @throws DataAccessException si ocurre un error en el acceso a datos.
     */
    @Transactional(readOnly = true)
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :id")
    public Optional<User> findByIdWithRoles(@Param("id") Long id) throws DataAccessException;

}
