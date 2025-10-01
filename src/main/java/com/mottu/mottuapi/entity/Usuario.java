package com.mottu.mottuapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario") 
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String username; // usado para login

    @Column(nullable=false)
    private String password; // senha criptografada

    @Column(nullable=false)
    private String role; // ROLE_USER ou ROLE_ADMIN

    // Novos campos
    @Column(nullable=false)
    private String nome;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=true)
    private String telefone;

    // Construtor padrão
    public Usuario() {}

    // Construtor completo
    public Usuario(Long id, String username, String password, String role, String nome, String email, String telefone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
