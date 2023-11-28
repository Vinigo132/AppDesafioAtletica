package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.myapplication.controller.UsuarioDAO;
import com.example.myapplication.model.Usuario;

import com.example.myapplication.view.MenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private TextInputLayout emailLogin;
    private TextInputLayout senhaLogin;
    private TextView esqueceuSenha;
    private TextInputLayout emailCadastro;
    private TextInputLayout senhaCadastro;
    private TextInputLayout confirmarSenhaCadastro;
    private TextInputLayout nome;
    private Animation fadeInAnimation;
    private Animation fadeOutAnimation;
    private EditText emailLoginEditText;
    private EditText senhaLoginEditText;
    private EditText nomeCadastroEditText;
    private EditText emailCadastroEditText;
    private EditText senhaCadastroEditText;
    private EditText ConferirSenhaEditText;
    private Button btnContinuar;
    private UsuarioDAO usuarioDAO;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth


        initializeViews();
        initializeAnimations();
        usuarioDAO = new UsuarioDAO();

        MaterialButtonToggleGroup materialButtonToggleGroup = findViewById(R.id.btnsToggle);

        materialButtonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {

                if (isChecked) {
                    if (checkedId == R.id.btnLogin) {
                        setVisibilityWithAnimation(View.VISIBLE, View.GONE);
                    } else if (checkedId == R.id.btnCadastrar) {
                        setVisibilityWithAnimation(View.GONE, View.VISIBLE);
                    }
                }
            }
        });
    }

    public void ClickContinuar(View view) {
        if(btnContinuar.getText().toString().equals("Continuar")){
            emailLoginEditText = findViewById(R.id.emailLoginEditText);
            senhaLoginEditText = findViewById(R.id.senhaLoginEditText);
            String email = emailLoginEditText.getText().toString();
            String senha = senhaLoginEditText.getText().toString();

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Login bem-sucedido", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Falha no login: " +
                                        task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }else{
            nomeCadastroEditText = findViewById(R.id.nomeCadastroEditText);
            emailCadastroEditText = findViewById(R.id.emailCadastroEditText);
            senhaCadastroEditText = findViewById(R.id.senhaCadastroEditText);
            ConferirSenhaEditText = findViewById(R.id.ConferirSenhaEditText);
            String nome = nomeCadastroEditText.getText().toString();
            String emailCadastro = emailCadastroEditText.getText().toString();
            String senhaCadastro = senhaCadastroEditText.getText().toString();
            String conferirSenha = ConferirSenhaEditText.getText().toString();

            Boolean cadastro = usuarioDAO.Cadastrar(nome, emailCadastro, senhaCadastro, conferirSenha);
            if (cadastro) {
                showToast("Usuário cadastrado com sucesso!");
                startMenuActivity();
            } else {
                showToast("Senhas não conferem. Tente novamente!");
            }

        }

    }

    private void initializeViews() {
        //       -------------- login------------------
        emailLogin = findViewById(R.id.emailLogin);
        senhaLogin = findViewById(R.id.senhaLogin);
        esqueceuSenha = findViewById(R.id.esqueceuSenha);
        //       -------------- Cadastro------------------
        emailCadastro = findViewById(R.id.emailCadastro);
        senhaCadastro = findViewById(R.id.senhaCadastro);
        confirmarSenhaCadastro = findViewById(R.id.confirmarSenhaCadastro);
        nome = findViewById(R.id.nomeCadastro);

        btnContinuar = findViewById(R.id.btnContinuar);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void initializeAnimations() {
        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
    }

    private void setVisibilityWithAnimation(int loginVisibility, int cadastroVisibility) {
        if (loginVisibility == View.VISIBLE) {
            emailLogin.startAnimation(fadeInAnimation);
            senhaLogin.startAnimation(fadeInAnimation);
            esqueceuSenha.startAnimation(fadeInAnimation);
            btnContinuar.setText("Continuar");
        } else {
            emailLogin.startAnimation(fadeOutAnimation);
            senhaLogin.startAnimation(fadeOutAnimation);
            esqueceuSenha.startAnimation(fadeOutAnimation);
        }

        if (cadastroVisibility == View.VISIBLE) {
            emailCadastro.startAnimation(fadeInAnimation);
            senhaCadastro.startAnimation(fadeInAnimation);
            confirmarSenhaCadastro.startAnimation(fadeInAnimation);
            nome.startAnimation(fadeInAnimation);
            btnContinuar.setText("Cadastrar");
        } else {
            emailCadastro.startAnimation(fadeOutAnimation);
            senhaCadastro.startAnimation(fadeOutAnimation);
            confirmarSenhaCadastro.startAnimation(fadeOutAnimation);
            nome.startAnimation(fadeOutAnimation);
        }

        emailLogin.setVisibility(loginVisibility);
        senhaLogin.setVisibility(loginVisibility);
        esqueceuSenha.setVisibility(loginVisibility);
        emailCadastro.setVisibility(cadastroVisibility);
        senhaCadastro.setVisibility(cadastroVisibility);
        confirmarSenhaCadastro.setVisibility(cadastroVisibility);
        nome.setVisibility(cadastroVisibility);
    }

    private void startMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}