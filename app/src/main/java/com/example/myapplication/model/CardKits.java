package com.example.myapplication.model;

public class CardKits extends Card{
    private double valor;
    private String tamanho;

    public CardKits(String descricao, String id, String img, double valor, String tamanho) {
        super(descricao, id, img);
        this.valor = valor;
        this.tamanho = tamanho;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
}
