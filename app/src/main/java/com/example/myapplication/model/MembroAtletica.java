package com.example.myapplication.model;

public class MembroAtletica extends Usuario{
    private String modalidade;

    public MembroAtletica(){};

    public MembroAtletica(String nome, String email, String senha){
        super(nome, email, senha);
    }
    public MembroAtletica(String nome, String email, String senha,String modalidade){
        super(nome, email, senha);
        this.modalidade = modalidade;
    }
    public void publicarNoticia(){};
    public void comprarKit(){};
}