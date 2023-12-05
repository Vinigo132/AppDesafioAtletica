package com.example.myapplication.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LojaActivity extends AppCompatActivity {

    private Dialog popUp;
    private RecyclerView recyclerProdutos;
    private ProdutosAdapter produtosAdapter;
    private CollectionReference colecaoProdutos;
    private List<CardKits> listaProdutos;
    private LinearLayoutManager linearLayoutManager;
    private EventListener<QuerySnapshot> eventListenerProdutos;
    BottomNavigationView navbarMenu;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loja);



        navbarMenu = findViewById(R.id.navbarMenu);
        navbarMenu.setSelectedItemId(R.id.loja);

        colecaoProdutos = db.collection("Produtos");
        listaProdutos = new ArrayList<>();
        produtosAdapter = new ProdutosAdapter(listaProdutos);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerProdutos = findViewById(R.id.recyclerProdutos);
        recyclerProdutos.setLayoutManager(linearLayoutManager);
        recyclerProdutos.setAdapter(produtosAdapter);

        Button create = findViewById(R.id.createbtn);

        exibirImagem();

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




        // ----------------- popUp --------------
        popUp = new Dialog(this);
        popUp.setContentView(R.layout.activity_add_page);
        popUp.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        popUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        ImageButton fechar = popUp.findViewById(R.id.closebtn);

        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp.dismiss();
            }
        });

        Button adicionar = popUp.findViewById(R.id.createbtn);

        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            popUp.dismiss();
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

    public void adicionarImagem(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    public void exibirImagem(){
        // Configurar o SnapshotListener
        eventListenerProdutos = new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("Firestore", "Erro ao ouvir as alterações", e);
                    return;
                }

                listaProdutos.clear();
                for (DocumentSnapshot document : querySnapshot) {
                    CardKits produto = new CardKits();
                    produto.setId((document.getId()));
                    produto.setDescricao(document.getString("descricao"));
                    produto.setImg(document.getString("imagem"));

                    listaProdutos.add(produto);
                }
                produtosAdapter.notifyDataSetChanged();
            }
        };
    }

    protected void onActivityResult(int ResquestCode, int ResultCode, Intent dados) {
        super.onActivityResult(ResquestCode, ResultCode, dados);
        if (ResquestCode == 2) {
            if(ResultCode == Activity.RESULT_OK) {
                try {
                    Uri imageUri = dados.getData();
                    Bitmap fotoBuscada = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    byte[] fotoEmBytes;
                    ByteArrayOutputStream streamDaFotoEmBytes = new ByteArrayOutputStream();
                    fotoBuscada.compress(Bitmap.CompressFormat.PNG, 70, streamDaFotoEmBytes);
                    fotoEmBytes = streamDaFotoEmBytes.toByteArray();
                    String fotoEmString = android.util.Base64.encodeToString(fotoEmBytes, android.util.Base64.DEFAULT);


                    CardKits produto = new CardKits();
                    EditText nome = popUp.findViewById(R.id.createNome);
                    EditText desc = popUp.findViewById(R.id.createDesc);
                    EditText valor = popUp.findViewById(R.id.createValor);
                    EditText tamanho = popUp.findViewById(R.id.createTamanho);
                    EditText qtd = popUp.findViewById(R.id.createQtd);


                    String nomeS = nome.getText().toString();
                    produto.setNome(nomeS);


                    String descS = desc.getText().toString();
                    produto.setDescricao(descS);

                    String tamanhoS = tamanho.getText().toString();
                    produto.setTamanho(tamanhoS);

                    Double valorD = parseDouble(valor);
                    produto.setValor(valorD);

                    int qtdI = parseInt(qtd);
                    produto.setQuantidade(qtdI);

                    produto.setImg(fotoEmString);
                    produto.setDescricao("produto");


                    if (produtosAdapter.adicionarItem(produto)) {
                        Toast.makeText(getApplicationContext(), "Tarefa adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Erro ao adicionar documento!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){

                }
            }
        }
    }



    private Double parseDouble(EditText editText) {
        try {
            return Double.parseDouble(editText.getText().toString().trim());
        } catch (NumberFormatException e) {
            return 0.0; // ou outro valor padrão, dependendo da sua lógica
        }
    }

    private Integer parseInt(EditText editText) {
        try {
            return Integer.parseInt(editText.getText().toString().trim());
        } catch (NumberFormatException e) {
            return 0; // ou outro valor padrão, dependendo da sua lógica
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        colecaoProdutos.addSnapshotListener(eventListenerProdutos);


    }

}