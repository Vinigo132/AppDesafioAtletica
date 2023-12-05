package com.example.myapplication.model;

public class CardCuponsParcerias extends Card{

    private String endereco;
    private String contato;

    public CardCuponsParcerias(){}

    public CardCuponsParcerias(String descricao, int id, String img, String endereco, String contato) {

        this.endereco = endereco;
        this.contato = contato;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }
}
