package com.cristiand.practica.springboot.app.springboot_practica_assassins.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    // -----------------------------
    // TODO: Definir constructores
    // -----------------------------
    public Role() {}

    public Role(String name) {
        this.name = name;
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
    
    // -------------------------
    // TODO: Definir toString
    // -------------------------
    @Override
    public String toString() {
        return "Role [id=" + id + ", name=" + name + "]";
    }
    
}
