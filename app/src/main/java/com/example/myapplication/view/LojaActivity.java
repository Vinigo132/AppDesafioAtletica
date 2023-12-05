package com.example.myapplication.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.CuponsParceriasAdapter;
import com.example.myapplication.controller.EventosAdapter;
import com.example.myapplication.model.CardCuponsParcerias;
import com.example.myapplication.model.CardEvento;
import com.example.myapplication.model.CardNoticias;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class LojaActivity extends AppCompatActivity {

    BottomNavigationView navbarMenu;
    private FirebaseFirestore db;
    private CollectionReference colecaoCupomParceria;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerCupomParceria;
    private List<CardCuponsParcerias> listaCupomParceria;
    private CuponsParceriasAdapter cupomParceriaAdapter;
    private EventListener<QuerySnapshot> eventListenerCupomParceria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loja);


        navbarMenu = findViewById(R.id.navbarMenu);
        navbarMenu.setSelectedItemId(R.id.loja);

        db = FirebaseFirestore.getInstance();
        colecaoCupomParceria = db.collection("cupons_parcerias");

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerCupomParceria = findViewById(R.id.recyclerCuponsParcerias);
        recyclerCupomParceria.setLayoutManager(linearLayoutManager);
        listaCupomParceria = new ArrayList<>();
        cupomParceriaAdapter = new CuponsParceriasAdapter(listaCupomParceria);
        recyclerCupomParceria.setAdapter(cupomParceriaAdapter);

        exibirCuponsParcerias();


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

    public void adicionarCupomParceria(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
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

                    CardCuponsParcerias cardCupomParceria = new CardCuponsParcerias();
                    cardCupomParceria.setImg(fotoEmString);
                    cardCupomParceria.setDescricao("cupom");
                    if (cupomParceriaAdapter.adicionarItem(cardCupomParceria)) {
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




    public void exibirCuponsParcerias(){
        // Configurar o SnapshotListener
        eventListenerCupomParceria = new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("Firestore", "Erro ao ouvir as alterações", e);
                    return;
                }

                listaCupomParceria.clear();
                for (DocumentSnapshot document : querySnapshot) {
                    CardCuponsParcerias cardCupomParceria = new CardCuponsParcerias();
                    cardCupomParceria.setId((document.getId()));
                    cardCupomParceria.setDescricao(document.getString("descricao"));
                    cardCupomParceria.setImg(document.getString("imagem"));


                    listaCupomParceria.add(cardCupomParceria);
                }
                cupomParceriaAdapter.notifyDataSetChanged();
            }
        };
    }
    @Override
    protected void onStart() {
        super.onStart();
        colecaoCupomParceria.addSnapshotListener(eventListenerCupomParceria);

    }
}