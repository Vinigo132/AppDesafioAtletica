package com.example.myapplication.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.controller.UsuarioDAO;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class SobreActivity extends AppCompatActivity {

    BottomNavigationView navbarMenu;
    private TextView nomeUsuario;
    private UsuarioDAO usuarioDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        usuarioDAO = new UsuarioDAO();

        nomeUsuario = findViewById(R.id.nomeUsuario);
        navbarMenu = findViewById(R.id.navbarMenu);
        navbarMenu.setSelectedItemId(R.id.sobre);

        usuarioDAO.recuperarNome(nome -> nomeUsuario.setText(nome));

        navbarMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.sobre) {
                    return true;
                }
                if (item.getItemId() == R.id.loja) {
                    startActivity(new Intent(getApplicationContext(), LojaActivity.class));
                    overridePendingTransition(0, 0);
                }
                if (item.getItemId() == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                    overridePendingTransition(0, 0);
                }
                return false;
            }
        });
    }

    public void clickSair(View view){
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }

}