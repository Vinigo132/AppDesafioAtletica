package com.example.myapplication.controller;

import com.example.myapplication.model.Card;

public interface ICard {

    public void inserir(String descricao, int id, String img);
    public void deletar(Card card);
    public void alterar(Card card);
}
