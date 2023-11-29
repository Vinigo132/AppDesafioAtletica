package com.example.myapplication.controller;

import android.app.Activity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.model.Usuario;

public interface IUsuario {

    public abstract void fazerLogin(String email, String senha, final IOnLoginCompleteListener listener);

    void onLoginResult(boolean success, String errorMessage);

    public abstract void Cadastrar(String Nome, String Email, String Senha, String ConferirSenha, final IOnLoginCompleteListener listener);
    public abstract boolean AlterarPerfil(Usuario user);

    public void salvarDadosUsuario(String nome);


}
