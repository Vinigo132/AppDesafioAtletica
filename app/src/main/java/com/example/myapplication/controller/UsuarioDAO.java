package com.example.myapplication.controller;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.model.MembroAtletica;
import com.example.myapplication.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class UsuarioDAO implements IUsuario{


    private String usuarioId;
    private String nomeUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void fazerLogin(String email, String senha, final IOnLoginCompleteListener listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            listener.onLoginResult(true, null);
                        } else {

                            listener.onLoginResult(false, "Falha ao fazer login: " +
                                    "verifique sua senha e email ou se tem cadastro!");
                        }
                    }
                });
    }
    @Override
    public void onLoginResult(boolean success, String errorMessage) {
    }


    public void Cadastrar(MembroAtletica membro, String conferirSenha, final IOnLoginCompleteListener listener) {
        if (membro.getSenha().equals(conferirSenha)) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(membro.getEmail(), membro.getSenha())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                salvarDadosUsuario(membro.getNome());
                                listener.onLoginResult(true, null);
                            } else {
                                String erro;
                                try {
                                    throw task.getException();

                                }catch(FirebaseAuthWeakPasswordException e){

                                    erro = "A senha deve conter no mínimo 6 caracteres!";
                                }catch (FirebaseAuthUserCollisionException e){

                                    erro = "Esta conta já foi cadastrada!";
                                }catch (FirebaseAuthInvalidCredentialsException e){

                                    erro = "Email inválido!";
                                }
                                catch (Exception e) {
                                    erro = "Erro ao cadastrar usuário!";
                                }
                                listener.onLoginResult(false, erro);
                            }
                        }
                    });
        }else{
            listener.onLoginResult(false,"As senhas não conferem!");
        }


    }

    public void recuperarNome(NomeCallback callback){

         usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();
         DocumentReference documentReference = db.collection("Usuarios").document(usuarioId);
        final String[] nome = new String[1];
         documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
             @Override
             public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                 if(value != null){
                     callback.onNomeRecuperado(value.getString("nome"));
                 }
             }
         });
    }

    public interface NomeCallback {
        void onNomeRecuperado(String nome);
    }
    public void salvarDadosUsuario(String nome){

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
