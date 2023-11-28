package com.example.myapplication.controller;

import androidx.annotation.NonNull;

import com.example.myapplication.model.AdmAtletica;
import com.example.myapplication.model.MembroAtletica;
import com.example.myapplication.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class UsuarioDAO implements IUsuario{
    private List<Usuario> usuarios;

    public UsuarioDAO() {

    }

    public boolean fazerLogin(String email, String senha) {
        final boolean[] login = {false};
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener((Executor) this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            login[0] = true;
                        } else {
                            login[0] = false;
                        }
                    }
                });

        return login[0];
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
