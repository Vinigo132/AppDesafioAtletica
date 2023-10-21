package com.example.myapplication.model;

public class CardNoticias extends Card{
    private String Titulo;
    private String Autor;

    public String getTitulo() {
        return Titulo;
    }

    public CardNoticias(String descricao, int id, String img, String titulo, String autor) {
        super(descricao, id, img);
        Titulo = titulo;
        Autor = autor;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String autor) {
        Autor = autor;
    }
}
