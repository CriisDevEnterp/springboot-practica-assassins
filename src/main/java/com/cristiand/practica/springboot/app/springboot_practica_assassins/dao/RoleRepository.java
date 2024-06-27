package com.cristiand.practica.springboot.app.springboot_practica_assassins.dao;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repositorio para la entidad {@link Role}.
 * Proporciona métodos para realizar operaciones CRUD y consultas personalizadas
 * sobre los roles en la base de datos.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Busca un rol por su nombre.
     * Esta operación es de solo lectura y está envuelta en una transacción.
     *
     * @param name el nombre del rol a buscar.
     * @return el rol encontrado, o null si no se encuentra.
     */
    @Transactional(readOnly = true)
    public Role findByName(String name);

    /**
     * Obtiene una lista de todos los roles, cargando también los usuarios asociados
     * a cada rol.
     * Esta operación es de solo lectura y está envuelta en una transacción.
     *
     * @return una lista de todos los roles con sus usuarios asociados.
     */
    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT r FROM Role r LEFT JOIN FETCH r.users")
    public List<Role> findAllWithUsers();

}
