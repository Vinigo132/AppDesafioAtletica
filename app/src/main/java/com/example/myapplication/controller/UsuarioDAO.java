package com.example.myapplication.controller;

import com.example.myapplication.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements IUsuario{
    private List<Usuario> usuarios;

    public UsuarioDAO() {
        usuarios = new ArrayList<>();

        usuarios.add(new Usuario("teste@ex.com", "123"));
        usuarios.add(new Usuario("usuario2@example.com", "senha456"));
        usuarios.add(new Usuario("usuario3@example.com", "senha789"));
    }

    public boolean fazerLogin(String email, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }
}
