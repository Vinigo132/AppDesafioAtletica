package com.example.myapplication.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.ProdutosAdapter;
import com.example.myapplication.model.CardKits;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LojaActivity extends AppCompatActivity {

    private RecyclerView recyclerProdutos;
    private ProdutosAdapter produtosAdapter;
    private Dialog popUp;
    BottomNavigationView navbarMenu;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loja);

        initializeViews();

        CardKits teste = new CardKits();
        navbarMenu = findViewById(R.id.navbarMenu);
        navbarMenu.setSelectedItemId(R.id.loja);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerProdutos = findViewById(R.id.recyclerProdutos);
        recyclerProdutos.setLayoutManager(linearLayoutManager);
        //produtosAdapter = new ProdutosAdapter(listaProdutos);
        recyclerProdutos.setAdapter(produtosAdapter);
        Button create = findViewById(R.id.createbtn);





    //    prod.setOnClickListener(new View.OnClickListener() {
     //       @Override
     //       public void onClick(View view) {
//                dialog.show(getSupportFragmentManager(),"Comprar Produto");
      //      }
      //  });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp.show();
            }
        });

        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LojaActivity.class));
            }
        });


        navbarMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                    overridePendingTransition(0, 0);
                }
                if (item.getItemId() == R.id.sobre) {
                    startActivity(new Intent(getApplicationContext(), SobreActivity.class));
                    overridePendingTransition(0, 0);
                }
                if (item.getItemId() == R.id.loja) {
                   return true;
                }
                return false;
            }
        });
    }

    @SuppressLint("ResourceType")
    private void initializeViews() {

        // ----------------- popUp --------------
        popUp = new Dialog(this);
        popUp.setContentView(R.layout.activity_add_page);
        popUp.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        popUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        ImageButton fechar = popUp.findViewById(R.id.closebtn);
    }

}