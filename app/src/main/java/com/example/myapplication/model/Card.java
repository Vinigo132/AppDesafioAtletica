package com.example.myapplication.model;

public abstract class Card {

    private String nome;
    private String descricao;
    private int id;
    private String img;

    public Card(String descricao, String id, String img) {
    }

    public Card(String nome, String descricao, int id, String img) {
        this.nome = nome;
        this.descricao = descricao;
        this.id = id;
        this.img = img;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
