package com.example.myapplication.model;

public abstract class Card {

    private String descricao;
    private String id;
    private String img;

    public Card() {
    }

    public Card(String descricao, String id, String img) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}