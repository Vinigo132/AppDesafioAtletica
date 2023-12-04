package com.example.myapplication.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class LojaActivity extends AppCompatActivity {

    BottomNavigationView navbarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loja);


        navbarMenu = findViewById(R.id.navbarMenu);
        navbarMenu.setSelectedItemId(R.id.loja);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ImageView prod = findViewById(R.id.prod1);

        prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialogFragment dialog = new MyDialogFragment();
                dialog.show(getSupportFragmentManager(),"Minha dialog");
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
}