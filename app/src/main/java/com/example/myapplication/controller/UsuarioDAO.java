package com.example.myapplication.controller;

import androidx.annotation.NonNull;

import com.example.myapplication.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UsuarioDAO implements IUsuario{




    public UsuarioDAO() {

    }
    public void fazerLogin(String email, String senha, final IOnLoginCompleteListener listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
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


    public void Cadastrar(String nome, String email, String senha, String conferirSenha,final IOnLoginCompleteListener listener) {
        if (senha.equals(conferirSenha)) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
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


    }

    @Override
    public boolean AlterarPerfil(Usuario user) {
        return false;
    }


}
