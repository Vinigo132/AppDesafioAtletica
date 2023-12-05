package com.example.myapplication.model;

public class CardEvento extends Card{
    private String contato;
    public CardEvento(){}

    public CardEvento(String descricao, String id, String img, String contato) {
        super(descricao, id, img);
        this.contato = contato;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }
}