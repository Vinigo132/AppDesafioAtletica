package com.example.myapplication.controller;

import com.example.myapplication.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements IUsuario{
    private List<Usuario> usuarios;

    public UsuarioDAO() {
        usuarios = new ArrayList<>();

        usuarios.add(new Usuario("fulano","teste@ex.com", "123"));
        usuarios.add(new Usuario("ciclano","usuario2@example.com", "senha456"));
        usuarios.add(new Usuario("teste","usuario3@example.com", "senha789"));
    }

    public boolean fazerLogin(String email, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean Cadastrar(String nome, String email, String senha, String conferirSenha) {
        if (senha.equals(conferirSenha)) {
            usuarios.add(new Usuario(nome,email,senha));
            return true;
        } else {
            return false;
        }
    }
}
