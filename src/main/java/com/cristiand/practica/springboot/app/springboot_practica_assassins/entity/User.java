package com.cristiand.practica.springboot.app.springboot_practica_assassins.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    // -----------------------
    // TODO: Definir campos
    // -----------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="username", unique = true)
    private String username;

    @Column(name="password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name="enabled")
    private Boolean enabled;

    // ------------------
    // TODO: Definir FK
    // ------------------
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "role_id"}) }
    )
    @JsonIgnoreProperties({"users", "handler", "hibernateLazyInitializer"}) // Ignorar la propiedad 'users' durante la serialización/deserialización
    private List<Role> roles;
    
    // -----------------------------
    // TODO: Definir constructores
    // -----------------------------
    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
    
    public User(String username, String password, boolean enabled, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    // -------------------------
    // TODO: Definir toString
    // -------------------------
    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", enabled=" + enabled
                + ", roles=" + roles + "]";
    }
    
}
