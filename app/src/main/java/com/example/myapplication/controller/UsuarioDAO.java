package com.example.myapplication.controller;

import androidx.annotation.NonNull;

import com.example.myapplication.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

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

                            listener.onLoginResult(false, "Falha ao fazer login: " +
                                    "verifique sua senha e email ou se tem cadastro!");
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

    @Override
    public boolean AlterarPerfil(Usuario user) {
        return false;
    }


}
