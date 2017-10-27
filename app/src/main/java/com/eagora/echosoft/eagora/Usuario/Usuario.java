package com.eagora.echosoft.eagora.Usuario;

/**
 * Created by dhanielmazini on 20/10/17.
 */

public class Usuario {

    public String nome;
    private String sobrenome;
    public String email;
    private String uid;
    private String senha;

    public Usuario() {

    }

    public Usuario(String nome, String sobrenome, String email, String uid, String senha) {
        this.nome = nome;
        this.email = email;
        this.sobrenome = sobrenome;
        this.uid = uid;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getUid() {
        return uid;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
