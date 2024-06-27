package com.cristiand.practica.springboot.app.springboot_practica_assassins.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * Clase que representa la entidad de un rol en la base de datos.
 * Esta clase está mapeada a la tabla "roles" en la base de datos.
 * Implementa Serializable para permitir que sus instancias sean serializadas.
 */
@Entity
@Table(name = "roles")
public class Role implements Serializable {

    /**
     * Identificador de versión para la serialización de la clase.
     * Esto asegura que la clase sea compatible durante el proceso de serialización
     * y deserialización.
     */
    private static final long serialVersionUID = 1L;

    /**
     * -----------------------
     * Campos
     * -----------------------
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * -----------------------
     * Relaciones
     * -----------------------
     */
    @JsonIgnoreProperties("roles")
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;

    /**
     * -----------------------
     * Constructores
     * -----------------------
     */
    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(String name, List<User> users) {
        this.name = name;
        this.users = users;
    }

    /**
     * -----------------------
     * Getters & Setters
     * -----------------------
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * -----------------------
     * toString()
     * -----------------------
     */
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
