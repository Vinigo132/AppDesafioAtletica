package com.example.myapplication.controller;

import com.example.myapplication.model.Usuario;

public interface IUsuario {

    public abstract boolean fazerLogin(String email, String senha);

    public abstract boolean Cadastrar( String Nome,String Email, String Senha, String ConferirSenha);
}
