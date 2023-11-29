package com.example.myapplication.controller;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class MinhaException extends Exception {

    public FirebaseAuthWeakPasswordException erroSenha;
    public FirebaseAuthUserCollisionException erroExiste;
    public FirebaseAuthInvalidCredentialsException erroEmail;
    public MinhaException(FirebaseAuthWeakPasswordException erroSenha,FirebaseAuthUserCollisionException erroExiste,FirebaseAuthInvalidCredentialsException erroEmail){
        this.erroSenha = erroSenha;
        this.erroEmail = erroEmail;
        this.erroExiste = erroExiste;
    }

    public String erroSenha(){

        return "penis";
    }
}
