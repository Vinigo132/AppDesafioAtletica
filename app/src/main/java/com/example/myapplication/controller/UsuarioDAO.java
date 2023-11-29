package com.example.myapplication.controller;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.MainActivity;
import com.example.myapplication.model.AdmAtletica;
import com.example.myapplication.model.MembroAtletica;
import com.example.myapplication.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements IUsuario, OnLoginCompleteListener{
    private FirebaseAuth mAuth;

    public UsuarioDAO() {

        mAuth = FirebaseAuth.getInstance();
    }

    public void fazerLogin(String email, String senha, final OnLoginCompleteListener listener) {
        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            listener.onLoginResult(true, null);
                        } else {
                            String errorMessage = task.getException().getMessage();
                            listener.onLoginResult(false, errorMessage);
                        }
                    }
                });
    }
    @Override
    public void onLoginResult(boolean success, String errorMessage) {
    }

    @Override
    public boolean Cadastrar(String nome, String email, String senha, String conferirSenha) {
     return true;

    }

    @Override
    public boolean AlterarPerfil(Usuario user) {
        return false;
    }
}
