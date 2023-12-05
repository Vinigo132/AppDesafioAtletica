package com.example.myapplication.model;

public class CardNoticias extends Card{
    private String Curso;
    private String Autor;

    public String getTitulo() {
        return Curso;
    }
    public CardNoticias(){}

    public CardNoticias(String descricao, String id, String img, String curso, String autor) {
        super(descricao, id, img);
        Curso = curso;
        Autor = autor;
    }

    public void setCurso(String titulo) {
        Curso = titulo;
    }
    public String getCurso(){
        return this.Curso;
    }

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String autor) {
        Autor = autor;
    }
}