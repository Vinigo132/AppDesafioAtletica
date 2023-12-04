package com.example.myapplication.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.EventosAdapter;
import com.example.myapplication.controller.UsuarioDAO;
import com.example.myapplication.model.CardNoticias;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MenuActivity extends AppCompatActivity {

    BottomNavigationView navbarMenu;
    private RecyclerView recyclerEventos;
    private FirebaseFirestore db;
    private EventosAdapter eventoAdapter;
    private CollectionReference colecao;
    private List<CardNoticias> listaPublicacoesUser;
    private TextView txtAdicionarPublicacao;
    private TextView teste;
    private UsuarioDAO usuarioDAO;
    private EventListener<QuerySnapshot> eventListener;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        navbarMenu = findViewById(R.id.navbarMenu);
        navbarMenu.setSelectedItemId(R.id.home);
        txtAdicionarPublicacao = findViewById(R.id.txtAdicionarTarefa);
        teste = findViewById(R.id.teste);


        db = FirebaseFirestore.getInstance();
        colecao = db.collection("publicacoesUsuarios");
        recyclerEventos = findViewById(R.id.recyclerPubliUsers);
        recyclerEventos.setLayoutManager(new LinearLayoutManager(this));

        listaPublicacoesUser = new ArrayList<>();
        eventoAdapter = new EventosAdapter(listaPublicacoesUser);
        recyclerEventos.setAdapter(eventoAdapter);
        exibirTarefas();





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
        usuarioDAO = new UsuarioDAO();
        noticiaUsuario.setDescricao((txtAdicionarPublicacao.getText().toString()));
        usuarioDAO.recuperarNome(nome -> {
            noticiaUsuario.setAutor(nome);
            // Nome do autor definido, agora você pode adicionar o item
            noticiaUsuario.setCurso(" - Odonto");
            if (eventoAdapter.adicionarItem(noticiaUsuario)) {
                Toast.makeText(getApplicationContext(), "Tarefa adicionada com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Erro ao adicionar documento!", Toast.LENGTH_SHORT).show();
            }
            txtAdicionarPublicacao.setText("");
        });
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
                eventoAdapter.notifyDataSetChanged();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        colecao.addSnapshotListener(eventListener);
    }



}