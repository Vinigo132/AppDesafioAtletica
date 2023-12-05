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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Base64;




import com.example.myapplication.R;
import com.example.myapplication.controller.EventosAdapter;
import com.example.myapplication.controller.PublicacoesAtleticaAdapter;
import com.example.myapplication.controller.PublicacoesUserAdapter;
import com.example.myapplication.controller.UsuarioDAO;
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
import java.util.Base64;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    BottomNavigationView navbarMenu;
    private RecyclerView recyclerPubliUsers;
    private RecyclerView recyclerEventos;
    private RecyclerView recyclerPubliAtletica;
    private FirebaseFirestore db;
    private PublicacoesUserAdapter publiUserAdapter;
    private PublicacoesAtleticaAdapter publiAtleticaAdapter;
    private EventosAdapter eventosAdapter;
    private CollectionReference colecaoPubliUsers;
    private CollectionReference colecaoPubliAtletica;
    private CollectionReference colecaoEventos;
    private List<CardNoticias> listaPublicacoesUser;
    private List<CardNoticias> listaPublicacoesAtletica;
    private List<CardEvento> listaEventos;
    private TextView txtAdicionarPublicacao;
    private TextView txtAdicionarPubliAtletica;
    private LinearLayoutManager linearLayoutManager;
    private UsuarioDAO usuarioDAO;
    private EventListener<QuerySnapshot> eventListener;
    private EventListener<QuerySnapshot> eventListenerEventos;
    private EventListener<QuerySnapshot> eventListenerAtletica;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        usuarioDAO = new UsuarioDAO();

        navbarMenu = findViewById(R.id.navbarMenu);
        navbarMenu.setSelectedItemId(R.id.home);
        txtAdicionarPublicacao = findViewById(R.id.txtAdicionarTarefa);
        txtAdicionarPubliAtletica = findViewById(R.id.txtAdicionarNoticiaAtletica);


        db = FirebaseFirestore.getInstance();
        colecaoPubliUsers = db.collection("publicacoesUsuarios");
        colecaoEventos = db.collection("eventos");
        colecaoPubliAtletica = db.collection("publicacoesAtletica");


        recyclerPubliUsers = findViewById(R.id.recyclerPubliUsers);
        recyclerPubliUsers.setLayoutManager(new LinearLayoutManager(this));
        listaPublicacoesUser = new ArrayList<>();
        publiUserAdapter = new PublicacoesUserAdapter(listaPublicacoesUser);
        recyclerPubliUsers.setAdapter(publiUserAdapter);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerEventos = findViewById(R.id.recyclerEventos);
        recyclerEventos.setLayoutManager(linearLayoutManager);
        listaEventos = new ArrayList<>();
        eventosAdapter = new EventosAdapter(listaEventos);
        recyclerEventos.setAdapter(eventosAdapter);

        recyclerPubliAtletica = findViewById(R.id.recyclerPubliAtletica);
        recyclerPubliAtletica.setLayoutManager(new LinearLayoutManager(this));
        listaPublicacoesAtletica = new ArrayList<>();
        publiAtleticaAdapter = new PublicacoesAtleticaAdapter(listaPublicacoesAtletica);
        recyclerPubliAtletica.setAdapter(publiAtleticaAdapter);



        exibirTarefas();
        exibirEventos();
        exibirPubliAtletica();


        navbarMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    return true;
                }
                if (item.getItemId() == R.id.loja) {
                    startActivity(new Intent(getApplicationContext(), LojaActivity.class));
                    overridePendingTransition(0, 0);
                }
                if (item.getItemId() == R.id.sobre) {
                    startActivity(new Intent(getApplicationContext(), SobreActivity.class));
                    overridePendingTransition(0, 0);
                }
                return false;
            }
        });
    }

    public void adicionarTarefa(View view){
        CardNoticias noticiaUsuario = new CardNoticias();
        noticiaUsuario.setDescricao((txtAdicionarPublicacao.getText().toString()));
        usuarioDAO.recuperarNome(nome -> {
            noticiaUsuario.setAutor(nome);
            // Nome do autor definido, agora você pode adicionar o item
            noticiaUsuario.setCurso("Engenharia");
            if (publiUserAdapter.adicionarItem(noticiaUsuario)) {
                Toast.makeText(getApplicationContext(), "Notícia adicionada com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Notícia adicionada com sucesso!", Toast.LENGTH_SHORT).show();
            }
            txtAdicionarPublicacao.setText("");
        });
    }
    public void adicionarPubliAtletica(View view){
        CardNoticias noticiaAtletica = new CardNoticias();
        noticiaAtletica.setDescricao((txtAdicionarPubliAtletica.getText().toString()));
            noticiaAtletica.setCurso("Engenharia");
            // Nome do autor definido, agora você pode adicionar o item
            if (publiAtleticaAdapter.adicionarItem(noticiaAtletica)) {
                Toast.makeText(getApplicationContext(), "Notícia adicionada com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Notícia adicionada com sucesso!", Toast.LENGTH_SHORT).show();
            }
            txtAdicionarPubliAtletica.setText("");
    }

    public void adicionarEvento(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    public void exibirTarefas(){
        // Configurar o SnapshotListener
        eventListener = new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("Firestore", "Erro ao ouvir as alterações", e);
                    return;
                }

                listaPublicacoesUser.clear();
                for (DocumentSnapshot document : querySnapshot) {
                    CardNoticias noticiaUsuario = new CardNoticias();
                    noticiaUsuario.setId((document.getId()));
                    noticiaUsuario.setDescricao(document.getString("descricao"));
                    noticiaUsuario.setCurso(document.getString("curso"));
                    noticiaUsuario.setAutor(document.getString("autor"));

                    listaPublicacoesUser.add(noticiaUsuario);
                }
                publiUserAdapter.notifyDataSetChanged();
            }
        };
    }

    public void exibirPubliAtletica(){
        // Configurar o SnapshotListener
        eventListenerAtletica = new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("Firestore", "Erro ao ouvir as alterações", e);
                    return;
                }

                listaPublicacoesAtletica.clear();
                for (DocumentSnapshot document : querySnapshot) {
                    CardNoticias noticiaAtletica = new CardNoticias();
                    noticiaAtletica.setId((document.getId()));
                    noticiaAtletica.setDescricao(document.getString("descricao"));
                    noticiaAtletica.setCurso(document.getString("curso"));

                    listaPublicacoesAtletica.add(noticiaAtletica);
                }
                publiAtleticaAdapter.notifyDataSetChanged();
            }
        };
    }

    public void exibirEventos(){
        // Configurar o SnapshotListener
        eventListenerEventos = new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("Firestore", "Erro ao ouvir as alterações", e);
                    return;
                }

                listaEventos.clear();
                for (DocumentSnapshot document : querySnapshot) {
                    CardEvento evento = new CardEvento();
                    evento.setId((document.getId()));
                    evento.setDescricao(document.getString("descricao"));
                    evento.setImg(document.getString("imagem"));

                    listaEventos.add(evento);
                }
                eventosAdapter.notifyDataSetChanged();
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

                    CardEvento evento = new CardEvento();
                    evento.setImg(fotoEmString);
                    evento.setDescricao("evento");
                    if (eventosAdapter.adicionarItem(evento)) {
                        Toast.makeText(getApplicationContext(), "Evento adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Evento adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){

            }
        }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        colecaoPubliUsers.addSnapshotListener(eventListener);
        colecaoEventos.addSnapshotListener(eventListenerEventos);
        colecaoPubliAtletica.addSnapshotListener(eventListenerAtletica);

    }



}