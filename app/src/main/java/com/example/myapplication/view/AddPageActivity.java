package com.example.myapplication.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.EventosAdapter;
import com.example.myapplication.controller.ProdutosAdapter;
import com.example.myapplication.controller.UsuarioDAO;
import com.example.myapplication.model.CardEvento;
import com.example.myapplication.model.CardKits;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
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

public class AddPageActivity extends AppCompatActivity {

    private RecyclerView recyclerProdutos;
    private ProdutosAdapter produtosAdapter;
    private CollectionReference colecaoProdutos;
    private List<CardKits> listaProdutos;
    private LinearLayoutManager linearLayoutManager;
    private EventListener<QuerySnapshot> eventListenerProdutos;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);


        Button adicionar = findViewById(R.id.createbtn);
        ImageButton fechar = findViewById(R.id.closebtn);

        colecaoProdutos = db.collection("Produtos");
        listaProdutos = new ArrayList<>();
        produtosAdapter = new ProdutosAdapter(listaProdutos);


        exibirImagem();

        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LojaActivity.class));
            }
        });

        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(AddPageActivity.this,"oi",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void adicionarImagem(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
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
        if (ResquestCode == 1) {
            if(ResultCode == Activity.RESULT_OK) {
                try {
                    Log.e("Firestore", "Erro ao ouvir as alterações");
                    Uri imageUri = dados.getData();
                    Bitmap fotoBuscada = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    byte[] fotoEmBytes;
                    ByteArrayOutputStream streamDaFotoEmBytes = new ByteArrayOutputStream();
                    fotoBuscada.compress(Bitmap.CompressFormat.PNG, 70, streamDaFotoEmBytes);
                    fotoEmBytes = streamDaFotoEmBytes.toByteArray();
                    String fotoEmString = android.util.Base64.encodeToString(fotoEmBytes, android.util.Base64.DEFAULT);
                    Log.e("Firestore", "Erro ao ouvir as alterações 2" + fotoEmString);

                    CardKits produto = new CardKits();
                    EditText nome = findViewById(R.id.createNome);
                    EditText desc = findViewById(R.id.createDesc);
                    EditText valor = findViewById(R.id.createValor);
                    EditText tamanho = findViewById(R.id.createTamanho);
                    EditText qtd = findViewById(R.id.createQtd);

                    String nomeS = nome.getText().toString().trim();
                    produto.setNome(nomeS);

                    String descS = desc.getText().toString().trim();
                    produto.setDescricao(descS);

                    String tamanhoS = tamanho.getText().toString().trim();
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