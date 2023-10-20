package com.example.myapplication.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.Produto;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LojaActivity extends AppCompatActivity {

    BottomNavigationView navbarMenu;
    private LinearLayout produtoContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loja);


        navbarMenu = findViewById(R.id.navbarMenu);
        navbarMenu.setSelectedItemId(R.id.loja);

        navbarMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                    overridePendingTransition(0, 0);
                }
                if (item.getItemId() == R.id.loja) {
                   return true;
                }
                return false;
            }
        });
        mostrarContainer();
    }

    private void addProdutoLoja(LinearLayout produtoContainer, final Produto produto) {
        // Inflar o layout de item de produto
        View lojaView = LayoutInflater.from(this).inflate(R.layout.activity_loja, null);

        // Obtenha as visualizações do layout de item de produto
        ImageView produtoImagem = lojaView.findViewById(R.id.prod1);


        // Configure as visualizações com os dados do produto
        produto.setIdImagem(String.valueOf(produtoImagem));

        // Defina um clique para abrir a aba de compra
        produtoImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCarrinho(produto);
            }
        });

        // Adicione o item de produto ao contêiner
        produtoContainer.addView(lojaView);
    }

    private void abrirCarrinho(Produto produto){
        showToast("Sucesso!");

    }
    private void mostrarContainer(){

        Produto produto1 = new Produto(1,"Kit 1","Camiseta preta e Short laranja",55.00,"prod1.jpg");
        Produto produto2 = new Produto(2,"Kit 2","Camiseta laranja e Short preto",55.00,"prod2.jpg");
        Produto produto3 = new Produto(1,"Caneca","Caneca com tirante",30.00,"prod3.jpg");

        produtoContainer = findViewById(R.id.produtoContainer);

        addProdutoLoja(produtoContainer, produto1);
        addProdutoLoja(produtoContainer, produto2);
        addProdutoLoja(produtoContainer, produto3);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}