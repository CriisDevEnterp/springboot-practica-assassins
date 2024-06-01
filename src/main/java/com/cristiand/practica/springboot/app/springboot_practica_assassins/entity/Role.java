package com.cristiand.practica.springboot.app.springboot_practica_assassins.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

    // -----------------------
    // TODO: Definir campos
    // -----------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="name", unique = true)
    private String name;
    
    // ------------------
    // TODO: Definir FK
    // ------------------
    @JsonIgnoreProperties({"roles", "handler", "hibernateLazyInitializer"}) // Ignorar la propiedad 'roles' durante la serialización/deserialización
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    // -----------------------------
    // TODO: Definir constructores
    // -----------------------------
    public Role() {}

    public Role(String name) {
        this.name = name;
    }
    
    public Role(String name, List<User> users) {
        this.name = name;
        this.users = users;
    }

    // ---------------------------------
    // TODO: Definir getters & setters
    // ---------------------------------
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    
    // -------------------------
    // TODO: Definir toString
    // -------------------------
    @Override
    public String toString() {
        return "Role [id=" + id + ", name=" + name + ", users=" + users + "]";
    }
    
}
