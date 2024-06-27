package com.cristiand.practica.springboot.app.springboot_practica_assassins.dao;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repositorio para consultas de seguridad relacionadas con usuarios y roles.
 * Proporciona métodos para realizar operaciones de consulta personalizadas
 * sobre usuarios con roles específicos.
 */
@Repository
public interface TokenRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su nombre de usuario, cargando también los roles
     * asociados.
     * Esta operación es de solo lectura y está envuelta en una transacción.
     *
     * @param username el nombre de usuario del usuario a buscar.
     * @return el usuario encontrado, con los roles cargados, o null si no se
     *         encuentra.
     * @throws DataAccessException si ocurre un problema de acceso a datos durante
     *                             la ejecución de la consulta.
     */
    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = :username")
    public User findByUsernameWithRoles(@Param("username") String username) throws DataAccessException;

}
