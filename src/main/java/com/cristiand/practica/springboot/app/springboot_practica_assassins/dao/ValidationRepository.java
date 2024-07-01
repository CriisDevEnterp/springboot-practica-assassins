package com.cristiand.practica.springboot.app.springboot_practica_assassins.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repositorio para validaciones.
 * Proporciona métodos para realizar consultas personalizadas en la base de
 * datos.
 *
 * @param <T>  la entidad que maneja este repositorio.
 * @param <ID> el tipo de la clave primaria de la entidad.
 */
@NoRepositoryBean
public interface ValidationRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    /**
     * Verifica si existe una entidad con un nombre de usuario dado.
     * Esta operación es de solo lectura y está envuelta en una transacción.
     *
     * @param username el nombre de usuario a verificar.
     * @return true si existe una entidad con el nombre de usuario dado, false en
     *         caso contrario.
     */
    @Transactional(readOnly = true)
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.username = :username")
    boolean existsByUsername(@Param("username") String username);

    /**
     * Verifica si existe una entidad con un correo dado.
     * Esta operación es de solo lectura y está envuelta en una transacción.
     *
     * @param email el correo a verificar.
     * @return true si existe una entidad con el correo dado, false en caso
     *         contrario.
     */
    @Transactional(readOnly = true)
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

}
