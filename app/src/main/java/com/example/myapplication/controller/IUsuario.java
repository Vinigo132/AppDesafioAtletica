package com.example.myapplication.controller;

import com.example.myapplication.model.Usuario;

public interface IUsuario {

    public abstract void fazerLogin(String email, String senha, final OnLoginCompleteListener listener);
    public abstract boolean Cadastrar( String Nome,String Email, String Senha, String ConferirSenha);
    public abstract boolean AlterarPerfil(Usuario user);

}
