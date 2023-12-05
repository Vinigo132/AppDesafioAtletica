package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CardKits extends Card implements Parcelable {
    private double valor;
    private String tamanho;
    private String nome;
    private int quantidade;

    public CardKits(){}
    public CardKits(String descricao, String id, String img, double valor, String tamanho,String nome, int quantidade) {
        super(descricao, id, img);
        this.valor = valor;
        this.tamanho = tamanho;
        this.nome = nome;
        this.quantidade = quantidade;
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

    public String getNome() {return nome;}

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {return quantidade;}

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {

    }
}
