package com.example.myapplication.model;

public class AdmAtletica extends Usuario{
    private String cargo;


    public AdmAtletica(String nome, String email, String senha,String cargo){
        super(nome, email, senha);
        this.cargo = cargo;
    }

    public boolean postarProduto(){
        return false;
    };

    public boolean postarEvento(){
        return false;
    };

    public boolean postarNoticiaAtletica(){
        return false;
    };

    public boolean gerenciarMembros(){
        return false;
    };

}
