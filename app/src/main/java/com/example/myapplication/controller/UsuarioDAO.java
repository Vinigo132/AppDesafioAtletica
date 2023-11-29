package com.example.myapplication.controller;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.MainActivity;
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


    public boolean fazerLogin(String email, String senha, Activity activity) {
        final boolean[] login = new boolean[1];
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(activity, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            login[0] = true;
                            Toast.makeText(activity, "Login bem-sucedido", Toast.LENGTH_SHORT).show();
                        } else {
                            login[0] = false;
                            Toast.makeText(activity, "Falha no login: " +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return login[0];

    }


    @Override
    public boolean Cadastrar(String nome, String email, String senha, String conferirSenha) {
        if (senha.equals(conferirSenha)) {
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
