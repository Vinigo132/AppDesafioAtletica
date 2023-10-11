package com.example.myapplication.controller;

import com.example.myapplication.model.Usuario;

public class UsuarioDAO implements IUsuario{

    @Override
    public boolean autenticarUsuario(Usuario u) {
        return false;
    }

    // implementar dps no main activity
}
