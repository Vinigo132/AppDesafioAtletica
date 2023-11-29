package com.example.myapplication.controller;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UsuarioDAO implements IUsuario{


    private String usuarioId;

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
                                salvarDadosUsuario(nome);
                                listener.onLoginResult(true, null);
                            } else {
                                String errorMessage = task.getException().getMessage();
                                listener.onLoginResult(false, errorMessage);
                            }
                        }
                    });
        }


    }

    public void salvarDadosUsuario(String nome){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> usuarios = new HashMap<>();
        usuarios.put("nome",nome);

        usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioId);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("DB", "sucesso ao salvar user");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("DB", "erro ao salvar user" + e.toString());
                    }
                });
    }

    @Override
    public boolean AlterarPerfil(Usuario user) {
        return false;
    }


}
