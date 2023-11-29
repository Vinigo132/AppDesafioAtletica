package com.example.myapplication.controller;

import android.app.Activity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.model.Usuario;

public interface IUsuario {

    public abstract boolean fazerLogin(String email, String senha,  Activity activity);
    public abstract boolean Cadastrar( String Nome,String Email, String Senha, String ConferirSenha);
    public abstract boolean AlterarPerfil(Usuario user);

    public interface OnLoginCompleteListener {
        public void onLoginResult(boolean success, String errorMessage);
    }

}
