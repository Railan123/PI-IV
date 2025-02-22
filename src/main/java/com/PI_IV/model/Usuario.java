package com.pi_iv.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario {

    @Id
    private String cpf;  // CPF será a chave primária, já que é único

    private String nome;
    private String email;
    private String grupo;
    private String senha;
    private boolean status;  // Agora o status é um booleano

    // Construtor
    public Usuario(String cpf, String nome, String email, String grupo, String senha, boolean status) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.grupo = grupo;
        this.senha = senha;
        this.status = status;
    }

    // Getters e Setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", grupo='" + grupo + '\'' +
                ", status=" + status +
                '}';
    }
}
