package com.example.myapplication.controller;

import com.example.myapplication.model.AdmAtletica;
import com.example.myapplication.model.MembroAtletica;
import com.example.myapplication.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements IUsuario{
    private List<Usuario> usuarios;

    public UsuarioDAO() {
        usuarios = new ArrayList<>();

        usuarios.add(new MembroAtletica("fulano","teste@ex.com", "123", "Jogador de xadrez"));
        usuarios.add(new MembroAtletica("ciclano","usuario2@example.com", "senha456", "Jogador de futebol"));
        usuarios.add(new AdmAtletica("teste","usuario3@example.com", "senha789", "Presidente"));
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
            usuarios.add(new MembroAtletica(nome,email,senha));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean AlterarPerfil(Usuario user) {
        return false;
    }
}
