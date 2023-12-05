package com.example.myapplication.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.CardKits;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddPageActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);


        Button adicionar = findViewById(R.id.createbtn);
        ImageButton fechar = findViewById(R.id.closebtn);

        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LojaActivity.class));
            }
        });

        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText nome = findViewById(R.id.createNome);
                EditText desc = findViewById(R.id.createDesc);
                EditText valor = findViewById(R.id.createValor);
                EditText tamanho = findViewById(R.id.createTamanho);
                EditText qtd = findViewById(R.id.createQtd);

                CardKits kits = new CardKits();
                String nomeS = nome.getText().toString().trim();
                kits.setNome(nomeS);

                String descS = desc.getText().toString().trim();
                kits.setDescricao(descS);

                String tamanhoS = tamanho.getText().toString().trim();
                kits.setTamanho(tamanhoS);

                Double valorD = parseDouble(valor);
                kits.setValor(valorD);

                int qtdI = parseInt(qtd);
                kits.setQuantidade(qtdI);

                if(nome != null || desc != null || valor != null || tamanho != null || qtd != null) {
                    createDados(kits);
                }
            }
        });
    }

    private void createDados(CardKits c){

        String id = UUID.randomUUID().toString();
        c.setId(id);

        Map<String, Object> doc = new HashMap<>();

        doc.put("ID", id);
        doc.put("Nome", c.getNome());
        doc.put("Descricao",c.getDescricao());
        doc.put("Valor", c.getValor());
        doc.put("Tamanho",c.getTamanho());
        doc.put("Quantidade",c.getQuantidade());

        db.collection("Produtos").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(AddPageActivity.this,"Produto Adicionado",Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddPageActivity.this,e.getMessage(),Toast.LENGTH_SHORT);
                    }
                });


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
}