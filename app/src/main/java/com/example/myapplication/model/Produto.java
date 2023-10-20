package com.example.myapplication.model;

public class Produto {

    private int id;
    private String nome;
    private String desc;
    private double valor;
    private String idImagem;

    public Produto(int id, String nome, String desc, double valor, String idImagem) {
        this.id = id;
        this.nome = nome;
        this.desc = desc;
        this.valor = valor;
        this.idImagem = idImagem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getIdImagem() {
        return idImagem;
    }

    public void setIdImagem(String idImagem) {
        this.idImagem = idImagem;
    }


}
